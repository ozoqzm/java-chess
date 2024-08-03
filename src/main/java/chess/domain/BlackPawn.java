package chess.domain;

import java.util.Set;

public final class BlackPawn extends Pawn {

    BlackPawn(final Position position) {
        super(Color.BLACK, position);
    }

    @Override
    protected Set<Direction> directions() {
        return Direction.ofBlackPawn();
    }

    @Override
    protected Piece update(final Position destination) {
        return new BlackPawn(destination);
    }
}