package advent.day5;

public class LineMap {
    int[][] world;
    private final int size;

    LineMap(final int size) {
        world = new int[size][size];
        this.size = size;
    }

    void record(final Line line) {
        if (line.isHorizontal()) {
            final int fromX = Math.min(line.a.x, line.b.x);
            final int toX = Math.max(line.a.x, line.b.x);
            final int fromY = Math.min(line.a.y, line.b.y);
            final int toY = Math.max(line.a.y, line.b.y);
            for (int x = fromX; x <= toX; x++) {
                for (int y = fromY; y <= toY; y++) {
                    world[y][x]++;
                }
            }
        }
        if (line.isDiagonal()) {
            recordDiagonal(line.a, line.b);
        }
    }

    private void recordDiagonal(final Point a, final Point b) {
        if (a.x > b.x) {
            recordDiagonal(b, a);
        } else {
            if (a.y < b.y) {
                recordDiagonalYIncreasing(a, b);
            } else {
                recordDiagonalYDecreasing(a, b);
            }
        }
    }

    private void recordDiagonalYIncreasing(final Point pointA, final Point pointB) {
        for (int x = pointA.x; x <= pointB.x; ) {
            for (int y = pointA.y; y <= pointB.y; y++) {
                world[y][x]++;
                x++;
            }
        }
    }

    private void recordDiagonalYDecreasing(final Point pointA, final Point pointB) {
        for (int x = pointA.x; x <= pointB.x; ) {
            for (int y = pointA.y; y >= pointB.y; y--) {
                world[y][x]++;
                x++;
            }
        }
    }

    int countGreaterThan(final int threshold) {
        int count = 0;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (world[x][y] > threshold) {
                    count++;
                }
            }
        }
        return count;
    }
}
