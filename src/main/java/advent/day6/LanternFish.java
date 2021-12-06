package advent.day6;

import java.util.Objects;

public class LanternFish {

    private int timerRemaining;

    LanternFish(final int timerRemaining) {
        this.timerRemaining = timerRemaining;
    }

    LanternFish liveADay() {
        timerRemaining--;
        if (timerRemaining < 0) {
            timerRemaining = 6;
            return new LanternFish(8);
        }
        return null;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LanternFish)) {
            return false;
        }
        final LanternFish that = (LanternFish) o;
        return timerRemaining == that.timerRemaining;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timerRemaining);
    }

    @Override
    public String toString() {
        return Integer.toString(timerRemaining);
    }
}
