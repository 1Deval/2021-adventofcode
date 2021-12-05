package advent.read;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {


    public static List<String> getData(final String fileName) throws IOException {
        try (final Stream<String> stream = Files.lines(Paths.get(Objects.requireNonNull(Util.class.getClassLoader().getResource(fileName)).getPath().substring(1)))) {
            return stream.collect(Collectors.toList());
        }
    }
}
