package advent.day8;

import java.util.List;

public class DataEntry {
    List<Digit> left;
    List<Digit> right ;

    public DataEntry(final List<Digit> left, final List<Digit> right) {
        this.left = left;
        this.right = right;
    }
}
