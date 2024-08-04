package chess.domain.piece;

import chess.domain.Position;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Piece {

    private final Color color;
    private final Position position;

    protected Piece(final Color color, final Position position) {
        this.color = color;
        this.position = position;
    }

    public final Piece move(final Position destination, final Set<Piece> pieces) {
        if (position.equals(destination)) {
            throw new IllegalArgumentException("동일한 위치로 이동할 수 없습니다.");
        }

        final var movablePositions = legalMovePositions(pieces);
        if (!movablePositions.contains(destination)) {
            throw new IllegalArgumentException("말을 움직일 수 없습니다.");
        }

        return update(destination);
    }

    public final Set<Position> legalMovePositions(final Set<Piece> rawPieces) {
        final var pieces = new Pieces(color, rawPieces);

        return directions().stream()
                .filter(position::canMove)
                .flatMap(it -> legalMovePositions(it, pieces).stream())
                .collect(Collectors.toSet());
    }

    protected abstract Set<Direction> directions(); // 가능한 이동 방향들

    protected abstract Set<Position> legalMovePositions(Direction direction, Pieces pieces); // 특정 방향으로 이동 가능한 위치들 계산

    public abstract Piece update(Position destination); // 위치 업데이트 후 기물 반환

    public abstract PieceType pieceType(); // 기물의 타입을 반환하는 메서드

    public final Color color() {
        return color;
    }

    public final Position position() {
        return position;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Piece piece = (Piece) o;
        return color == piece.color && Objects.equals(position, piece.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, position);
    }

    @Override
    public String toString() {
        return "Piece{" +
                "color=" + color +
                ", position=" + position +
                '}';
    }
}
