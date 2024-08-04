package chess.domain.piece;

import chess.domain.*;

import java.util.Set;

public final class FirstWhitePawn extends Pawn {

    public FirstWhitePawn(final Position position) {
        super(Color.WHITE, position);
    }

    @Override
    protected Set<Direction> directions() {
        return Direction.ofFirstWhitePawn();
    }

    @Override
    protected Piece update(final Position destination) {
        return new WhitePawn(destination);
    }
}
