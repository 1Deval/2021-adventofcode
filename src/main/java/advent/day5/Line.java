package advent.day5;

public class Line {
    final Point a;
    final Point b;

    public Line(final Point a, final Point b) {
        this.a = a;
        this.b = b;
    }

    boolean isHorizontal() {
        return a.x == b.x || a.y == b.y;
    }

    boolean isDiagonal() {
        return Math.abs(a.x - b.x) == Math.abs(a.y - b.y);
    }

    int max() {
        return Math.max(a.max, b.max);
    }
}
