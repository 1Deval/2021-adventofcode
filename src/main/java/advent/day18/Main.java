package advent.day18;

import advent.read.Util;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day18/sample.txt");

        SnailFish number = null;
        for (final String row : data) {
            if (number == null) {
                number = toSnailFileR(row);
            } else {
                number = number.add(toSnailFileR(row));
            }
//            final SnailFish number = toSnailFileR(row);
            String before;
            System.out.println("Before Loop: " + number);
            do {
                before = number.toString();
                number.explode();
                System.out.println("after explode: " + number);
                number.split();
                System.out.println(" after split : " + number);
            } while (!before.equals(number.toString()));
            System.out.println("end of loop: " + before);
        }

//        System.out.println(number);
    }

    private static SnailFish toSnailFileR(final String line) {
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
                return toSnailFileR(line.substring(1, line.length() - 1));
            } else {
                return new Complex(
                        toSnailFileR(line.substring(1, closingIndex)),
                        toSnailFileR(line.substring(closingIndex + 2))
                );
            }
        }
        final char startingChar = line.charAt(0);
        if (isDigit(startingChar)) {
            return new Complex(new Literal(Integer.parseInt(String.valueOf(startingChar))), toSnailFileR(line.substring(3, line.length() - 1)));
        }
        final char endingChar = line.charAt(line.length() - 1);
        if (isDigit(endingChar)) {
            return new Complex(toSnailFileR(line.substring(0, line.length() - 3)), new Literal(Integer.parseInt(String.valueOf(startingChar))));
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


    static SnailFish toLiteral(final String row) {
        final Pattern pattern = Pattern.compile("\\[(?<left>\\d+),(?<right>\\d+)]");
        final Matcher matcher = pattern.matcher(row);
        if (!matcher.matches()) {
            throw new IllegalStateException();
        }
        return new Complex(new Literal(Integer.parseInt(matcher.group("left"))), new Literal(Integer.parseInt(matcher.group("right"))));
    }
}
