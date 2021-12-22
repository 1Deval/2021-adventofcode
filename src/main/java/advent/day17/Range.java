package advent.day17;

public class Range {
    final int min;
    final int max;

    public Range(final int min, final int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return min + ".." + max;
    }
}
