package advent.day17;

import advent.read.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day17/input.txt");
        final Range xRange = getRange("x", data.get(0));
        final Range yRange = getRange("y", data.get(0));


        final int dip = Math.abs(yRange.min);

        final int yMaxPosition = dip * (dip + 1) / 2 - dip;
        final int yMax = getInitVelocityForMax(yMaxPosition);
        final int xMin = getInitVelocityForMax(xRange.min);
        System.out.println("P1:" + yMaxPosition);
        System.out.println("yMax:" + yMax);
        System.out.println("xMin:" + xMin);

        final List<Probe> probes = new ArrayList<>();
        for (int x = xMin; x <= xRange.max; x++) {
            for (int y = yRange.min; y <= yMax; y++) {
                probes.add(new Probe(x, y, xRange, yRange));
            }
        }
        int inRange = 0;
        while(!probes.isEmpty()) {
            final Iterator<Probe> iterator = probes.iterator();
            while (iterator.hasNext()) {
                final Probe probe = iterator.next();
                final Probe.Bounds bounds = probe.step();
                if (bounds == Probe.Bounds.IN) {
                    iterator.remove();
                    inRange++;
                } else if (bounds == Probe.Bounds.OUT) {
                    iterator.remove();
                }
            }
        }
        System.out.println(inRange);
    }


    private static int getInitVelocityForMax(final int yMaxPosition) {
        return (int) ((-1 + Math.sqrt(1 + 8 * yMaxPosition)) / 2);
    }

    private static Range getRange(final String label, final String line) {
        final Pattern rangePattern = Pattern.compile(".*" + Pattern.quote(label + "=") + "(?<min>[-\\d]+)" + Pattern.quote("..") + "(?<max>[-\\d]+).*");
        final Matcher matcher = rangePattern.matcher(line);
        if (matcher.matches()) {
            return new Range(Integer.parseInt(matcher.group("min")), Integer.parseInt(matcher.group("max")));
        } else {
            throw new IllegalStateException();
        }
    }
}
