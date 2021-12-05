package advent.day2;

import advent.read.Util;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(final String... args) throws IOException {

        final List<Move> data = new Main().getData();
        final Coordinate coordinateP1 = new CoordinateP1();
        final Coordinate coordinateP2 = new CoordinateP2();
        data.forEach(move -> {
            move.getMovement().move(coordinateP1, move.getAmount());
            move.getMovement().move(coordinateP2, move.getAmount());
        });
        System.out.println(coordinateP1.getArea());
        System.out.println(coordinateP2.getArea());
    }


    private List<Move> getData() throws IOException {
        return Util.getData("day2/input.txt").stream()
                .map(s -> {
                    final String[] split = s.split(" ");
                    return new Move(Movement.valueOf(split[0]), Integer.parseInt(split[1]));
                }).collect(Collectors.toList());
    }
}
