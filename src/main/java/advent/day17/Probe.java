package advent.day17;

public class Probe {
    int x;
    int y;
    int xVelocity;
    int yVelocity;
    final Range xRange;
    final Range yRange;

    public Probe(final int xVelocity, final int yVelocity, final Range xRange, final Range yRange) {
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.xRange = xRange;
        this.yRange = yRange;
    }

    enum Bounds {
        OUT, IN, UNKNOWN
    }

    Bounds step() {
        x += xVelocity;
        y += yVelocity;
        if (xVelocity != 0) {
            xVelocity --;
        }
        yVelocity--;

        if (x > xRange.max || y < yRange.min) {
            return Bounds.OUT;
        }
        if (x >= xRange.min && y <= yRange.max) {
            return Bounds.IN;
        }
        return Bounds.UNKNOWN;
    }
}
