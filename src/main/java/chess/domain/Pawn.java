package chess.domain;

import java.util.Set;

public abstract class Pawn extends Piece {

    protected Pawn(final Color color, final Position position) {
        super(color, position);
    }

    @Override
    protected Set<Position> legalMovePositions(final Direction direction, final Pieces pieces) {
        final var movedPosition = position().move(direction);
        if (direction.isVertical() && pieces.isBlank(movedPosition)) {
            return Set.of(movedPosition);
        }
        if (direction.isDiagonal() && pieces.isOpposite(movedPosition)) {
            return Set.of(movedPosition);
        }

        return Set.of();
    }
}
