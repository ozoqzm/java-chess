package chess.domain.piece;

import chess.domain.*;

import java.util.Set;

public final class Queen extends SlidingPiece {

    public Queen(final Color color, final Position position) {
        super(color, position);
    }

    @Override
    protected Set<Direction> directions() {
        return Direction.ofEvery();
    }

    @Override
    public Piece update(final Position destination) {
        return new Queen(color(), destination);
    }
    @Override
    public PieceType pieceType() {
        return PieceType.QUEEN;
    }
}