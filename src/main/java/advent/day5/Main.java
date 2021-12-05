package advent.day5;

import advent.read.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day5/input.txt");

        final List<Line> lines = getLines(data);
        final int maxIndex = lines.stream().mapToInt(Line::max).max().getAsInt();
        System.out.println(maxIndex);
        final LineMap lineMapP1 = new LineMap(maxIndex + 1);
        final LineMap lineMapP2 = new LineMap(maxIndex + 1);
        lines.stream().filter(Line::isHorizontal).forEach(lineMapP1::record);
        lines.stream().filter(line -> line.isDiagonal() || line.isHorizontal()).forEach(lineMapP2::record);
        System.out.println("P1: " + lineMapP1.countGreaterThan(1));
        System.out.println("P2: " + lineMapP2.countGreaterThan(1));
    }

    static Pattern lineInputPattern = Pattern.compile("(?<ax>\\d+),(?<ay>\\d+) -> (?<bx>\\d+),(?<by>\\d+)");

    private static List<Line> getLines(final List<String> rawLines) {
        final List<Line> lines = new ArrayList<>(rawLines.size());
        for (final String rawLine : rawLines) {

            final Matcher matcher = lineInputPattern.matcher(rawLine);
            if (matcher.matches()) {
                lines.add(new Line(
                        new Point(Integer.parseInt(matcher.group("ax")),
                                Integer.parseInt(matcher.group("ay"))),
                        new Point(Integer.parseInt(matcher.group("bx")),
                                Integer.parseInt(matcher.group("by")))
                ));
            } else {
                System.err.printf("didnt match[%s]%n", rawLine);
            }
        }
        return lines;
    }
}
