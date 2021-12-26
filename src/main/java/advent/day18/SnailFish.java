package advent.day18;

interface SnailFish {
    SnailFish add(SnailFish snailFish);

    SnailFish explode(int level);

    default SnailFish explode() {
        final SnailFish explode = explode(0);
        System.out.println("intf" + explode);
        return explode;
    }

    SnailFish split();

    String coloredString(int level);

    default String coloredString() {
        return coloredString(0);
    }
}
