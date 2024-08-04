package chess.view;

import chess.domain.Board;
import chess.domain.piece.Piece;

public class OutputView {
    public void printChess(Board board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
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
