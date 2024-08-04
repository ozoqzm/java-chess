package chess.domain;

import chess.service.BoardService;
import chess.view.OutputView;

public class ChessGame {
    private final Board board;
    private final BoardService boardService;
    private long gameId; // 게임 ID를 관리

    public ChessGame(BoardService boardService) {
        this.board = new Board();
        this.boardService = boardService;
    }

    public void handleMove(String input, OutputView ov) {
        String[] parts = input.split(" ");
        if (parts.length == 3) {
            try {
                Position from = parsePosition(parts[1]); // 출발지
                Position to = parsePosition(parts[2]); // 목적지
                board.movePiece(from, to);

                // 게임 종료 상태 확인
                if (board.isGameOver()) {
                    ov.printChess(board);
                    ov.printGameOver(board.getWinner(), board);
                } else {
                    ov.printChess(board);
                }
                // 보드 상태 저장
                boardService.saveBoardState(gameId, board);

            } catch (Exception e) {
                System.out.println("이동 오류: " + e.getMessage());
            }
        } else {
            ov.printInvalidCommand();
        }
    }

    private Position parsePosition(String positionString) {
        char columnChar = positionString.charAt(0);
        char rowChar = positionString.charAt(1);

        Column column = Column.valueOf(String.valueOf(columnChar).toUpperCase());
        Row row;
        try {
            int rowNumber = (rowChar - '0'); // 아래서부터
            row = Row.values()[rowNumber - 1]; // ex.Row.EIGHT -> 인덱스 0
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("행(row) 값이 유효하지 않습니다.");
        }

        return new Position(row, column);
    }

    public Board getBoard() {
        return board;
    }
}
