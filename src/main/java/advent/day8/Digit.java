package advent.day8;

import java.util.Collections;
import java.util.Set;

public class Digit {
    Set<Character> codes;
    int value;

    public Digit(final Set<Character> codes) {
        this.codes = Collections.unmodifiableSet(codes);
    }
}
