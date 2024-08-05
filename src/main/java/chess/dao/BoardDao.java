package chess.dao;

import chess.domain.Board;
import chess.domain.piece.Blank;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;
import chess.domain.piece.*;
import chess.domain.Position;
import chess.domain.Row;
import chess.domain.Column;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BoardDao {
    private static final String SERVER = "localhost:3306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL 데이터베이스 이름
    private static final String OPTION = "?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root"; // MySQL 서버 아이디
    private static final String PASSWORD = "20020923"; // MySQL 서버 비밀번호

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD
            );
        } catch (SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Board loadBoard() {
        Board board = new Board(); // 새로운 보드 객체 생성
        String sql = "SELECT row_num, col_num, piece_type, color FROM board_state";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int rowNum = rs.getInt("row_num");
                int colNum = rs.getInt("col_num");
                String pieceType = rs.getString("piece_type");
                String color = rs.getString("color");

                Position position = new Position(Row.values()[rowNum], Column.values()[colNum]);
                Piece piece = createPiece(pieceType, color, position);

                // 직접 보드에 기물 설정
                board.getPiece(rowNum, colNum).update(position); // 해당 위치에 기물 설정
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return board;
    }

    public void saveBoard(Board board) {
        String sql = "INSERT INTO board_state (row_num, col_num, piece_type, color) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 기존 보드 상태 삭제
            String deleteSql = "DELETE FROM board_state";
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.executeUpdate();
            }

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Piece piece = board.getPiece(row, col);
                    if (!(piece instanceof Blank)) {
                        pstmt.setInt(1, row);
                        pstmt.setInt(2, col);
                        pstmt.setString(3, piece.pieceType().name());
                        pstmt.setString(4, piece.color().name());
                        pstmt.addBatch();
                    }
                }
            }
            pstmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertInitialBoardState() {
        String sql = "INSERT INTO board_state (row_num, col_num, piece_type, color) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 초기 보드 상태를 데이터베이스에 저장
            Board board = new Board();
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    Piece piece = board.getPiece(row, col);
                    if (!(piece instanceof Blank)) {
                        pstmt.setInt(1, row);
                        pstmt.setInt(2, col);
                        pstmt.setString(3, piece.pieceType().name());
                        pstmt.setString(4, piece.color().name());
                        pstmt.addBatch();
                    }
                }
            }
            pstmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Piece createPiece(String pieceType, String color, Position position) {
        Color pieceColor = Color.valueOf(color);
        switch (pieceType) {
            case "KING":
                return new King(pieceColor, position);
            case "QUEEN":
                return new Queen(pieceColor, position);
            case "ROOK":
                return new Rook(pieceColor, position);
            case "BISHOP":
                return new Bishop(pieceColor, position);
            case "KNIGHT":
                return new Knight(pieceColor, position);
            case "PAWN":
                // Default to FirstBlackPawn or FirstWhitePawn based on color
                return pieceColor == Color.BLACK ? new FirstBlackPawn(position) : new FirstWhitePawn(position);
            default:
                throw new IllegalArgumentException("Unknown piece type: " + pieceType);
        }
    }
}
