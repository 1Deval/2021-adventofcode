package advent.day18;

import advent.utils.Pair;

interface SnailFish {
    SnailFish add(SnailFish snailFish);

    SnailFish explode(int level, int depthLimit);

    default SnailFish explode() {
        return explode(0, 3);
    }

    default SnailFish reduce() {
        String before;
        do {
            before = toString();
            final SnailFish explode = explode();
            if (explode != null) {
                continue;
            }
            split();
        } while (!before.equals(toString()));
        return this;
    }

    Pair<SnailFish, Boolean> split();

    String coloredString(int level);

    default String coloredString() {
        return coloredString(0);
    }

    long magnitude();
}
