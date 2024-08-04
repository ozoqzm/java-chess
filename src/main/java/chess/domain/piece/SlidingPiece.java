package chess.domain.piece;

import chess.domain.Position;

import java.util.HashSet;
import java.util.Set;

// 연속 여러 칸 이동 가능 (퀸, 룩, 비숍)
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
