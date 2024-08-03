package chess.domain;

import java.util.HashSet;
import java.util.Set;

public abstract class SlidingPiece extends Piece {

    protected SlidingPiece(final Color color, final Position position) {
        super(color, position);
    }

    @Override
    protected final Set<Position> legalMovePositions(final Direction direction, final Pieces pieces) {
        final Set<Position> positions = new HashSet<>();

        var current = position();
        while (current.canMove(direction)) {
            current = current.move(direction);

            if (pieces.isSameColor(current)) {
                break;
            }
            if (pieces.isOpposite(current)) {
                positions.add(current);
                break;
            }

            positions.add(current);
        }

        return positions;
    }
}
