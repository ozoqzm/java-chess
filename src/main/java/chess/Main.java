package chess;

import chess.dao.BoardDao;
import chess.domain.Board;
import chess.service.BoardService;
import chess.view.InputView;
import chess.view.OutputView;
import chess.domain.ChessGame;

public class Main {
    public static void main(String[] args) {
        InputView iv = new InputView();
        OutputView ov = new OutputView();
        BoardDao boardDao = new BoardDao();
        BoardService boardService = new BoardService(boardDao);

        ChessGame chessGame = new ChessGame(boardService); // gameId는 예시로 1로 설정

        System.out.println("체스 게임을 시작합니다.");
        System.out.println("> 게임 시작: start");
        System.out.println("> 종료: end");
        System.out.println("> 게임 이동: move source위치 target위치 - 예. move b2 b3");

        while (true) {
            String input = iv.whether();
            if (input.equals("start")) {
                ov.printChess(chessGame.getBoard()); // Print the current board
            } else if (input.equals("end")) {
                break;
            } else if (input.startsWith("move")) {
                chessGame.handleMove(input, ov);
            } else {
                ov.printInvalidCommand();
            }
        }
    }
}
