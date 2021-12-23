package advent.day18;

import java.util.Objects;

public class Complex implements SnailFish {
    SnailFish left;
    SnailFish right;

    public Complex(final SnailFish left, final SnailFish right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public SnailFish add(final SnailFish snailFish) {
        return new Complex(this, snailFish);
    }

    @Override
    public SnailFish getExplosion(final int level) {
        return null;
    }

    @Override
    public String toString() {
        return String.format("[%s,%s]", left, right);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Complex)) {
            return false;
        }
        final Complex complex = (Complex) o;
        return Objects.equals(left, complex.left) && Objects.equals(right, complex.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
