package advent.day19;

import advent.read.Util;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day19/sample.txt");

        final List<Scanner> scanners = parseScanners(data);

        final Multiset<Coordinate> counts = HashMultiset.create();
        for (int i = 0; i < scanners.size(); i++) {
            for (int j = 1; j < scanners.size(); j++) {
                if (i != j) {
//                    Map<Coordinate, Coordinate> scanner1Map = new HashMap<>();
                    final Scanner scanner1 = scanners.get(i);
                    final Scanner scanner2 = scanners.get(j);
                    final List<Scanner> variations = scanner1.getVariations();
                    for (int variation1index = 0; variation1index < variations.size(); variation1index++) {
                        final Scanner variation1 = variations.get(variation1index);
                        final List<Scanner> scanner2Variations = scanner2.getVariations();
                        for (int variation2Index = 0; variation2Index < scanner2Variations.size(); variation2Index++) {
                            final Scanner variation2 = scanner2Variations.get(variation2Index);
                            for (final Coordinate coordinate1 : variation1.coordinates) {
                                for (final Coordinate coordinate2 : variation2.coordinates) {
                                    final Coordinate difference = coordinate1.difference(coordinate2);
                                    counts.add(difference);
//                                    scanner1Map.putIfAbsent(difference, coordinate1);
                                }
                            }
                        }
                    }
                    dropBottom(counts);
//                    scanner1Map.keySet().retainAll(counts.elementSet());
                    System.out.println(counts);
//                    System.out.println(scanner1Map);
                    counts.clear();
                }
            }
        }
        for (final Multiset.Entry<Coordinate> coordinateEntry : counts.entrySet()) {
            System.out.println("element" + coordinateEntry.getElement());
            for (final Coordinate coordinate : scanners.get(0).coordinates) {
                System.out.println(coordinate.difference(coordinateEntry.getElement()));
            }
            System.out.println("---");
        }
//        System.out.println(counts);
    }

    private static void dropBottom(final Multiset<Coordinate> counts) {
        final int maxCount = counts.entrySet().stream().mapToInt(Multiset.Entry::getCount).max().getAsInt();
        counts.removeIf(coordinate -> counts.count(coordinate) < maxCount);
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
