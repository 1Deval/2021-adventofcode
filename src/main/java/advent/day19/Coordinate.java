package advent.day19;

import java.util.Objects;

public class Coordinate implements Comparable<Coordinate> {
    final int x, y, z;

    public Coordinate(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return String.format("[%s,%s,%s]", x, y, z);
    }

    Coordinate difference(final Coordinate coordinate) {
        return new Coordinate(x - coordinate.x, y - coordinate.y, z - coordinate.z);
    }

    Coordinate add(final Coordinate coordinate) {
        return new Coordinate(x + coordinate.x, y + coordinate.y, z + coordinate.z);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Coordinate)) {
            return false;
        }
        final Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }


    @Override
    public int compareTo(final Coordinate o) {
        return x - o.x;
    }
}
