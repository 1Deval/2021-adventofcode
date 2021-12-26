package advent.day18;

import advent.utils.Pair;

interface SnailFish {
    SnailFish add(SnailFish snailFish);

    SnailFish explode(int level, int depthLimit);

    default SnailFish explode() {
//        System.out.println("limit: " + depthLimit);
        return explode(0, 3);
    }

    Pair<SnailFish, Boolean> split();

    String coloredString(int level);

    default String coloredString() {
        return coloredString(0);
    }

    long magnitude();
}
