package advent.day19;

import advent.read.Util;
import advent.utils.Pair;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public class Main {


    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day19/sample.txt");

        final List<Scanner> scanners = parseScanners(data);


        final Scanner base = scanners.get(0);
        final Set<Coordinate> foundBeacons = new TreeSet<>(base.coordinates);
        for (int i = 1; i < scanners.size(); i++) {
            final Scanner underComparison = scanners.get(i);
            final List<Scanner> variations = underComparison.getVariations();

            final PriorityQueue<Pair<Integer, Pair<Coordinate, Integer>>> likelyVariation = new PriorityQueue<>(Comparator.<Pair<Integer, Pair<Coordinate, Integer>>, Integer>comparing(integerPairPair -> integerPairPair.getSecondValue().getSecondValue()).reversed());

            for (int variationIndex = 0; variationIndex < variations.size(); variationIndex++) {
                final Multiset<Coordinate> countsLocal = HashMultiset.create();
                for (final Coordinate coordinateBase : foundBeacons) {
                    for (final Coordinate coordinate2 : variations.get(variationIndex).coordinates) {
                        final Coordinate difference = coordinateBase.difference(coordinate2);
                        countsLocal.add(difference);
                    }
                }
                dropBottom(countsLocal);
                if (!countsLocal.entrySet().isEmpty()) {
                    System.out.println(countsLocal);
                    for (final Multiset.Entry<Coordinate> coordinateEntry : countsLocal.entrySet()) {
                        likelyVariation.add(Pair.of(variationIndex, Pair.of(coordinateEntry.getElement(), coordinateEntry.getCount())));
                    }
                }
            }

            final Pair<Integer, Pair<Coordinate, Integer>> poll = likelyVariation.poll();
            final Scanner variationFound;
            if (poll != null) {
                variationFound = variations.get(poll.getFirstValue());
//            foundBeacons.retainAll(variationFound.coordinates.stream().map(coordinate -> coordinate.add(poll.getSecondValue().getFirstValue())).collect(Collectors.toList()));
                for (final Coordinate coordinate : variationFound.coordinates) {
                    final Coordinate relativeToBase = coordinate.add(poll.getSecondValue().getFirstValue());
                    if (foundBeacons.contains(relativeToBase)) {
                        System.out.println(relativeToBase);
                    }
                    foundBeacons.add(relativeToBase);
                }
            } else {
                System.err.println("No poll");
            }
            System.out.println("coordinate poll for " + i + " " + poll);

            System.out.println(foundBeacons.size());
        }

//        for (final String str : foundBeacons.stream().map(Coordinate::toString).collect(Collectors.toList())) {
//            System.out.println(str);
//        }

//        for (final String str : foundBeacons.stream().sorted().map(Coordinate::toString).collect(Collectors.toList())) {
//            System.out.println(str);
//        }

    }

    private static void dropBottom(final Multiset<Coordinate> counts) {
        final int maxCount = counts.entrySet().stream().mapToInt(Multiset.Entry::getCount).max().getAsInt();
        counts.removeIf(coordinate -> {
            final int count = counts.count(coordinate);
            return count <3 || count < maxCount;
        });
    }

    private static List<Scanner> parseScanners(final List<String> data) {
        final List<Scanner> scanners = new ArrayList<>();
        List<Coordinate> scannerBuild = null;
        for (final String line : data) {
            if (line.startsWith("---")) {
                if (scannerBuild != null) {
                    scanners.add(new Scanner(scannerBuild));
                }
                scannerBuild = new ArrayList<>();
            } else if (!line.isEmpty()) {
                final String[] split = line.split(",");
                Objects.requireNonNull(scannerBuild).add(new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
            }
        }
        scanners.add(new Scanner(scannerBuild));
        return scanners;
    }


}
