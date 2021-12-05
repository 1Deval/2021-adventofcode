package advent.day2;

import java.util.function.BiConsumer;

public enum Movement {
    down(Coordinate::down), up(Coordinate::up), forward(Coordinate::forward);

    private final BiConsumer<Coordinate, Integer> biConsumer;

    Movement(final BiConsumer<Coordinate, Integer> biConsumer) {
        this.biConsumer = biConsumer;
    }

    void move(final Coordinate coordinate, final int amount) {
        biConsumer.accept(coordinate, amount);
    }
}
