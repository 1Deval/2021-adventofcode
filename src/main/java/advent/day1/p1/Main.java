package advent.day1.p1;

import advent.read.Util;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(final String... args) throws IOException {

        final List<Long> data = new Main().getData();
        long previous = data.get(0);
        int inc = 0;
        for (int i = 1; i < data.size(); i++) {
            final long curr = data.get(i);
            if (curr > previous) {
                inc++;
            }
            previous = curr;
        }
        System.out.println(inc);
    }


    private List<Long> getData() throws IOException {
        return Util.getData("day1/input.txt").stream().map(Long::parseLong).collect(Collectors.toList());
    }
}
