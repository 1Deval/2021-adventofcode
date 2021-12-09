package advent.day8;

import advent.read.Util;
import com.google.common.collect.HashMultiset;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day8/input.txt");
        final List<DataEntry> entries = data.stream()
                .map(s -> s.split("\\|"))
                .map(strings -> new DataEntry(parseDigits(strings[0]), parseDigits(strings[1])))
                .collect(Collectors.toList());

        int count1478 = 0;
        final Map<Integer, Integer> knownSizeToDigit = new HashMap<>();
        knownSizeToDigit.put(2, 1);
        knownSizeToDigit.put(4, 4);
        knownSizeToDigit.put(3, 7);
        knownSizeToDigit.put(7, 8);

        int sum = 0;
        for (final DataEntry entry : entries) {
            count1478 += entry.right.stream().filter(digit -> knownSizeToDigit.containsKey(digit.codes.size())).count();

            final Digit[] all = new Digit[10];
            final Iterator<Digit> allDigitsIterator = entry.left.iterator();
            while (allDigitsIterator.hasNext()) {
                final Digit currentDigit = allDigitsIterator.next();
                final int size = currentDigit.codes.size();
                if (size == 2) {
                    all[1] = currentDigit;
                    allDigitsIterator.remove();
                } else if (size == 4) {
                    all[4] = currentDigit;
                    allDigitsIterator.remove();
                } else if (size == 3) {
                    all[7] = currentDigit;
                    allDigitsIterator.remove();
                } else if (size == 7) {
                    all[8] = currentDigit;
                    allDigitsIterator.remove();
                }

            }
            final char a = getA(all, entry.left);
            final char[] eRef = new char[1];
            final char[] gRef = new char[1];
            setEG(all, entry.left, eRef, gRef);
            final char e = eRef[0];
            final char g = gRef[0];

            find9(entry, all, e);
            find6(entry, all);
            find0(entry, all);
            find2(entry, all, e);
            find3(entry, all);

            all[5] = entry.left.iterator().next();

            final Map<Set<Character>, String> mapping = new HashMap<>();
            for (int i = 0; i < all.length; i++) {
                mapping.put(all[i].codes, Integer.toString(i));
            }

            final int val = Integer.parseInt(entry.right.stream()
                    .map(digit -> digit.codes).map(mapping::get)
                    .collect(Collectors.joining()));
            System.out.println(val);
            sum += val;
        }

        System.out.printf("p1: %d%n", count1478);
        System.out.printf("p2: %d%n", sum);
    }

    private static void find3(final DataEntry entry, final Digit[] all) {
        final Iterator<Digit> allDigitsIterator = entry.left.iterator();
        while (allDigitsIterator.hasNext()) {
            final Digit currentDigit = allDigitsIterator.next();
            if (currentDigit.codes.size() == 5 && currentDigit.codes.containsAll(all[1].codes)) {
                all[3] = currentDigit;
                allDigitsIterator.remove();
            }
        }
    }

    private static void find2(final DataEntry entry, final Digit[] all, final char e) {
        final Iterator<Digit> allDigitsIterator = entry.left.iterator();
        while (allDigitsIterator.hasNext()) {
            final Digit currentDigit = allDigitsIterator.next();
            if (currentDigit.codes.size() == 5 && currentDigit.codes.contains(e)) {
                all[2] = currentDigit;
                allDigitsIterator.remove();
            }
        }
    }

    private static void find0(final DataEntry entry, final Digit[] all) {
        final Iterator<Digit> allDigitsIterator = entry.left.iterator();
        while (allDigitsIterator.hasNext()) {
            final Digit currentDigit = allDigitsIterator.next();
            if (currentDigit.codes.size() == 6) {
                all[0] = currentDigit;
                allDigitsIterator.remove();
            }
        }
    }

    private static void find6(final DataEntry entry, final Digit[] all) {
        final Iterator<Digit> allDigitsIterator = entry.left.iterator();
        while (allDigitsIterator.hasNext()) {
            final Digit currentDigit = allDigitsIterator.next();
            if (currentDigit.codes.size() == 6 && !currentDigit.codes.containsAll(all[1].codes)) {
                all[6] = currentDigit;
                allDigitsIterator.remove();
            }
        }
    }

    private static void find9(final DataEntry entry, final Digit[] all, final char e) {
        final Set<Character> expected9 = new HashSet<>(all[8].codes);
        expected9.remove(e);
        final Iterator<Digit> allDigitsIterator = entry.left.iterator();
        while (allDigitsIterator.hasNext()) {
            final Digit currentDigit = allDigitsIterator.next();
            if (currentDigit.codes.equals(expected9)) {
                all[9] = currentDigit;
                allDigitsIterator.remove();
            }
        }
    }

    private static char getA(final Digit[] all, final List<Digit> left) {
        final Set<Character> calc = new HashSet<>(all[7].codes);
        calc.removeAll(all[1].codes);
        return getCharacter(calc);
    }

    private static void setEG(final Digit[] all, final List<Digit> left, final char[] eRef, final char[] gRef) {
        final Set<Character> calc = new HashSet<>(all[8].codes);
        calc.removeAll(all[7].codes);
        calc.removeAll(all[4].codes);
        final HashMultiset<Character> chars = HashMultiset.create();
        for (final Digit digit : left) {
            chars.addAll(digit.codes);
        }
        chars.retainAll(calc);
        final Iterator<Character> iterator = chars.elementSet().iterator();
        final Character first = iterator.next();
        final Character second = iterator.next();
        if (chars.count(first) > chars.count(second)) {
            eRef[0] = second;
            gRef[0] = first;
        } else {
            eRef[0] = first;
            gRef[0] = second;
        }
    }

    private static Character getCharacter(final Set<Character> calc) {
        if (calc.size() != 1) {
            throw new IllegalStateException();
        }
        return calc.iterator().next();
    }

    private static List<Digit> parseDigits(final String data) {
        return Arrays.stream(data.trim().split("\\W+")).map(s -> new Digit(s.chars().mapToObj(value -> (char) value).collect(Collectors.toSet()))).collect(Collectors.toList());
    }
}
