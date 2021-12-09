package advent.day9;

public class EdgeReference {
    private final int xMax;
    private final int yMax;


    public EdgeReference(final int xMax, final int yMax) {
        this.xMax = xMax;
        this.yMax = yMax;
    }

    Mapper of(final int x, final int y) {
        return new Mapper(x, y, this);
    }

    static class Mapper {
        private final int x;
        private final int y;
        private final EdgeReference edgeReference;

        Mapper(final int x, final int y, final EdgeReference edgeReference) {
            this.x = x;
            this.y = y;
            this.edgeReference = edgeReference;
        }

        Mapper reposition(final int x, final int y) {
            return new Mapper(x, y, edgeReference);
        }

        boolean isTopEdge() {
            return y == 0;
        }

        boolean isBottomEdge() {
            return y == edgeReference.yMax;
        }

        boolean isRightEdge() {
            return x == edgeReference.xMax;
        }

        boolean isLeftEdge() {
            return x == 0;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
