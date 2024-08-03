package chess.domain;

import java.util.Set;

// 빈칸
public final class Blank extends Piece {

    public Blank(final Position position) {
        super(Color.EMPTY, position);
    }

    @Override
    protected Set<Direction> directions() {
        return Set.of();
    }

    @Override
    protected Set<Position> legalMovePositions(final Direction direction, final Pieces pieces) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Piece update(final Position destination) {
        throw new UnsupportedOperationException();
    }
}