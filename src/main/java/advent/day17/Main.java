package advent.day17;

import advent.read.Util;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day17/sample.txt");
        final Range xRange = getRange("x", data.get(0));
        final Range yRange = getRange("y", data.get(0));


        final int dip = Math.abs(yRange.min);

        System.out.println("P1:" + (dip * (dip + 1) / 2 - dip));
    }

    private static Range getRange(final String label, final String line) {
        final Pattern rangePattern = Pattern.compile(".*" + Pattern.quote(label + "=") + "(?<min>[-\\d]+)" + Pattern.quote("..") + "(?<max>[-\\d]+).*");
        final Matcher matcher = rangePattern.matcher(line);
        if (matcher.matches()) {
            return new Range(Integer.parseInt(matcher.group("min")), Integer.parseInt(matcher.group("max")));
        }
        return null;
    }
}
