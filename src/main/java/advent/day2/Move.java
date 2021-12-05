package advent.day2;

public class Move {
    private final Movement movement;
    private final int amount;

    public Move(final Movement movement, final int amount) {
        this.movement = movement;
        this.amount = amount;
    }

    public Movement getMovement() {
        return movement;
    }

    public int getAmount() {
        return amount;
    }
}
