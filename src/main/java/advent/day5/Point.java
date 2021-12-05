package advent.day5;

public class Point {
    final int x;
    final int y;

    final int max;

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
        max = Math.max(x, y);
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
