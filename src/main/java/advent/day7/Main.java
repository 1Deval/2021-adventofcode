package advent.day7;

import advent.read.Util;
import com.google.common.collect.HashMultiset;

import java.io.IOException;
import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Main {
    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day7/input.txt");
        final int[] positions = Arrays.stream(data.get(0).split(",")).mapToInt(Integer::parseInt).toArray();

        final IntSummaryStatistics intSummaryStatistics = Arrays.stream(positions).summaryStatistics();
        final int avg = (int) intSummaryStatistics.getAverage();


        System.out.printf("avg: %d%n", avg);
        final int mid = (int) Math.round(getMedian(positions));
        System.out.printf("median: %d%n", mid);
        System.out.printf("p1 avg: %d%n", calculateCostOptimizedP1(positions, avg));
        System.out.printf("p1 mid: %d%n", calculateCostOptimizedP1(positions, mid));

        System.out.printf("p2 avg: %d%n", calculateCostOptimizedP2(positions, avg));
        System.out.printf("p2 mid: %d%n", calculateCostOptimizedP2(positions, mid));

    }

    private static double getMedian(final int[] positions) {
        Arrays.sort(positions);
        final double median;
        if (positions.length % 2 == 0) {
            median = ((double) positions[positions.length / 2] + (double) positions[positions.length / 2 - 1]) / 2;
        } else {
            median = positions[positions.length / 2];
        }
        return median;
    }

    private static int calculateCost(final int[] positions, final int targetPosition) {
        int cost = 0;
        for (final int position : positions) {
            cost += Math.abs(targetPosition - position);
        }
        return cost;
    }

    private static int calculateCostOptimizedP1(final int[] positions, final int targetPosition) {
        final HashMultiset<Integer> positionsSet = HashMultiset.create();
        positionsSet.addAll(Arrays.stream(positions).boxed().collect(Collectors.toList()));
        final AtomicInteger cost = new AtomicInteger();
        positionsSet.forEachEntry((position, count) -> cost.addAndGet(Math.abs(targetPosition - position) * count));
        return cost.get();
    }

    private static long calculateCostOptimizedP2(final int[] positions, final int targetPosition) {
        final HashMultiset<Integer> positionsSet = HashMultiset.create();
        positionsSet.addAll(Arrays.stream(positions).boxed().collect(Collectors.toList()));
        final AtomicLong cost = new AtomicLong();
        positionsSet.forEachEntry((position, count) -> {
            final long distance = Math.abs(targetPosition - position);
            final long localCost = (distance * (distance + 1)) / 2;
            cost.addAndGet(localCost * count);
        });
        return cost.get();
    }
}
