package chess;

import chess.domain.Board;
import chess.view.InputView;
import chess.view.OutputView;

public class Main {
    public static void main(String[] args) {
        InputView iv = new InputView();
        OutputView ov = new OutputView();
        Board board = new Board();

        System.out.println("체스 게임을 시작합니다.");
        System.out.println("> 게임 시작: start");
        System.out.println("> 종료: end");
        System.out.println("> 게임 이동: move source위치 target위치 - 예. move b2 b3");

        while (true) {
            String input = iv.whether();
            if (input.equals("start")) {
                ov.printChess(board);
            } else if (input.equals("end")) {
                break;
            } else {
                System.out.println("잘못된 입력입니다. 'start' 또는 'end'를 입력하세요.");
            }
        }
    }
}
