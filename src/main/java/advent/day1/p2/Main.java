package advent.day1.p2;

import advent.read.Util;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(final String... args) throws IOException {

        final List<Long> data = new Main().getData();
        long previous = data.get(0) + data.get(1) + data.get(2);
        int inc = 0;
        for (int i = 3; i < data.size(); i++) {
            final long curr = previous - data.get(i - 3) + data.get(i);
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
