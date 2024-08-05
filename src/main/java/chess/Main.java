package chess;

import chess.dao.BoardDao;
import chess.domain.ChessGame;
import chess.service.BoardService;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BoardService boardService = new BoardService(new BoardDao());
        ChessGame chessGame = new ChessGame(boardService);
        OutputView outputView = new OutputView();
        InputView inputView = new InputView();

        Scanner scanner = new Scanner(System.in);

        // 게임 시작 및 초기 보드 상태 설정
        chessGame.startGame();
        // outputView.printChess(chessGame.getBoard());

        System.out.println("체스 게임을 시작합니다.");
        System.out.println("> 게임 시작: start");
        System.out.println("> 종료: end");
        System.out.println("> 게임 이동: move source위치 target위치 - 예. move b2 b3");

        while (true) {
            String input = inputView.whether();
            if (input.equals("start")) {
                outputView.printChess(chessGame.getBoard());
            } else if (input.equals("end")) {
                break;
            } else if (input.startsWith("move")) {
                chessGame.handleMove(input, outputView);
            } else {
                outputView.printInvalidCommand();
            }
        }
        scanner.close();
    }
}
