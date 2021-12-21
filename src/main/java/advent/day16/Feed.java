package advent.day16;

public class Feed {
    int version;
    PacketType packetType;
    final int originalLength;
    String input;

    public Feed(final String input) {
        this.input = input;
        originalLength = input.length();
    }

    void init() {
        version = getInt(3);
        packetType = PacketType.fromId(getInt(3));
    }

    int lengthProcessed() {
        return originalLength - input.length();
    }

    int getInt(final int size) {
        final String valueString = takeSubString(size);
        return Integer.parseInt(valueString, 2);
    }

    String takeSubString(final int size) {
        final String inputVal = input;
        final String valueString = inputVal.substring(0, size);
        input = inputVal.substring(size);
        return valueString;
    }

    private static void processTypeLiteral(final Feed feed) {
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
    }

    void printVersionAndPacketId() {
        System.out.printf("version: %d packetType %s%n", version, packetType);
    }
}
