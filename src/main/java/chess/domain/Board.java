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

        // Initialize black pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new FirstBlackPawn(new Position(Row.SEVEN, Column.values()[i]));
        }

        // Initialize empty spaces
        for (int i = 2; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Blank(new Position(Row.values()[i], Column.values()[j]));
            }
        }

        // Initialize white pawns
        for (int i = 0; i < 8; i++) {
            board[6][i] = new FirstWhitePawn(new Position(Row.TWO, Column.values()[i]));
        }

        // Initialize white pieces
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

}
