package chess.domain.piece;

import chess.domain.Position;

import java.util.Set;

public final class WhitePawn extends Pawn {

    WhitePawn(final Position position) {
        super(Color.WHITE, position);
    }

    @Override
    protected Set<Direction> directions() {
        return Direction.ofWhitePawn();
    }

    @Override
    public Piece update(final Position destination) {
        return new WhitePawn(destination);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.PAWN;
    }
}
