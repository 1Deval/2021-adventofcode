package advent.day18;

import java.util.Objects;

public class Literal implements SnailFish {
    int value;

    public Literal(final int value) {
        this.value = value;
    }

    @Override
    public SnailFish add(final SnailFish snailFish) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SnailFish getExplosion(final int level) {
        throw new UnsupportedOperationException();
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
