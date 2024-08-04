package chess.service;

import chess.dao.BoardDao;
import chess.domain.Board;
import chess.domain.piece.Color;
import chess.domain.Position;

public class BoardService {
    private final BoardDao boardDao;

    public BoardService(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    public void movePiece(Board board, Position from, Position to) {
        board.movePiece(from, to);
        boardDao.saveBoard(board);
    }

    public boolean isGameOver(Board board) {
        return board.isGameOver();
    }

    public Color getWinner(Board board) {
        return board.getWinner();
    }

    public void saveBoard(Board board) {
        boardDao.saveBoard(board);
    }

    public Board loadBoard() {
        return boardDao.loadBoard();
    }

    public void insertInitialBoardState() {
        Board initialBoard = new Board(); // 새 초기 보드 생성
        boardDao.saveBoard(initialBoard); // 데이터베이스에 저장
    }
}
