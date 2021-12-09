package advent.day9;

import advent.read.Util;
import advent.utils.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day9/input.txt");
        final int[][] grid = getGrid(data);

        final Map<Pair<Integer, Integer>, Integer> lowPointsToBasin = new HashMap<>();
        int p1 = 0;
        final int xMax = grid.length - 1;
        final int yMax = grid[0].length - 1;

        final EdgeReference edgeReference = new EdgeReference(xMax, yMax);
        for (int y = 0; y < grid[0].length; y++) {

            for (int x = 0; x < grid.length; x++) {

                final boolean isMin = isMin(grid, edgeReference.of(x, y));

                if (isMin) {
                    p1 += grid[x][y] + 1;
                    lowPointsToBasin.put(Pair.of(x, y), 0);
                }
                System.out.printf("%s%d ", isMin ? "*" : " ", grid[x][y]);
            }
            System.out.println();
        }
        System.out.println(p1);

        for (final Map.Entry<Pair<Integer, Integer>, Integer> entry : lowPointsToBasin.entrySet()) {
            final Pair<Integer, Integer> origin = entry.getKey();
            final int basinSize = getBasinSize(grid, edgeReference.of(origin.getFirstValue(), origin.getSecondValue()), new HashSet<>());
            System.out.println(origin + " " + basinSize );
            lowPointsToBasin.put(origin, basinSize);
        }

        final List<Integer> basinSizes = new ArrayList<>(lowPointsToBasin.values());
        Collections.sort(basinSizes);
        Collections.reverse(basinSizes);

        System.out.println("P2: " + basinSizes.get(0) * basinSizes.get(1) * basinSizes.get(2));

    }

    private static int getBasinSize(final int[][] grid, final EdgeReference.Mapper directionMapper, final Set<Pair<Integer, Integer>> visited) {
        final int x = directionMapper.getX();
        final int y = directionMapper.getY();
        final Pair<Integer, Integer> current = new Pair<>(x, y);
        if (visited.contains(current)) {
            return 0;
        }
        visited.add(current);
        final Set<Direction> surroundingDirections = getSurroundingDirections(directionMapper);
        final List<Pair<Integer, Integer>> qualifiedSurrounds = surroundingDirections.stream().map(direction -> new Pair<>(x + direction.getX(), y + direction.getY()))
                .filter(pair -> grid[pair.getFirstValue()][pair.getSecondValue()] != 9)
                .collect(Collectors.toList());
        final long count = qualifiedSurrounds.stream()
                .map(pair -> getBasinSize(
                        grid,
                        directionMapper.reposition(pair.getFirstValue(), pair.getSecondValue()),
                        visited
                ))
                .mapToInt(value -> value).sum();
        return (int) (1 + count);
    }

    private static boolean isMin(final int[][] grid, final EdgeReference.Mapper mapper) {
        final int x = mapper.getX();
        final int y = mapper.getY();

        final Set<Direction> directionsToCheck = getSurroundingDirections(mapper);

        final int[] surroundings = directionsToCheck.stream().map(direction -> direction.getValue(grid, x, y)).mapToInt(value -> value).toArray();

        Arrays.sort(surroundings);
        final int current = grid[x][y];
        return current < surroundings[0];
    }

    private static Set<Direction> getSurroundingDirections(final EdgeReference.Mapper mapper) {
        final Set<Direction> directionsToCheck = EnumSet.allOf(Direction.class);
        if (mapper.isTopEdge()) {
            directionsToCheck.remove(Direction.TOP);
//            directionsToCheck.remove(Direction.TOP_LEFT);
//            directionsToCheck.remove(Direction.TOP_RIGHT);
        } else if (mapper.isBottomEdge()) {
            directionsToCheck.remove(Direction.BOTTOM);
//            directionsToCheck.remove(Direction.BOTTOM_LEFT);
//            directionsToCheck.remove(Direction.BOTTOM_RIGHT);
        }
        if (mapper.isRightEdge()) {
            directionsToCheck.remove(Direction.RIGHT);
//            directionsToCheck.remove(Direction.TOP_RIGHT);
//            directionsToCheck.remove(Direction.BOTTOM_RIGHT);
        } else if (mapper.isLeftEdge()) {
            directionsToCheck.remove(Direction.LEFT);
//            directionsToCheck.remove(Direction.TOP_LEFT);
//            directionsToCheck.remove(Direction.BOTTOM_LEFT);
        }
        return directionsToCheck;
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
}
