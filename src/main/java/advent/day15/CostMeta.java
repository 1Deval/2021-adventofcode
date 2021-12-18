package advent.day15;

import advent.utils.Pair;

import java.util.StringJoiner;

public class CostMeta {
    Pair<Integer, Integer> visitFrom;
    Integer visitCost;

    public CostMeta(final Pair<Integer, Integer> visitFrom, final Integer visitCost) {
        this.visitFrom = visitFrom;
        this.visitCost = visitCost;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CostMeta.class.getSimpleName() + "[", "]")
                .add("visitFrom=" + visitFrom)
                .add("visitCost=" + visitCost)
                .toString();
    }
}
