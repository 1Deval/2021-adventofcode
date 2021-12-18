package advent.day15;

import advent.read.Util;
import advent.utils.Color;
import advent.utils.Pair;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day15/input.txt");
        final int[][] grid = getGrid(data);

        final int xMax = grid.length - 1;
        final int yMax = grid[0].length - 1;

        final EdgeReference edgeReference = new EdgeReference(xMax, yMax);

        printGrid(grid);
        final Set<Pair<Integer, Integer>> points = convertToPoints(grid);
        final Map<Pair<Integer, Integer>, CostMeta> pairCost = new HashMap<>();
        final Pair<Integer, Integer> start = Pair.of(0, 0);
        pairCost.put(start, new CostMeta(null, 0));
        final HashSet<Pair<Integer, Integer>> visited = new HashSet<>();
//        final List<Pair<Integer, Integer>> toVisit = new ArrayList<>();
        final PriorityQueue<Pair<Integer, Integer>> toVisit = new PriorityQueue<>(Comparator.comparing(integerIntegerPair -> pairCost.get(integerIntegerPair).visitCost));
        toVisit.add(start);
        while (!toVisit.isEmpty()) {
            final Set<Pair<Integer, Integer>> surround = fill(toVisit.poll(), visited, edgeReference, pairCost, grid);
            surround.removeAll(visited);
            toVisit.forEach(surround::remove);
            toVisit.addAll(surround);
        }

        System.out.println(pairCost.get(Pair.of(xMax, yMax)));
        printPath(grid, xMax, yMax, pairCost);
    }

    private static Set<Pair<Integer, Integer>> fill(final Pair<Integer, Integer> point,
                                                    final Set<Pair<Integer, Integer>> visited,
                                                    final EdgeReference edgeReference,
                                                    final Map<Pair<Integer, Integer>, CostMeta> pairCost,
                                                    final int[][] grid) {
        if (visited.contains(point)) {
            return Collections.emptySet();
        }
        final Integer x = point.getFirstValue();
        final Integer y = point.getSecondValue();
        final Set<Direction> surroundingDirections = getSurroundingDirections(edgeReference.of(x, y));
        visited.add(point);
        final CostMeta currentPointCost = pairCost.get(point);
        for (final Direction surroundingDirection : surroundingDirections) {
            final Pair<Integer, Integer> surroundPoint = Pair.of(x + surroundingDirection.getX(), y + surroundingDirection.getY());
            pairCost.compute(surroundPoint, (integerIntegerPair, costMeta) -> {
                final int possibleCost = grid[surroundPoint.getFirstValue()][surroundPoint.getSecondValue()] + currentPointCost.visitCost;
                if (costMeta == null || possibleCost < costMeta.visitCost) {
                    return new CostMeta(point, possibleCost);
                } else {
                    return costMeta;
                }
            });
        }
        return surroundingDirections.stream().map(direction -> Pair.of(x + direction.getX(), y + direction.getY())).collect(Collectors.toSet());
    }

    private static void printPath(final int[][] grid, final int xMax, final int yMax, final Map<Pair<Integer, Integer>, CostMeta> pairCost) {
        final Map<Pair<Integer, Integer>, Integer> pathCost = new HashMap<>();
        Pair<Integer, Integer> tpt = Pair.of(xMax, yMax);
        do {
            final CostMeta tcm = pairCost.get(tpt);
            pathCost.put(tpt, tcm.visitCost);
            tpt = tcm.visitFrom;
        } while (tpt != null);
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                final int val = grid[x][y];
                System.out.printf("%s ", pathCost.containsKey(Pair.of(x, y)) ? Color.ANSI_RED.wrap(val) : Integer.toString(val));
            }
            System.out.println();
        }
    }


    private static Set<Pair<Integer, Integer>> convertToPoints(final int[][] grid) {
        final Set<Pair<Integer, Integer>> points = new HashSet<>();
        for (int y = 0; y < grid[0].length; y++) {
            for (int x = 0; x < grid.length; x++) {
                points.add(Pair.of(x, y));
            }
        }
        return points;
    }


    private static void printGrid(final int[][] grid) {
        for (int y = 0; y < grid[0].length; y++) {
            for (final int[] ints : grid) {
                final int val = ints[y];
                System.out.printf("%s ", val == 0 ? Color.ANSI_RED.wrap(Integer.toString(val)) : Integer.toString(val));
            }
            System.out.println();
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
        } else if (mapper.isBottomEdge()) {
            directionsToCheck.remove(Direction.BOTTOM);
        }
        if (mapper.isRightEdge()) {
            directionsToCheck.remove(Direction.RIGHT);
        } else if (mapper.isLeftEdge()) {
            directionsToCheck.remove(Direction.LEFT);
        }
        return directionsToCheck;
    }
}
