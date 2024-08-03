package chess.domain;

import java.util.Set;

public final class Rook extends SlidingPiece {

    public Rook(final Color color, final Position position) {
        super(color, position);
    }

    @Override
    protected Set<Direction> directions() {
        return Direction.ofLinear();
    }

    @Override
    protected Piece update(final Position destination) {
        return new Rook(color(), destination);
    }
}
