package advent.day2;

public interface Coordinate {

    void down(final int amount);

    void up(final int amount);

    void forward(final int amount);

    int getArea();
}
