package advent.day19;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;
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

    @Override
    public String toString() {


        return new StringJoiner(", ", Scanner.class.getSimpleName() + "[", "]")
                .add("coordinates=" + coordinates)
                .toString();
    }
}
