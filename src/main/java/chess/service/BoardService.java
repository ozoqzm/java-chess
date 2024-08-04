package chess.service;

import chess.domain.Board;
import chess.dao.BoardDao;
import chess.domain.Position;
import chess.domain.piece.Blank;
import chess.domain.piece.Piece;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class BoardService {
    private final BoardDao boardDao;

    public BoardService(BoardDao boardDao) {
        this.boardDao = boardDao;
    }

    // 보드 상태 저장
    public void saveBoardState(long gameId, Board board) {
        try {
            Set<Piece> pieces = extractPieces(board);
            boardDao.savePieces(gameId, pieces);

        } catch (SQLException e) {
            throw new RuntimeException("Error saving board state", e);
        }
    }

    // 모든 기물 Set으로 추출
    private Set<Piece> extractPieces(Board board) {
        Set<Piece> pieces = new HashSet<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null && !(piece instanceof Blank)) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

}
