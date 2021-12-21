package advent.day16;

import advent.read.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

public class Main {


    static int versionSum;

    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day16/input.txt");
        final String rawInputString = data.get(0);
        final Feed feed = new Feed(hexToBin(rawInputString));

        processCount(feed, 1);

        System.out.println(feed.input);
        System.out.println("P1: " + versionSum);
        System.out.println("P2: " + feed.value);

    }

    static List<Feed> processCount(final Feed feed, final long countLimit) {
        final List<Feed> processed = new ArrayList<>();
        Feed feedLocal = feed;
        for (int i = 0; i < countLimit; i++) {
            final Feed remainder = processPacket(feedLocal);
            processed.add(feedLocal);
            feedLocal = remainder;
        }
        return processed;
    }

    static List<Feed> processLength(final Feed feed, final long lengthLimit) {
        final List<Feed> processed = new ArrayList<>();
        Feed feedLocal = feed;
        for (int i = 0; i < lengthLimit; ) {
            final Feed remainder = processPacket(feedLocal);
            i += feedLocal.lengthProcessed();
            processed.add(feedLocal);
            feedLocal = remainder;
        }
        return processed;
    }

    private static Feed processPacket(final Feed feed) {
        feed.init();
        feed.printVersionAndPacketId();
        versionSum += feed.version;
        if (feed.packetType == PacketType.LITERAL) {
            feed.processTypeLiteral();
        } else {
            feed.value = processOperator(feed);
            System.out.println("op " + feed.value);
        }
        return new Feed(feed.input);
    }

    private static long processOperator(final Feed feed) {
        final String lengthType = feed.takeSubString(1);
        final List<Feed> processed;
        System.out.println("length bit " + lengthType);
        if ("0".equals(lengthType)) {
            final long processingLength = feed.getLong(15);
            final Feed newFeed = new Feed(feed.takeSubString((int) processingLength));
            processed = processLength(newFeed, processingLength);
        } else {
            final long packetCountLimit = feed.getLong(11);
            final Feed newFeed = new Feed(feed.input);
            processed = processCount(newFeed, packetCountLimit);
            final int size = processed.stream().mapToInt(Feed::lengthProcessed).sum();
            feed.takeSubString(size);
        }
        switch (feed.packetType) {

            case SUM:
                return processed.stream().mapToLong(Feed::getValue).sum();
            case PRODUCT:
                long val = 1;
                for (final Feed fd : processed) {
                    val *= fd.getValue();
                }
                return val;
            case MINIMUM:
                final OptionalLong min = processed.stream().mapToLong(Feed::getValue).min();
                if (min.isPresent()) {
                    return min.getAsLong();
                }
                throw new IllegalStateException();
            case MAXIMUM:
                final OptionalLong max = processed.stream().mapToLong(Feed::getValue).max();
                if (max.isPresent()) {
                    return max.getAsLong();
                }
                throw new IllegalStateException();
            case GREATER_THAN:
                if (processed.size() != 2) {
                    throw new IllegalStateException("not 2");
                }
                if (processed.get(0).getValue() > processed.get(1).getValue()) {
                    return 1;
                } else {
                    return 0;
                }
            case LESS_THAN:
                if (processed.size() != 2) {
                    throw new IllegalStateException("not 2");
                }
                if (processed.get(0).getValue() < processed.get(1).getValue()) {
                    return 1;
                } else {
                    return 0;
                }
            case EQUAL:
                if (processed.size() != 2) {
                    throw new IllegalStateException("not 2");
                }
                if (processed.get(0).getValue() == processed.get(1).getValue()) {
                    return 1;
                } else {
                    return 0;
                }
            default:
                throw new IllegalStateException("not acceptable");
        }
    }


    static String hexToBin(final String hex) {
        final StringBuilder bin = new StringBuilder();
        new StringBuilder();
        StringBuilder binFragment;
        int iHex;
        String trim = hex.trim();
        trim = trim.replaceFirst("0x", "");

        for (int i = 0; i < trim.length(); i++) {
            iHex = Integer.parseInt("" + trim.charAt(i), 16);
            binFragment = new StringBuilder(Integer.toBinaryString(iHex));

            while (binFragment.length() < 4) {
                binFragment.insert(0, "0");
            }
            bin.append(binFragment);
        }
        return bin.toString();
    }

}
