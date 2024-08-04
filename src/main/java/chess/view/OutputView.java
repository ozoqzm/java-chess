package chess.view;

import chess.domain.Board;
import chess.domain.piece.Blank;
import chess.domain.piece.Color;
import chess.domain.piece.Piece;

public class OutputView {

    // 보드를 출력하는 메서드
    public void printChess(Board board) {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece instanceof Blank) {
                    System.out.print('.');
                } else {
                    char symbol = piece.pieceType().symbol();
                    if (piece.color().isWhite()) {
                        System.out.print(Character.toLowerCase(symbol)); // 흰색 기물은 소문자
                    } else {
                        System.out.print(Character.toUpperCase(symbol)); // 검은색 기물은 대문자
                    }
                }
            }
            System.out.println();
        }
    }

    // 잘못된 명령어 입력 시 출력하는 메서드
    public void printInvalidCommand() {
        System.out.println("잘못된 입력입니다. 'start' 또는 'end'를 입력하세요.");
    }

    // 게임 종료 시 출력하는 메서드
    public void printGameOver(Color winner, Board board) {
        System.out.println("게임 종료! 승자는 " + winner + "입니다.");
        System.out.println("흰색 점수: " + board.getScore(Color.WHITE));
        System.out.println("검은색 점수: " + board.getScore(Color.BLACK));
    }
}
