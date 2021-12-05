package advent.day4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    final int[][] data;
    final boolean[][] called;
    private final int size;
    private int rowFillIndex;
    private final Map<Integer, List<Coordinate>> index;
    int remainingCallSum;

    Board(final int size) {
        data = new int[size][size];
        called = new boolean[size][size];
        this.size = size;
        rowFillIndex = 0;
        index = new HashMap<>();
    }

    void fill(final int[] row) {
        data[rowFillIndex] = row;
        for (int c = 0; c < row.length; c++) {
            index.computeIfAbsent(row[c], integer -> new ArrayList<>()).add(new Coordinate(rowFillIndex, c));
        }
        rowFillIndex++;
        remainingCallSum += Arrays.stream(row).asLongStream().sum();
    }

    Integer call(final int call) {
        final List<Coordinate> coordinates = index.get(call);
        if (coordinates == null) {
            return null;
        } else {
            for (final Coordinate coordinate : coordinates) {
                called[coordinate.row][coordinate.column] = true;
                remainingCallSum -= call;
                if (winByRow(coordinate.row) || winByColumn(coordinate.column)) {
//                    System.out.printf("found for %d %d r %d c %d%n%s ", call, remainingCallSum, coordinate.row, coordinate.column, this);
                    return remainingCallSum * call;
                }
            }
        }

        return null;
    }

    private boolean winByColumn(final int column) {
        for (int i = 0; i < size; i++) {
            if (!called[i][column]) {
                return false;
            }
        }
        return true;
    }

    private boolean winByRow(final int row) {
        for (final boolean c : called[row]) {
            if (!c) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int r = 0; r < data.length; r++) {
            for (int c = 0; c < data[r].length; c++) {
                final String val = called[r][c] ? "\tt" : "\tf";
                builder.append(val).append(data[r][c]);
            }
            builder.append(System.lineSeparator());
        }

        return builder.toString();
    }

    static class Coordinate {
        final int row, column;

        Coordinate(final int row, final int column) {
            this.row = row;
            this.column = column;
        }
    }
}
