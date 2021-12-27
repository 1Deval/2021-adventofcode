package advent.day19;

import advent.utils.Pair;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Scanner {
    final List<Coordinate> coordinates;

    Scanner(final List<Coordinate> coordinates) {
        this.coordinates = Collections.unmodifiableList(coordinates);
    }


    List<Scanner> getVariations() {
        final int variationCount = 24;
        final List<List<Coordinate>> varyingCoordinates = new ArrayList<>();
        // init
        for (int i = 0; i < variationCount; i++) {
            varyingCoordinates.add(new ArrayList<>());
        }

        for (final Coordinate coordinate : coordinates) {
            final List<Coordinate> variations = getCoordinates(coordinate);
            for (int vi = 0; vi < variations.size(); vi++) {
                varyingCoordinates.get(vi).add(variations.get(vi));
            }
        }
        final List<Scanner> varyingScanners = varyingCoordinates.stream().map(Scanner::new).collect(Collectors.toList());
        return varyingScanners;
    }

    private static List<Coordinate> getCoordinates(final Coordinate coordinate) {
        final List<Coordinate> coordinates = new ArrayList<>();
        final int x = coordinate.x;
        final int y = coordinate.y;
        final int z = coordinate.z;
        coordinates.addAll(shuffle(x, y, z));
        coordinates.addAll(shuffle(x, y, -1 * z));
        coordinates.addAll(shuffle(x, -1 * y, z));
        coordinates.addAll(shuffle(-1 * x, y, z));
        coordinates.addAll(shuffle(-1 * x, y, -1 * z));
        coordinates.addAll(shuffle(-1 * x, -1 * y, -1 * z));
        return coordinates;
    }

    private static List<Coordinate> shuffle(final int x, final int y, final int z) {
        final List<Coordinate> coordinates = new ArrayList<>();
        coordinates.add(new Coordinate(x, y, z));
        coordinates.add(new Coordinate(x, z, y));
        coordinates.add(new Coordinate(z, y, x));
        coordinates.add(new Coordinate(y, z, x));


//        coordinates.add(new Coordinate(y,x, z));
//        coordinates.add(new Coordinate(z, x, y));

        return coordinates;

//        System.out.printf("%s,%s,%s%n", x, y, z);
//        System.out.printf("%s,%s,%s%n", y, x, z);
//        System.out.printf("%s,%s,%s%n", y, z, x);
//        System.out.printf("%s,%s,%s%n", x, z, y);
//        System.out.printf("%s,%s,%s%n", z, x, y);
//        System.out.printf("%s,%s,%s%n", z, y, x);
    }

    Scanner findBeacons(final Scanner underComparison) {
        final Set<Coordinate> foundBeacons = new TreeSet<>(coordinates);

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
//                System.out.println(countsLocal);
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
//                if (foundBeacons.contains(relativeToBase)) {
//                    System.out.println(relativeToBase);
//                }
                foundBeacons.add(relativeToBase);
            }
        } else {
//            System.err.println("No poll");
            return null;
        }
//        System.out.println("coordinate poll for " + i + " " + poll);

//        System.out.println(foundBeacons.size());
        return new Scanner(new ArrayList<>(foundBeacons));
    }


    private static void dropBottom(final Multiset<Coordinate> counts) {
        counts.removeIf(coordinate -> {
            final int count = counts.count(coordinate);
            return count < 12;
        });
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Scanner.class.getSimpleName() + "[", "]")
                .add("coordinates=" + coordinates)
                .toString();
    }
}
