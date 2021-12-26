package advent.day18;

import advent.utils.Pair;

import java.util.Objects;

public class Literal implements SnailFish {
    int value;

    public Literal(final int value) {
        this.value = value;
    }

    @Override
    public SnailFish add(final SnailFish snailFish) {
        if (!(snailFish instanceof Literal)) {
            throw new UnsupportedOperationException();
        }
        return new Literal(value + ((Literal) snailFish).value);
    }

    @Override
    public SnailFish explode(final int level, final int depthLimit) {
        return null;
    }

    @Override
    public Pair<SnailFish, Boolean> split() {
        if (value > 9) {
            return Pair.of(new Complex(new Literal(value / 2), new Literal(value - value / 2)), true);
        } else {
            return Pair.of(this, false);
        }
    }

    @Override
    public String coloredString(final int level) {
        return toString();
    }

    @Override
    public long magnitude() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Literal)) {
            return false;
        }
        final Literal literal = (Literal) o;
        return value == literal.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
