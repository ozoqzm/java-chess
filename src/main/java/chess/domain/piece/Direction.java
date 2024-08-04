package chess.domain.piece;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.unmodifiableSet;

public enum Direction {

    // 기본 방향 정의 (x, y 좌표)
    NORTH(0, 1),            // 북쪽으로 한 칸
    NORTH_NORTH(0, 2),      // 북쪽으로 두 칸
    NORTH_EAST(1, 1),       // 북동쪽으로 한 칸
    EAST(1, 0),             // 동쪽으로 한 칸
    SOUTH_EAST(1, -1),      // 남동쪽으로 한 칸
    SOUTH(0, -1),           // 남쪽으로 한 칸
    SOUTH_SOUTH(0, -2),     // 남쪽으로 두 칸
    SOUTH_WEST(-1, -1),     // 남서쪽으로 한 칸
    WEST(-1, 0),            // 서쪽으로 한 칸
    NORTH_WEST(-1, 1),      // 북서쪽으로 한 칸

    // 나이트(말)의 이동 방향 정의 (x, y 좌표)
    NORTH_NORTH_EAST(1, 2), // 북쪽으로 두 칸, 동쪽으로 한 칸
    NORTH_NORTH_WEST(-1, 2),// 북쪽으로 두 칸, 서쪽으로 한 칸
    SOUTH_SOUTH_EAST(1, -2),// 남쪽으로 두 칸, 동쪽으로 한 칸
    SOUTH_SOUTH_WEST(-1, -2),// 남쪽으로 두 칸, 서쪽으로 한 칸
    EAST_EAST_NORTH(2, 1),  // 동쪽으로 두 칸, 북쪽으로 한 칸
    EAST_EAST_SOUTH(2, -1), // 동쪽으로 두 칸, 남쪽으로 한 칸
    WEST_WEST_NORTH(-2, 1), // 서쪽으로 두 칸, 북쪽으로 한 칸
    WEST_WEST_SOUTH(-2, -1);// 서쪽으로 두 칸, 남쪽으로 한 칸

    private final int x;
    private final int y;

    Direction(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    // 상하좌우 방향 반환 (Rook)
    public static Set<Direction> ofLinear() {
        return Set.of(NORTH, EAST, SOUTH, WEST);
    }

    // 대각선 방향 반환 (Bishop)
    public static Set<Direction> ofDiagonal() {
        return Set.of(NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST);
    }

    // 모든 방향 반환 (king, queen)
    public static Set<Direction> ofEvery() {
        return Stream.concat(ofLinear().stream(), ofDiagonal().stream())
                .collect(Collectors.toSet());
    }

    // 나이트 이동 방향 반환
    public static Set<Direction> ofKnight() {
        return Set.of(NORTH_NORTH_EAST, NORTH_NORTH_WEST,
                SOUTH_SOUTH_EAST, SOUTH_SOUTH_WEST,
                EAST_EAST_NORTH, EAST_EAST_SOUTH,
                WEST_WEST_NORTH, WEST_WEST_SOUTH);
    }

    // 백색 폰 첫 이동 방향 반환
    public static Set<Direction> ofFirstWhitePawn() {
        final var whitePawnDirections = new HashSet<>(ofWhitePawn());
        whitePawnDirections.add(NORTH_NORTH);

        return unmodifiableSet(whitePawnDirections);
    }

    // 백색 폰 일반 이동 방향 반환
    public static Set<Direction> ofWhitePawn() {
        return Set.of(NORTH, NORTH_EAST, NORTH_WEST);
    }

    // 흑색 폰 첫 이동 방향 반환
    public static Set<Direction> ofFirstBlackPawn() {
        final var blackPawnDirections = new HashSet<>(ofBlackPawn());
        blackPawnDirections.add(SOUTH_SOUTH);

        return unmodifiableSet(blackPawnDirections);
    }

    // 흑색 폰 일반 이동 방향 반환
    public static Set<Direction> ofBlackPawn() {
        return Set.of(SOUTH, SOUTH_EAST, SOUTH_WEST);
    }

    // x, y좌표 해당하는 방향 반환
    public static Direction valueOf(final int x, final int y) {
        return Arrays.stream(values())
                .filter(it -> it.x == x && it.y == y)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 위치입니다."));
    }

    // 세로 방향인지 확인
    public boolean isVertical() {
        return x == 0 && y != 0;
    }

    // 대각선 방향인지 확인
    public boolean isDiagonal() {
        return ofDiagonal().contains(this);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "x=" + x +
                ", y=" + y +
                "} " + super.toString();
    }
}
