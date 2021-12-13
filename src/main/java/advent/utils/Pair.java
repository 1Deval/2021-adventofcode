package advent.utils;

import java.text.MessageFormat;
import java.util.Objects;

public final class Pair<F, S> {

    private final F firstValue;
    private final S secondValue;
    private static final String TO_STRING = "Pair [{0}, {1}]";
    private final int hash = Integer.MAX_VALUE;

    /**
     * Construct a Pair using the given values
     */
    public Pair(final F firstValue, final S secondValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
    }

    public F getFirstValue() {
        return firstValue;
    }

    public S getSecondValue() {
        return secondValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pair)) {
            return false;
        }
        final Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(firstValue, pair.firstValue) && Objects.equals(secondValue, pair.secondValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstValue, secondValue);
    }

    @Override
    public String toString() {
        return MessageFormat.format(TO_STRING, getFirstValue(), getSecondValue());
    }

    public static <A, B> Pair<A, B> of(final A firstValue, final B secondValue) {
        return new Pair<>(firstValue, secondValue);
    }
}
