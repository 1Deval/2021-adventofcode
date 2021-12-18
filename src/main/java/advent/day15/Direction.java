package advent.day15;

public enum Direction {
    TOP(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    BOTTOM(0, 1);

    private final int x;
    private final int y;

    Direction(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    int getValue(final int[][] grid, final int ix, final int iy) {
        return grid[ix + x][iy + y];
    }

    void setValue(final int[][] grid, final int ix, final int iy, final int value) {
        grid[ix + x][iy + y] = value;
    }
}
