package advent.day11;

import advent.read.Util;
import advent.utils.Color;
import advent.utils.Pair;

import java.io.IOException;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day11/input.txt");
        final int[][] grid = getGrid(data);

        final int xMax = grid.length - 1;
        final int yMax = grid[0].length - 1;

        final EdgeReference edgeReference = new EdgeReference(xMax, yMax);
        int flashes = 0;
        int i = 0;
        while (!allZero(grid)) {
            flashes += step(grid, edgeReference);
            printGrid(grid, i);

            i++;
            if (i == 100) {
                System.out.println("P1:" + flashes);
            }
        }
        System.out.println("P2:" + Color.ANSI_CYAN.wrap(i));

    }

    private static boolean allZero(final int[][] grid) {
        for (int y = 0; y < grid[0].length; y++) {
            for (final int[] ints : grid) {
                if (ints[y] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void printGrid(final int[][] grid, final int i) {
        System.out.printf("after step %d%n", i + 1);
        for (int y = 0; y < grid[0].length; y++) {
            for (final int[] ints : grid) {
                final int val = ints[y];
                System.out.printf("%s ", val == 0 ? Color.ANSI_RED.wrap(Integer.toString(val)) : Integer.toString(val));
            }
            System.out.println();
        }
    }

    private static int step(final int[][] grid, final EdgeReference edgeReference) {
        final Set<Pair<Integer, Integer>> toBeFlashed = new HashSet<>();
        final Set<Pair<Integer, Integer>> flashed = new HashSet<>();

        increment(grid, toBeFlashed);
        while (!toBeFlashed.isEmpty()) {
            final Iterator<Pair<Integer, Integer>> toBeFlashedIterator = toBeFlashed.iterator();
            final Set<Pair<Integer, Integer>> newToBeFlashed = new HashSet<>();
            while (toBeFlashedIterator.hasNext()) {
                newToBeFlashed.addAll(flash(grid, edgeReference, toBeFlashedIterator.next(), flashed));
                toBeFlashedIterator.remove();
            }
            toBeFlashed.addAll(newToBeFlashed);
        }

        return flashed.size();
    }

    private static Set<Pair<Integer, Integer>> flash(final int[][] grid, final EdgeReference edgeReference, final Pair<Integer, Integer> target, final Set<Pair<Integer, Integer>> flashed) {
        if (flashed.contains(target)) {
            return Collections.emptySet();
        }
        final Integer x = target.getFirstValue();
        final Integer y = target.getSecondValue();
        grid[x][y] = 0;
        final Set<Pair<Integer, Integer>> newToBeFlashed = new HashSet<>();
        final Set<Direction> surroundingDirections = getSurroundingDirections(edgeReference.of(x, y));
        for (final Direction surroundingDirection : surroundingDirections) {
            final Pair<Integer, Integer> surrTarget = Pair.of(surroundingDirection.getX() + x, surroundingDirection.getY() + y);
            if (flashed.contains(surrTarget)) {
                continue;
            }
            final int currentValue = surroundingDirection.getValue(grid, x, y);
            final int newValue = currentValue + 1;
            surroundingDirection.setValue(grid, x, y, newValue);
            if (newValue > 9) {
                newToBeFlashed.add(surrTarget);
            }
        }

        flashed.add(target);
        return newToBeFlashed;
    }

    private static void increment(final int[][] grid, final Set<Pair<Integer, Integer>> toBeFlashed) {
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                ++grid[x][y];
                final int current = grid[x][y];
                final boolean isFlash = current > 9;
                if (isFlash) {
                    toBeFlashed.add(Pair.of(x, y));
                }
//                System.out.printf("%s%d ", isFlash ? "*" : " ", current);
            }
//            System.out.println("");
        }
    }

    private static int[][] getGrid(final List<String> data) {
        final int[][] grid = new int[data.get(0).length()][data.size()];
        for (int y = 0; y < data.size(); y++) {
            final char[] row = data.get(y).toCharArray();
            for (int x = 0; x < row.length; x++) {
                grid[x][y] = Integer.parseInt(String.valueOf(row[x]));
            }
        }
        return grid;
    }

    private static Set<Direction> getSurroundingDirections(final EdgeReference.Mapper mapper) {
        final Set<Direction> directionsToCheck = EnumSet.allOf(Direction.class);
        if (mapper.isTopEdge()) {
            directionsToCheck.remove(Direction.TOP);
            directionsToCheck.remove(Direction.TOP_LEFT);
            directionsToCheck.remove(Direction.TOP_RIGHT);
        } else if (mapper.isBottomEdge()) {
            directionsToCheck.remove(Direction.BOTTOM);
            directionsToCheck.remove(Direction.BOTTOM_LEFT);
            directionsToCheck.remove(Direction.BOTTOM_RIGHT);
        }
        if (mapper.isRightEdge()) {
            directionsToCheck.remove(Direction.RIGHT);
            directionsToCheck.remove(Direction.TOP_RIGHT);
            directionsToCheck.remove(Direction.BOTTOM_RIGHT);
        } else if (mapper.isLeftEdge()) {
            directionsToCheck.remove(Direction.LEFT);
            directionsToCheck.remove(Direction.TOP_LEFT);
            directionsToCheck.remove(Direction.BOTTOM_LEFT);
        }
        return directionsToCheck;
    }
}
