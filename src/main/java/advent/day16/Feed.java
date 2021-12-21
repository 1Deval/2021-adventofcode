package advent.day16;

public class Feed {
    int version;
    PacketType packetType;
    final int originalLength;
    String input;
    long value;

    public Feed(final String input) {
        this.input = input;
        originalLength = input.length();
    }

    void init() {
        version = (int) getLong(3);
        packetType = PacketType.fromId((int) getLong(3));
    }

    int lengthProcessed() {
        return originalLength - input.length();
    }

    long getLong(final int size) {
        final String valueString = takeSubString(size);
        return Long.parseLong(valueString, 2);
    }

    String takeSubString(final int size) {
        final String inputVal = input;
        final String valueString = inputVal.substring(0, size);
        input = inputVal.substring(size);
        return valueString;
    }

    public long getValue() {
        return value;
    }

     void processTypeLiteral() {
        long val = 0;
        boolean hasNext = true;
        String nextBit;
        while (hasNext) {
            nextBit = takeSubString(1);
            val = val << 4;
            val += getLong(4);
            hasNext = "1".equals(nextBit);
        }
        System.out.printf("parsed val: %d%n", val);
        value = val;
    }

    void printVersionAndPacketId() {
        System.out.printf("version: %d packetType %s%n", version, packetType);
    }
}
