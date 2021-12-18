package advent.day15;

public class InfiniteGrid {
    final int[][] grid;
    final int xWidth;
    final int yWidth;
    final int gridBase;

    public InfiniteGrid(final int[][] grid) {
        this.grid = grid.clone();
        xWidth = grid.length;
        yWidth = grid[0].length;
        gridBase = xWidth;
    }

    int getValue(final int x, final int y) {
        return (grid[x % xWidth][y % yWidth] - 1 + x / xWidth + y / yWidth) % 9 + 1;
    }
}
