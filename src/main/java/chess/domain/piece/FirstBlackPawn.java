package chess.domain.piece;

import chess.domain.*;

import java.util.Set;

public final class FirstBlackPawn extends Pawn {

    public FirstBlackPawn(final Position position) {
        super(Color.BLACK, position);
    }

    @Override
    protected Set<Direction> directions() {
        return Direction.ofFirstBlackPawn();
    }

    @Override
    public Piece update(final Position destination) {
        return new BlackPawn(destination);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.PAWN;
    }
}
