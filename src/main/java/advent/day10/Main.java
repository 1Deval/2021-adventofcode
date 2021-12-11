package advent.day10;

import advent.read.Util;
import advent.utils.Pair;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    static final Map<Character, Integer> pointReference = new HashMap<>();

    static final Map<Character, Character> openCloseMap = new HashMap<>();
    static final Map<Character, Character> closeOpenMap = new HashMap<>();
    static final Map<Character, Integer> autoCompleteScore = new HashMap<>();

    static {
        pointReference.put(')', 3);
        pointReference.put(']', 57);
        pointReference.put('}', 1197);
        pointReference.put('>', 25137);

        openCloseMap.put('(', ')');
        openCloseMap.put('[', ']');
        openCloseMap.put('{', '}');
        openCloseMap.put('<', '>');

        closeOpenMap.put(')', '(');
        closeOpenMap.put(']', '[');
        closeOpenMap.put('}', '{');
        closeOpenMap.put('>', '<');

        autoCompleteScore.put(')', 1);
        autoCompleteScore.put(']', 2);
        autoCompleteScore.put('}', 3);
        autoCompleteScore.put('>', 4);
    }

    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day10/input.txt");


        int points = 0;
        final List<Long> autoCompleteScores = new ArrayList<>();
        for (final String line : data) {
            final Pair<Integer, Deque<Character>> errorPoints = findErrorPoints(line);
            points += errorPoints.getFirstValue();
            if (errorPoints.getSecondValue() != null) {
                autoCompleteScores.add(getAutoCompleteScore(errorPoints.getSecondValue()));
            }
        }
        System.out.println("P1: " + points);
        Collections.sort(autoCompleteScores);
        final int middle = autoCompleteScores.size() / 2;
        System.out.println(autoCompleteScores.get(middle));
    }

    private static long getAutoCompleteScore(final Deque<Character> characters) {
        long score = 0;
        while (characters.peek() != null) {
            score *= 5;
            score += autoCompleteScore.get(characters.pop());
        }
        return score;
    }

    private static Pair<Integer, Deque<Character>> findErrorPoints(final String line) {
        final Deque<Character> stack = new ArrayDeque<>();
        for (final char c : line.toCharArray()) {
            if (closeOpenMap.containsKey(c)) {
                try {
                    final Character pop = stack.pop();
                    if (pop != c) {
                        return Pair.of(pointReference.get(c), null);
                    }
                } catch (final EmptyStackException e) {
                    return Pair.of(pointReference.get(c), null);
                }
            } else {
                stack.push(openCloseMap.get(c));
            }
        }
        return Pair.of(0, stack);
    }
}
