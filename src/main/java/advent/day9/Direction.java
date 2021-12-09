package advent.day9;

public enum Direction {
//    TOP_LEFT(-1, -1),
    TOP(0, -1),
//    TOP_RIGHT(1, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
//    BOTTOM_LEFT(-1, 1),
    BOTTOM(0, 1);
//    BOTTOM_RIGHT(1, 1);

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

    int getValue(final int[][] grid, final int ix, final int iy){
        return grid[ix+x][iy+y];
    }
}
