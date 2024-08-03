package chess.domain;

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
    protected Piece update(final Position destination) {
        return new Bishop(color(), destination);
    }
}