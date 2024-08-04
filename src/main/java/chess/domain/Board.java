package chess.domain;

import chess.domain.piece.*;

public class Board {
    private final Piece[][] board;

    public Board() {
        this.board = new Piece[8][8];
        initializeBoard();
    }

    private void initializeBoard() {
        // Initialize black pieces
        board[0] = new Piece[]{
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
            board[1][i] = new FirstBlackPawn(new Position(Row.SEVEN, Column.values()[i]));
        }

        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Blank(new Position(Row.values()[i], Column.values()[j]));
            }
        }
        for (int i = 0; i < 8; i++) {
            board[6][i] = new FirstWhitePawn(new Position(Row.TWO, Column.values()[i]));
        }
        board[7] = new Piece[]{
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

    public void updatePiece(Position from, Position to, Piece piece) {
        board[to.row().ordinal()][to.column().ordinal()] = piece.update(to);
        board[from.row().ordinal()][from.column().ordinal()] = new Blank(from);
    }

    // 현재 보드 출력
    public void printBoard() {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece.color().isEmpty()) {
                    System.out.print('.');
                } else {
                    char symbol = piece.pieceType().symbol();
                    if (piece.color().isWhite()) {
                        System.out.print(Character.toLowerCase(symbol));
                    } else {
                        System.out.print(Character.toUpperCase(symbol));
                    }
                }
            }
            System.out.println();
        }
    }

}
