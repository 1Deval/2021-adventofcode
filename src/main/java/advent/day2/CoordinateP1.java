package advent.day2;

public class CoordinateP1 implements Coordinate {
    private int horizontal;
    private int depth;

    @Override
    public void down(final int amount) {
        depth += amount;
    }

    @Override
    public void up(final int amount) {
        down(amount * -1);
    }

    @Override
    public void forward(final int amount) {
        horizontal += amount;
    }

    @Override
    public int getArea() {
        return horizontal * depth;
    }
}
