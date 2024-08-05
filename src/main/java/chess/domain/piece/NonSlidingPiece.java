package chess.domain.piece;

import chess.domain.Position;

import java.util.Set;

// 특정 패턴에 따라 한 번의 이동으로만 이동 (나이트, 킹)
public abstract class NonSlidingPiece extends Piece {

    protected NonSlidingPiece(final Color color, final Position position) {
        super(color, position);
    }

    @Override
    protected Set<Position> legalMovePositions(final Direction direction, final Pieces pieces) {
        final var movedPosition = position().move(direction);
        if (pieces.isBlank(movedPosition) || pieces.isOpposite(movedPosition)) {
            return Set.of(movedPosition);
        }

        return Set.of();
    }
}
