package chess.domain;

import chess.service.BoardService;
import chess.domain.Position;
import chess.view.OutputView;

public class ChessGame {
    private Board board;
    private final BoardService boardService;

    public ChessGame(BoardService boardService) {
        this.boardService = boardService;
        this.board = new Board(); // 초기화할 필요 없으니 loadGame에서 로드
    }

    public void handleMove(String input, OutputView ov) {
        String[] parts = input.split(" ");
        if (parts.length == 3) {
            try {
                Position from = parsePosition(parts[1]); // 출발지
                Position to = parsePosition(parts[2]); // 목적지
                boardService.movePiece(board, from, to);

                // 게임 종료 상태 확인
                if (boardService.isGameOver(board)) {
                    ov.printChess(board);
                    ov.printGameOver(boardService.getWinner(board), board);
                } else {
                    ov.printChess(board);
                }
                // 보드 상태 저장
                boardService.saveBoard(board);

            } catch (Exception e) {
                System.out.println("이동 오류: " + e.getMessage());
            }
        } else {
            ov.printInvalidCommand();
        }
    }

    public void startGame() {
        boardService.insertInitialBoardState(); // 초기 보드 상태를 데이터베이스에 저장
        loadGame(); // 게임 시작 시 보드 상태 로드
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

    public void loadGame() {
        boardService.loadBoard(); // 데이터베이스에서 보드 상태를 로드
    }

    public Board getBoard() {
        return board;
    }
}
