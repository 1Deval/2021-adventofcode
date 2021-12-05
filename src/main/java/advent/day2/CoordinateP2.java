package advent.day2;

public class CoordinateP2 implements Coordinate {
    private int horizontal;
    private int depth;
    private int aim;

    @Override
    public void down(final int amount) {
        aim += amount;
    }

    @Override
    public void up(final int amount) {
        down(amount * -1);
    }

    @Override
    public void forward(final int amount) {
        horizontal += amount;
        depth += aim * amount;
    }

    @Override
    public int getArea() {
        return horizontal * depth;
    }
}
