package advent.day19;

import advent.read.Util;
import com.google.common.collect.Multiset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Main {


    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day19/input.txt");

        final List<Scanner> scanners = parseScanners(data);


        final Scanner base = scanners.get(0);

        System.out.println(scanners.get(0).coordinates.size());
        visit(scanners);
        visit(scanners);
        final Map<Integer, Integer> visitMap = visit(scanners);
        for (int i = 1; i < scanners.size(); i++) {
            if (visitMap.containsKey(i)) {
                final int to = visitMap.get(i);
                final Scanner beacons = scanners.get(to).findBeacons(scanners.get(i));
                if (beacons != null) {
                    scanners.set(to, beacons);
                }
            } else {
                System.out.println("not found " + i);
            }
        }

        for (int i = 1; i < scanners.size(); i++) {
            if (visitMap.containsKey(i)) {
                final int to = visitMap.get(i);
                final Scanner beacons = scanners.get(to).findBeacons(scanners.get(i));
                if (beacons != null) {
                    scanners.set(to, beacons);
                }
            } else {
                System.out.println("not found " + i);
            }
        }
        for (int i = 1; i < scanners.size(); i++) {
            if (visitMap.containsKey(i)) {
                final int to = visitMap.get(i);
                final Scanner beacons = scanners.get(to).findBeacons(scanners.get(i));
                if (beacons != null) {
                    scanners.set(to, beacons);
                }
            } else {
                System.out.println("not found " + i);
            }
        }

        System.out.println(scanners.get(0).coordinates.size());


//        System.out.println(base.findBeacons(scanners.get(1).findBeacons(scanners.get(4))).findBeacons(scanners.get(2)).findBeacons(scanners.get(3)).coordinates.size());

//        System.out.println(base.coordinates.size());

//        System.out.println(findBeacons(base, Collections.singletonList(0), scanners).coordinates.size());


//        System.out.println(scanners.get(1).findBeacons(scanners.get(4)).coordinates.size());

//        final Set<Coordinate> foundBeacons = new TreeSet<>(base.coordinates);
//        for (int i = 1; i < scanners.size(); i++) {
//            final Scanner underComparison = scanners.get(i);
//            final List<Scanner> variations = underComparison.getVariations();
//
//            final PriorityQueue<Pair<Integer, Pair<Coordinate, Integer>>> likelyVariation = new PriorityQueue<>(Comparator.<Pair<Integer, Pair<Coordinate, Integer>>, Integer>comparing(integerPairPair -> integerPairPair.getSecondValue().getSecondValue()).reversed());
//
//            for (int variationIndex = 0; variationIndex < variations.size(); variationIndex++) {
//                final Multiset<Coordinate> countsLocal = HashMultiset.create();
//                for (final Coordinate coordinateBase : foundBeacons) {
//                    for (final Coordinate coordinate2 : variations.get(variationIndex).coordinates) {
//                        final Coordinate difference = coordinateBase.difference(coordinate2);
//                        countsLocal.add(difference);
//                    }
//                }
//                dropBottom(countsLocal);
//                if (!countsLocal.entrySet().isEmpty()) {
//                    System.out.println(countsLocal);
//                    for (final Multiset.Entry<Coordinate> coordinateEntry : countsLocal.entrySet()) {
//                        likelyVariation.add(Pair.of(variationIndex, Pair.of(coordinateEntry.getElement(), coordinateEntry.getCount())));
//                    }
//                }
//            }
//
//            final Pair<Integer, Pair<Coordinate, Integer>> poll = likelyVariation.poll();
//            final Scanner variationFound;
//            if (poll != null) {
//                variationFound = variations.get(poll.getFirstValue());
////            foundBeacons.retainAll(variationFound.coordinates.stream().map(coordinate -> coordinate.add(poll.getSecondValue().getFirstValue())).collect(Collectors.toList()));
//                for (final Coordinate coordinate : variationFound.coordinates) {
//                    final Coordinate relativeToBase = coordinate.add(poll.getSecondValue().getFirstValue());
//                    if (foundBeacons.contains(relativeToBase)) {
//                        System.out.println(relativeToBase);
//                    }
//                    foundBeacons.add(relativeToBase);
//                }
//            } else {
//                System.err.println("No poll");
//            }
//            System.out.println("coordinate poll for " + i + " " + poll);
//
//            System.out.println(foundBeacons.size());
//        }

//        for (final String str : foundBeacons.stream().map(Coordinate::toString).collect(Collectors.toList())) {
//            System.out.println(str);
//        }

//        for (final String str : foundBeacons.stream().sorted().map(Coordinate::toString).collect(Collectors.toList())) {
//            System.out.println(str);
//        }

    }

    private static Map<Integer, Integer> visit(final List<Scanner> scanners) {
        final HashSet<Integer> visited = new HashSet<>();
        final PriorityQueue<Integer> toVisit = new PriorityQueue<>();
        toVisit.add(0);
        final Map<Integer, Integer> visitMap = new HashMap<>();
        while (!toVisit.isEmpty()) {
            final Set<Integer> surround = fill(toVisit.poll(), visited, scanners, visitMap);
            surround.removeAll(visited);
            toVisit.forEach(surround::remove);
            toVisit.addAll(surround);
        }
        System.out.println(visitMap);
        System.out.println(visitMap.size());
        return visitMap;
    }

    private static Set<Integer> fill(final Integer poll, final HashSet<Integer> visited, final List<Scanner> scanners, final Map<Integer, Integer> visitMap) {
        if (visited.contains(poll)) {
            return Collections.emptySet();
        }
        visited.add(poll);
        final Set<Integer> surround = new HashSet<>();
        for (int i = 0; i < scanners.size(); i++) {
            if (i != poll && !visitMap.containsKey(i)) {
                final Scanner beacons = scanners.get(poll).findBeacons(scanners.get(i));
                if (beacons != null) {
                    visitMap.put(i, poll);
                    surround.add(i);
                    scanners.set(poll, beacons);
                }
            }
        }

        return surround;
    }

    private static Scanner findBeacons(final Scanner base, final List<Integer> visited, final List<Scanner> scanners) {
        Scanner localBase = base;
        final ArrayList<Integer> localVisited = new ArrayList<>(visited);
        for (int i = 0; i < scanners.size(); i++) {
            if (localVisited.contains(i)) {
                continue;
            }
            final Scanner beacons = localBase.findBeacons(scanners.get(i));
            if (beacons != null) {
                localVisited.add(i);
                localBase = beacons;
            } else {
                final Scanner beacons1 = findBeacons(scanners.get(i), localVisited, scanners);
                if (beacons1 != null) {
                    final Scanner beacons2 = localBase.findBeacons(beacons1);
                    if (beacons2 != null) {
                        localBase = beacons2;
                        localVisited.add(i);
                    }
                }
            }
        }

        return localBase;
    }

    private static Scanner findBeacons(final Scanner base, final List<Scanner> scanners) {
        if (scanners.isEmpty()) {
            return base;
        }
        Scanner localBase = base;
        final ArrayList<Scanner> localScanners = new ArrayList<>(scanners);
        final ArrayList<Scanner> notFound = new ArrayList<>();
        for (final Scanner localScanner : localScanners) {
            final Scanner beacons = localBase.findBeacons(localScanner);
            if (beacons == null) {
                notFound.add(localScanner);
            } else {
                localBase = beacons;
            }
        }

        final ArrayList<Scanner> prep = new ArrayList<>(notFound);
        for (final Scanner scanner : scanners) {
            prep.remove(scanner);
            final Scanner beacons = findBeacons(scanner, prep);
            if (beacons != null) {
                final Scanner resp = localBase.findBeacons(beacons);
                if (resp != null) {
                    localBase = resp;
                }
            }
        }
        return localBase;
    }

    private static void dropBottom(final Multiset<Coordinate> counts) {
        counts.removeIf(coordinate -> {
            final int count = counts.count(coordinate);
            return count < 12;
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
