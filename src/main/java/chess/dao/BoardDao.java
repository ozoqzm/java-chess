package chess.dao;

import chess.domain.Board;
import chess.domain.Column;
import chess.domain.Position;
import chess.domain.Row;
import chess.domain.piece.*;
import java.sql.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BoardDao {
    private static final String SERVER = "localhost:3306"; // MySQL 서버 주소
    private static final String DATABASE = "chess"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root"; // MySQL 서버 아이디
    private static final String PASSWORD = "20020923"; // MySQL 서버 비밀번호

    public Connection getConnection() {
        // 드라이버 연결
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void savePieces(long gameId, Set<Piece> pieces) throws SQLException {
        String pieceQuery = "INSERT INTO piece (x, y, color, type) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE x=VALUES(x), y=VALUES(y), color=VALUES(color), type=VALUES(type)";
        String gamePieceQuery = "INSERT INTO chess_game_piece (chess_game_id, piece_id) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement pieceStatement = connection.prepareStatement(pieceQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement gamePieceStatement = connection.prepareStatement(gamePieceQuery)) {

            Map<Piece, Long> pieceIdMap = new HashMap<>();

            for (Piece piece : pieces) {
                pieceStatement.setString(1, piece.position().column().name());
                pieceStatement.setString(2, piece.position().row().name());
                pieceStatement.setString(3, piece.color().name());
                pieceStatement.setString(4, piece.pieceType().name());
                pieceStatement.addBatch();
            }

            pieceStatement.executeBatch();

            ResultSet generatedKeys = pieceStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                long pieceId = generatedKeys.getLong(1);
                Piece piece = findPieceById(pieces, pieceId);
                if (piece != null) {
                    pieceIdMap.put(piece, pieceId);
                }
            }

            for (Map.Entry<Piece, Long> entry : pieceIdMap.entrySet()) {
                gamePieceStatement.setLong(1, gameId);
                gamePieceStatement.setLong(2, entry.getValue());
                gamePieceStatement.addBatch();
            }
            gamePieceStatement.executeBatch();
        }
    }

    public Set<Piece> loadPieces(long gameId) throws SQLException {
        Set<Piece> pieces = new HashSet<>();
        String query = "SELECT p.type, p.color, p.x, p.y FROM piece p JOIN chess_game_piece cgp ON p.id = cgp.piece_id WHERE cgp.chess_game_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, gameId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String pieceType = resultSet.getString("type");
                    String color = resultSet.getString("color");
                    String column = resultSet.getString("x");
                    String row = resultSet.getString("y");
                    Position position = new Position(Row.valueOf(row), Column.valueOf(column));

                    Piece piece = createPiece(pieceType, color, position);
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

    private Piece createPiece(String pieceType, String color, Position position) {
        Color pieceColor = Color.valueOf(color);
        switch (pieceType) {
            case "PAWN":
                return pieceColor == Color.WHITE ? new WhitePawn(position) : new BlackPawn(position);
            case "ROOK":
                return new Rook(pieceColor, position);
            case "KNIGHT":
                return new Knight(pieceColor, position);
            case "BISHOP":
                return new Bishop(pieceColor, position);
            case "QUEEN":
                return new Queen(pieceColor, position);
            case "KING":
                return new King(pieceColor, position);
            default:
                throw new IllegalArgumentException("Unknown piece type: " + pieceType);
        }
    }

    private Piece findPieceById(Set<Piece> pieces, long pieceId) {
        // Implement this method based on how you track the piece objects
        // This might require an additional way to retrieve piece objects or maintain a map
        return null; // Placeholder
    }
}
