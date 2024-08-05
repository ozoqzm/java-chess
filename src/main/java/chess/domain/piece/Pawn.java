package chess.domain.piece;

import chess.domain.*;

import java.util.Set;

public abstract class Pawn extends Piece {

    protected Pawn(final Color color, final Position position) {
        super(color, position);
    }

    @Override
    protected Set<Position> legalMovePositions(final Direction direction, final Pieces pieces) {
        final var movedPosition = position().move(direction);
        if (direction.isVertical() && pieces.isBlank(movedPosition)) { // 이동 방향 수직이고 목적지가 빈칸
            return Set.of(movedPosition);
        }
        if (direction.isDiagonal() && pieces.isOpposite(movedPosition)) { // 이동 방향 대각선이고 목적지에 상대 기물
            return Set.of(movedPosition);
        }

        return Set.of(); // 둘다 아니면 빈집합 반환
    }
}
