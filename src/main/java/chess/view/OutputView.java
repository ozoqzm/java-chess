package chess.view;

import chess.domain.Board;
import chess.domain.piece.Blank;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;

public class OutputView {

    public void printChess(Board board) {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece instanceof Blank) {
                    System.out.print('.');
                } else {
                    char symbol = piece.pieceType().symbol();
                    if (piece.color().isWhite()) { // 흰색일 경우 소문자
                        System.out.print(Character.toLowerCase(symbol));
                    } else {
                        System.out.print(Character.toUpperCase(symbol));
                    }
                }
            }
            System.out.println();
        }
    }

    public void printInvalidCommand() {
        System.out.println("잘못된 입력입니다. 'start' 또는 'end'를 입력하세요.");
    }

    public void printGameOver(Color winner, Board board) {
        System.out.println("게임 종료! 승자는 " + winner + "입니다.");
        System.out.println("흰색 점수: " + board.getScore(Color.WHITE));
        System.out.println("검은색 점수: " + board.getScore(Color.BLACK));
    }
}
