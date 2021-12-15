package advent.day13;

import advent.read.Util;
import advent.utils.Color;
import advent.utils.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    private static final Pattern FOLD_INSTRUCTION_PATTERN = Pattern.compile("fold along (?<direction>[xy])=(?<index>\\d+)");

    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day13/input.txt");

        final Set<Pair<Integer, Integer>> rawGridPoints = getPoints(data.stream().filter(s -> s.contains(",")).collect(Collectors.toList()));

        final List<Pair<FoldDirection, Integer>> foldInstructions = data.stream()
                .map(FOLD_INSTRUCTION_PATTERN::matcher)
                .filter(Matcher::matches)
                .map(matcher -> Pair.of("x".equals(matcher.group("direction")) ? FoldDirection.vertical : FoldDirection.horizontal, Integer.parseInt(matcher.group("index"))))
                .collect(Collectors.toList());
        System.out.println(foldInstructions);
        for (final Pair<FoldDirection, Integer> foldInstruction : foldInstructions) {
            final Set<Pair<Integer, Integer>> outside = new HashSet<>();
            final Iterator<Pair<Integer, Integer>> pointIterator = rawGridPoints.iterator();
            while (pointIterator.hasNext()) {
                final Pair<Integer, Integer> pt = pointIterator.next();
                final int foldLine = foldInstruction.getSecondValue();
                switch (foldInstruction.getFirstValue()) {
                    case horizontal:
                        if (pt.getSecondValue() > foldLine) {
                            pointIterator.remove();
                            outside.add(Pair.of(pt.getFirstValue(), foldLine - (pt.getSecondValue() - foldLine)));
                        }
                        break;
                    case vertical:
                        if (pt.getFirstValue() > foldLine) {
                            pointIterator.remove();
                            outside.add(Pair.of(foldLine - (pt.getFirstValue() - foldLine), pt.getSecondValue()));
                        }
                        break;
                }
            }
            rawGridPoints.addAll(outside);

        }


        final Pair<Integer, Integer> boundaries = findMax(rawGridPoints);
        final boolean[][] grid = initGrid(rawGridPoints, boundaries);
        printGrid(grid);

        System.out.println(rawGridPoints.size());


    }

    private static void printGrid(final boolean[][] grid) {
        for (int y = 0; y < grid[0].length; y++) {
            for (final boolean[] ints : grid) {
                final boolean val = ints[y];
                System.out.printf("%s ", val ? Color.ANSI_RED.wrap("#") : ".");
            }
            System.out.println();
        }
    }

    private static boolean[][] initGrid(final Set<Pair<Integer, Integer>> rawGridPoints, final Pair<Integer, Integer> boundaries) {
        final boolean[][] grid = new boolean[boundaries.getFirstValue() + 1][boundaries.getSecondValue() + 1];
        for (final Pair<Integer, Integer> point : rawGridPoints) {
            grid[point.getFirstValue()][point.getSecondValue()] = true;
        }
        return grid;
    }

    private static Set<Pair<Integer, Integer>> getPoints(final List<String> data) {
        return data.stream()
                .map(s -> s.split(","))
                .map(strings -> Pair.of(Integer.parseInt(strings[0]), Integer.parseInt(strings[1])))
                .collect(Collectors.toSet());
    }

    private static Pair<Integer, Integer> findMax(final Set<Pair<Integer, Integer>> rawGridPoints) {
        int xMax = 0;
        int yMax = 0;
        for (final Pair<Integer, Integer> rawGridPoint : rawGridPoints) {
            xMax = Math.max(rawGridPoint.getFirstValue(), xMax);
            yMax = Math.max(rawGridPoint.getSecondValue(), yMax);
        }
        return Pair.of(xMax, yMax);
    }
}
