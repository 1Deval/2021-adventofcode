package advent.day18;

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
    public SnailFish explode(final int level) {
        return null;
    }

    @Override
    public SnailFish split() {
        if (value > 9) {
            return new Complex(new Literal(value / 2), new Literal(value - value / 2));
        } else {
            return this;
        }
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
