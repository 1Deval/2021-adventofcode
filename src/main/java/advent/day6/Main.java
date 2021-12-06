package advent.day6;

import advent.read.Util;
import advent.utils.Pair;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day6/input.txt");
        final int[] fishesStartState = Arrays.stream(data.get(0).split(",")).mapToInt(Integer::parseInt).toArray();

//        basic(fishesStartState);

        hashMap(fishesStartState);
    }

    private static void basic(final int[] fishesStartState) {
        final List<LanternFish> fishes = new LinkedList<>();
        for (final int fish : fishesStartState) {
            fishes.add(new LanternFish(fish));
        }

        for (int i = 0; i < 5; i++) {
            final List<LanternFish> newFishes = new LinkedList<>();
            fishes.forEach(lanternFish -> {
                final LanternFish newFish = lanternFish.liveADay();
                if (newFish != null) {
                    newFishes.add(newFish);
                }
            });
            fishes.addAll(newFishes);
        }
        System.out.println(fishes.size());
        System.out.println(fishes);
    }

    private static void hashMap(final int[] fishesStartState) {
        final Map<LanternFish, Long> fishes = new HashMap<>();
        for (final int fish : fishesStartState) {
            fishes.compute(new LanternFish(fish), (lanternFish, integer) -> integer == null ? 1 : integer + 1);
        }


        for (int i = 0; i < 256; i++) {
            final List<Pair<LanternFish, Long>> filesTobeLived = fishes.entrySet().stream().map(lanternFishIntegerEntry -> Pair.of(lanternFishIntegerEntry.getKey(), lanternFishIntegerEntry.getValue())).collect(Collectors.toList());
            fishes.clear();
            filesTobeLived.forEach(lanternFishIntegerPair -> {
                final LanternFish fish = lanternFishIntegerPair.getFirstValue();
                final Long count = lanternFishIntegerPair.getSecondValue();
                final LanternFish newFish = fish.liveADay();
                if (newFish != null) {
                    fishes.put(newFish, count);
                }
            });
            filesTobeLived.forEach(pair -> fishes.compute(pair.getFirstValue(), (lanternFish, integer) -> integer == null ? pair.getSecondValue() : integer + pair.getSecondValue()));
        }

        System.out.println(fishes.values().stream().mapToLong(value -> value).sum());
    }


}
