package chess.domain;

import chess.domain.piece.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {
    private final Piece[][] board;
    private Position whiteKingPosition;
    private Position blackKingPosition;

    public Board() {
        this.board = new Piece[8][8];
        initializeBoard();
    }

    private void initializeBoard() {
        // 검은색 진영 하단 배치
        board[7] = new Piece[]{
                new Rook(Color.BLACK, new Position(Row.EIGHT, Column.A)),
                new Knight(Color.BLACK, new Position(Row.EIGHT, Column.B)),
                new Bishop(Color.BLACK, new Position(Row.EIGHT, Column.C)),
                new Queen(Color.BLACK, new Position(Row.EIGHT, Column.D)),
                new King(Color.BLACK, new Position(Row.EIGHT, Column.E)),
                new Bishop(Color.BLACK, new Position(Row.EIGHT, Column.F)),
                new Knight(Color.BLACK, new Position(Row.EIGHT, Column.G)),
                new Rook(Color.BLACK, new Position(Row.EIGHT, Column.H))
        };
        for (int i = 0; i < 8; i++) {
            board[6][i] = new FirstBlackPawn(new Position(Row.SEVEN, Column.values()[i]));
        }

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Blank(new Position(Row.values()[i], Column.values()[j]));
            }
        }
        for (int i = 0; i < 8; i++) {
            board[1][i] = new FirstWhitePawn(new Position(Row.TWO, Column.values()[i]));
        }
        board[0] = new Piece[]{
                new Rook(Color.WHITE, new Position(Row.ONE, Column.A)),
                new Knight(Color.WHITE, new Position(Row.ONE, Column.B)),
                new Bishop(Color.WHITE, new Position(Row.ONE, Column.C)),
                new Queen(Color.WHITE, new Position(Row.ONE, Column.D)),
                new King(Color.WHITE, new Position(Row.ONE, Column.E)),
                new Bishop(Color.WHITE, new Position(Row.ONE, Column.F)),
                new Knight(Color.WHITE, new Position(Row.ONE, Column.G)),
                new Rook(Color.WHITE, new Position(Row.ONE, Column.H))
        };
    }

    public Piece getPiece(int row, int column) {
        return board[row][column];
    }

    public Piece getPiece(Position position) {
        return board[position.row().ordinal()][position.column().ordinal()];
    }

    public void movePiece(Position from, Position to) {
        Piece piece = getPiece(from.row().ordinal(), from.column().ordinal());
        if (piece == null || piece instanceof Blank) {
            throw new IllegalArgumentException("이동할 기물이 없습니다.");
        }

        Pieces pieces = new Pieces(piece.color(), getAllPieces());

        // 이동할 기물이 목적지에 있을 경우 확인
        Piece targetPiece = pieces.get(to);
        if (!targetPiece.color().isEmpty() && targetPiece.color().equals(piece.color())) {
            throw new IllegalArgumentException("자신의 기물이 있는 위치로 이동할 수 없습니다.");
        }

        // 기물 이동 처리
        updatePiece(from, to, piece);
    }

    private Set<Piece> getAllPieces() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(piece -> !(piece instanceof Blank))
                .collect(Collectors.toSet());
    }

    public void updatePiece(Position from, Position to, Piece piece) {
        board[to.row().ordinal()][to.column().ordinal()] = piece.update(to);
        board[from.row().ordinal()][from.column().ordinal()] = new Blank(from);
    }

    public boolean isGameOver() {
        return getKing(Color.WHITE) == null || getKing(Color.BLACK) == null;
    }

    public Color getWinner() {
        if (getKing(Color.WHITE) == null) {
            return Color.BLACK;
        } else if (getKing(Color.BLACK) == null) {
            return Color.WHITE;
        }
        throw new IllegalStateException("게임이 아직 종료되지 않았습니다.");
    }

    public double getScore(Color color) {
        Map<Integer, Integer> columnCounts = new HashMap<>();  // 각 열의 폰 개수
        double score = 0.0;

        // 기물 점수 계산
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece.color().equals(color) && !(piece instanceof Blank)) { // 색같고 빈칸 아니면
                    if (piece.pieceType() == PieceType.PAWN) {
                        // getOrDefault- 주어진 키값 반환, 키 x경우 기본값 반환
                        columnCounts.put(col, columnCounts.getOrDefault(col, 0) + 1);
                    }
                    score += piece.pieceType().value();
                }
            }
        }
        // 같은 세로줄에 있는 폰의 점수 조정
        for (int column : columnCounts.keySet()) {
            int count = columnCounts.get(column);
            if (count > 1) {
                score -= count * 0.5; // 기본 점수에서 0.5씩 감소
            }
        }
        return score;
    }

    private Piece getKing(Color color) {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(piece -> piece instanceof King && piece.color().equals(color))
                .findFirst()
                .orElse(null);
    }
}
