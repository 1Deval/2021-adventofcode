package advent.day3;

import advent.read.Util;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(final String... args) throws IOException {
        final List<String> data = new Main().getData();
        final int firstValueLength = data.get(0).length();
        final CountPair[] countPairs = new CountPair[firstValueLength];
        for (int i = 0; i < firstValueLength; i++) {
            countPairs[i] = getCounts(data, i);
        }

        final StringBuilder gammaBuilder = new StringBuilder();
        final StringBuilder epsilonBuilder = new StringBuilder();

        for (int i = 0; i < firstValueLength; i++) {
            if (countPairs[i].trueCount > countPairs[i].falseCount) {
                gammaBuilder.append("1");
                epsilonBuilder.append("0");
            } else {
                gammaBuilder.append("0");
                epsilonBuilder.append("1");
            }
        }
        final int gamma = Integer.parseInt(gammaBuilder.toString(), 2);
        final int epsilon = Integer.parseInt(epsilonBuilder.toString(), 2);

        System.out.println(gamma * epsilon);


        final String oxygenRating = findFilteredRating(data, 0, countPair -> {
            if (countPair.trueCount < countPair.falseCount) {
                return '0';
            } else {
                return '1';
            }
        });

        final String co2Rating = findFilteredRating(data, 0, countPair -> {
            if (countPair.trueCount < countPair.falseCount) {
                return '1';
            } else {
                return '0';
            }
        });
        System.out.println(Integer.parseInt(oxygenRating, 2) * Integer.parseInt(co2Rating, 2));


    }

    private static CountPair getCounts(final List<String> data, final int position) {
        final CountPair countPair = new CountPair();
        data.forEach(s -> {
            if (s.charAt(position) == '0') {
                countPair.falseCount++;
            } else {
                countPair.trueCount++;
            }
        });
        return countPair;
    }

    private static String findFilteredRating(final List<String> data, final int position, final Function<CountPair, Character> filterFunction) {
        final CountPair countPair = getCounts(data, position);
        final char filterChar = filterFunction.apply(countPair);

        final List<String> filteredData = data.stream().filter(s -> s.charAt(position) == filterChar).collect(Collectors.toList());

        if (filteredData.size() == 1) {
            return filteredData.get(0);
        }
        return findFilteredRating(filteredData, position + 1, filterFunction);
    }

    List<String> getData() throws IOException {
        return Util.getData("day3/input.txt");
    }
}
