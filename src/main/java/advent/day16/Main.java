package advent.day16;

import advent.read.Util;

import java.io.IOException;
import java.util.List;

public class Main {




    enum ParseMode {
        LENGTH, PACKET_COUNT
    }

    static int version;

    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day16/sample.txt");
        final String rawInputString = data.get(0);
        final Feed feed = new Feed(hexToBin(rawInputString));
        final boolean processNext = false;

        processCount(feed, 1);

        System.out.println(feed.input);
        System.out.println("P1: " + version);
//        System.out.printf("version sum %d%n", versionSum);

    }

    static int processCount(final Feed feed, final int countLimit) {
        int sizeProcessed = 0;
        Feed feedLocal = feed;
        for (int i = 0; i < countLimit; i++) {
            final Feed remainder = processPacket(feedLocal);
            sizeProcessed += feedLocal.lengthProcessed();
            feedLocal = remainder;
        }
        return sizeProcessed;
    }

    static void processLength(final Feed feed, final int lengthLimit) {
        Feed feedLocal = feed;
        for (int i = 0; i < lengthLimit; ) {
            final Feed remainder = processPacket(feedLocal);
            i += feedLocal.lengthProcessed();
            feedLocal = remainder;
        }
    }

    private static Feed processPacket(final Feed feed) {
        feed.init();
        feed.printVersionAndPacketId();
        version += feed.version;
        if (feed.packetType == PacketType.LITERAL) {
            processTypeLiteralValue(feed);
        } else {
            processOperator(feed);
        }
        return new Feed(feed.input);
    }

    private static void processOperator(final Feed feed) {
        final String lengthType = feed.takeSubString(1);
        System.out.println("length bit " + lengthType);
        if ("0".equals(lengthType)) {
            final int processingLength = feed.getInt(15);
            final Feed newFeed = new Feed(feed.takeSubString(processingLength));
            processLength(newFeed, processingLength);
        } else {
            final int packetCountLimit = feed.getInt(11);
            final Feed newFeed = new Feed(feed.input);
            final int size = processCount(newFeed, packetCountLimit);
            feed.takeSubString(size);
        }
    }


    private static int processTypeLiteralValue(final Feed feed) {
        int val = 0;
        boolean hasNext = true;
        String nextBit;
        while (hasNext) {
            nextBit = feed.takeSubString(1);
            val = val << 4;
            val += feed.getInt(4);
            hasNext = "1".equals(nextBit);
        }
        System.out.printf("parsed val: %d%n", val);
        return val;
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
