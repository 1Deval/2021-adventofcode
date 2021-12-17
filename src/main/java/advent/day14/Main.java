package advent.day14;

import advent.read.Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day14/input.txt");

        final String template = data.get(0);
        Map<String, Long> stringPairs = getInitialPairs(template);
        final HashMap<Character, Long> counts = new HashMap<>();
        final Map<String, String> mapper = getMapper(data);
        template.chars().forEach(value -> counts.compute((char) value, (character, count) -> count == null ? 1 : count + 1));

        for (int i = 0; i < 40; i++) {
            stringPairs = step(stringPairs, mapper, counts);
        }

        final LongSummaryStatistics countsStats = counts.values().stream().mapToLong(aLong -> aLong).summaryStatistics();

        System.out.println(countsStats.getMax() - countsStats.getMin());
        System.out.println(counts.size());

    }

    private static Map<String, Long> step(final Map<String, Long> stringPairs, final Map<String, String> mapper, final HashMap<Character, Long> counts) {
        final Map<String, Long> multiset = new HashMap<>();
        for (final Map.Entry<String, Long> entry : stringPairs.entrySet()) {
            final String element = entry.getKey();
            final long elementCount = entry.getValue();
            if (mapper.containsKey(element)) {
                final String mappedValue = mapper.get(element);
                multiset.compute(element.charAt(0) + mappedValue, (s, val) -> val == null ? elementCount : elementCount + val);
                multiset.compute(mappedValue + element.charAt(1), (s, val) -> val == null ? elementCount : elementCount + val);
                counts.compute(mappedValue.charAt(0), (character, count) -> count == null ? elementCount : count + elementCount);
            } else {
                multiset.compute(element, (s, val) -> val == null ? elementCount : elementCount + val);
            }
        }
        return multiset;
    }

    private static Map<String, String> getMapper(final List<String> data) {
        final Pattern mapsPattern = Pattern.compile("(?<key>\\w+) -> (?<value>\\w)");
        return data.stream().map(mapsPattern::matcher)
                .filter(Matcher::matches)
                .collect(Collectors.toMap(matcher -> matcher.group("key"), matcher -> matcher.group("value")));
    }

    private static Map<String, Long> getInitialPairs(final String template) {

        final Map<String, Long> multiset = new HashMap<>();
        for (int i = 0; i < template.length() - 1; i++) {
            multiset.compute(template.substring(i, i + 2), (s, aLong) -> aLong == null ? 1 : aLong + 1);

        }
        return multiset;
    }
}
