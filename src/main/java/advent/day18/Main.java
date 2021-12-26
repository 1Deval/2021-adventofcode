package advent.day18;

import advent.read.Util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    static List<String> data;

    public static void main(final String... args) throws IOException {
        data = Util.getData("day18/input.txt");

        final List<SnailFish> allNumbers = readAllNumbers(data);
        System.out.println("P1 magnitude: " + sumAndMagnitude(allNumbers));
        System.out.println("P2 magnitude: " + maxForPair());
    }

    private static SnailFish getSnailFishNumber(final int index) throws IOException {
        return toSnailFishNumber(data.get(index));
    }

    private static List<SnailFish> readAllNumbers(final List<String> data) {
        return data.stream().map(Main::toSnailFishNumber).collect(Collectors.toList());
    }

    private static long maxForPair() throws IOException {
        long magMax = 0;
        final int numbersSize = data.size();
        for (int i = 0; i < numbersSize; i++) {
            for (int j = 0; j < numbersSize; j++) {
                if (i != j) {
                    final long mag = sumAndMagnitude(Arrays.asList(getSnailFishNumber(i), getSnailFishNumber(j)));
                    magMax = Math.max(magMax, mag);
                }
            }
        }
        return magMax;
    }

    private static long sumAndMagnitude(final List<SnailFish> numbers) {
        SnailFish number = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            number = number.add(numbers.get(i));
        }
        return number.magnitude();
    }

    private static SnailFish toSnailFishNumber(final String line) {
        final Pattern pattern = Pattern.compile("^(?<left>\\d),(?<right>\\d)$");
        final Matcher matcher = pattern.matcher(line);
        if (matcher.matches()) {
            return new Complex(new Literal(Integer.parseInt(matcher.group("left"))), new Literal(Integer.parseInt(matcher.group("right"))));
        }
        if (line.length() == 1) {
            return new Literal(Integer.parseInt(line));
        }
        if (line.startsWith("[")) {
            final int closingIndex = findClosingIndex(line);
            if (closingIndex == line.length() - 1) {
                return toSnailFishNumber(line.substring(1, line.length() - 1));
            } else {
                return new Complex(
                        toSnailFishNumber(line.substring(1, closingIndex)),
                        toSnailFishNumber(line.substring(closingIndex + 2))
                );
            }
        }
        final char startingChar = line.charAt(0);
        if (isDigit(startingChar)) {
            return new Complex(new Literal(Integer.parseInt(String.valueOf(startingChar))), toSnailFishNumber(line.substring(3, line.length() - 1)));
        }
        final char endingChar = line.charAt(line.length() - 1);
        if (isDigit(endingChar)) {
            return new Complex(toSnailFishNumber(line.substring(0, line.length() - 3)), new Literal(Integer.parseInt(String.valueOf(startingChar))));
        }
        throw new IllegalStateException();
    }

    private static int findClosingIndex(final String line) {
        int count = 0;
        for (int i = 0; i < line.length(); i++) {
            final char c = line.charAt(i);
            switch (c) {
                case '[':
                    count++;
                    break;
                case ']':
                    count--;
                    break;
            }
            if (count == 0) {
                return i;
            }
        }
        return 0;
    }

    private static boolean isDigit(final char startingChar) {
        return startingChar >= '0' && startingChar <= '9';
    }

}
