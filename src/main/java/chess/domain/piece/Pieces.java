package chess.domain.piece;

import chess.domain.Position;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.function.UnaryOperator.identity;
import static java.util.stream.Collectors.toMap;

// 말들 위치와 정보 Map으로 관리
public final class Pieces {

    private final Color color;
    private final Map<Position, Piece> value;

    public Pieces(final Color color, final Set<Piece> value) {
        // Set<Piece>를 Map<Position, Piece>으로..
        this(color, value.stream().collect(toMap(Piece::position, identity()))); // 각 piece의 position을 키로 사용, piece가 map의 값
    }

    public Pieces(final Color color, final Map<Position, Piece> value) {
        this.color = color;
        this.value = new HashMap<>(value);
    }

    public Piece get(final Position position) {
        return value.getOrDefault(position, new Blank(position));
    }

    public boolean isSameColor(final Position position) {
        return color.equals(get(position).color());
    }

    public boolean isOpposite(final Position position) {
        return color.opposite().equals(get(position).color());
    }

    public boolean isBlank(final Position position) {
        return get(position).color().isEmpty();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Pieces pieces = (Pieces) o;
        return color == pieces.color && Objects.equals(value, pieces.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, value);
    }

    @Override
    public String toString() {
        return "Pieces{" +
                "color=" + color +
                ", value=" + value +
                '}';
    }
}