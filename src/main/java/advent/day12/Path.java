package advent.day12;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.List;

public class Path {

    private final Multiset<String> visitedSmallCaves;
    private final List<String> path;

    public Path() {
        this(HashMultiset.create(), new ArrayList<>());
    }


    private Path(final Multiset<String> visitedSmallCaves, final List<String> path) {
        this.visitedSmallCaves = visitedSmallCaves;
        this.path = path;
    }

    boolean canVisitP1(final String node) {
        return !visitedSmallCaves.contains(node);
    }

    boolean canVisitP2(final String node) {
        if ("start".equals(node) || "end".equals(node)) {
            return !visitedSmallCaves.contains(node);
        } else {
            if (visitedSmallCaves.count(node) == 0) {
                return true;
            } else {
                final boolean anyTwice = visitedSmallCaves.entrySet()
                        .stream()
                        .filter(entry -> !("start".equals(entry.getElement()) || "end".equals(entry.getElement())))
                        .filter(entry -> !entry.getElement().equals(node))
                        .anyMatch(entry -> entry.getCount() == 2);
                return (anyTwice && !visitedSmallCaves.contains(node)) || (!anyTwice && visitedSmallCaves.count(node) < 2);
            }
        }
    }

    void visit(final String node) {
        path.add(node);
        if (Character.isLowerCase(node.charAt(0))) {
            visitedSmallCaves.add(node);
        }
    }

    boolean isComplete() {
        return "end".equals(path.get(path.size() - 1));
    }

    @Override
    public Path clone() {
        return new Path(HashMultiset.create(visitedSmallCaves), new ArrayList<>(path));
    }

    @Override
    public String toString() {
        return path.toString().replace(" ","");
    }
}
