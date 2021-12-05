package advent.day4;

import advent.read.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(final String... args) throws IOException {
        final List<String> data = Util.getData("day4/input.txt");
        final int[] calls = Arrays.stream(data.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        final List<String> rawBoards = data.subList(2, data.size());

        final List<Board> boards = getBoards(rawBoards);

        for (final int call : calls) {
            final Iterator<Board> boardIterator = boards.iterator();
            while (boardIterator.hasNext()) {
                final Board board = boardIterator.next();
                final Integer computed = board.call(call);
                if (computed != null) {
                    System.out.println(computed);
                    boardIterator.remove();
                }
            }
        }
//        System.out.println(boards);
    }

    private static List<Board> getBoards(final List<String> rawBoards) {
        final List<Board> boards = new ArrayList<>();
        Board board = null;
        for (final String line : rawBoards) {
            System.out.println(line);
            if (line.isEmpty()) {
                boards.add(board);
                board = null;
            } else {
                final int[] cellNumbers = Arrays.stream(line.split("\\W+")).filter(s -> !s.isEmpty())
                        .mapToInt(Integer::parseInt).toArray();
                if (board == null) {
                    board = new Board(cellNumbers.length);
                }
                board.fill(cellNumbers);
            }
        }
        boards.add(board);
        return boards;
    }
}
