package chess.domain;

import java.util.Set;

public final class Knight extends NonSlidingPiece {

    public Knight(final Color color, final Position position) {
        super(color, position);
    }

    @Override
    protected Set<Direction> directions() {
        return Direction.ofKnight();
    }

    @Override
    protected Piece update(final Position destination) {
        return new Knight(color(), destination);
    }
}