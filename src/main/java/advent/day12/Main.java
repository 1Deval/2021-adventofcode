package advent.day12;

import advent.read.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

public class Main {

    private static final Map<String, List<String>> routes = new HashMap<>();

    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day12/input.txt");
        for (final String route : data) {
            final String[] split = route.split("-");
            routes.computeIfAbsent(split[0], s -> new ArrayList<>()).add(split[1]);
            routes.computeIfAbsent(split[1], s -> new ArrayList<>()).add(split[0]);
        }
        System.out.println(routes);

        System.out.println("P1:" + findEnd(new Path(), "start", Path::canVisitP1).size());
        System.out.println("P2:" + findEnd(new Path(), "start", Path::canVisitP2).size());

    }

    private static List<Path> findEnd(final Path path, final String visitNode, final BiFunction<Path, String, Boolean> canVisitRule) {
        path.visit(visitNode);
        if (path.isComplete()) {
//            System.out.println(path);
            return Collections.singletonList(path);
        }

        final List<Path> paths = new ArrayList<>();
        for (final String nextNode : routes.get(visitNode)) {
            if (canVisitRule.apply(path, nextNode)) {
                paths.addAll(findEnd(path.clone(), nextNode, canVisitRule));
            }
        }
        return paths;

    }
}
