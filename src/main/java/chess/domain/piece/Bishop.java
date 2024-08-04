package chess.domain.piece;

import chess.domain.*;

import java.util.Set;

public final class Bishop extends SlidingPiece {

    public Bishop(final Color color, final Position position) {
        super(color, position);
    }

    @Override
    protected Set<Direction> directions() {
        return Direction.ofDiagonal();
    }

    @Override
    public Piece update(final Position destination) {
        return new Bishop(color(), destination);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.BISHOP;
    }
}