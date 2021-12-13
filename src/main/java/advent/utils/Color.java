package advent.utils;

public enum Color {


    ANSI_BLACK("\u001B[30m"),

    ANSI_RED("\u001B[31m"),

    ANSI_GREEN("\u001B[32m"),

    ANSI_YELLOW("\u001B[33m"),

    ANSI_BLUE("\u001B[34m"),

    ANSI_PURPLE("\u001B[35m"),

    ANSI_CYAN("\u001B[36m"),

    ANSI_WHITE("\u001B[37m");

    private final String code;

    Color(final String code) {
        this.code = code;
    }

    public String wrap(final Object input) {
        return String.format("%s%s\u001B[0m", code, input.toString());
    }
}
