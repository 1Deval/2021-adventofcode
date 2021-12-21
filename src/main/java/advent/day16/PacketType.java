package advent.day16;

enum PacketType {

    LITERAL(4),
    SUM(0),
    PRODUCT(1),
    MINIMUM(2),
    MAXIMUM(3),
    GREATER_THAN(5),
    LESS_THAN(6),
    EQUAL(7);

    private final int id;

    PacketType(final int id) {
        this.id = id;
    }

    static PacketType fromId(final int id) {
        for (final PacketType value : values()) {
            if (value.id == id) {
                return value;
            }
        }
        throw new IllegalStateException("ID: " + id);
    }
}
