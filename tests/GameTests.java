import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class GameTests {
    private static void require(boolean condition) {
        if (!condition) throw new AssertionError("Проверка не пройдена");
    }

    private static String play(String moves) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes, true, StandardCharsets.UTF_8);
        Scanner input = new Scanner(moves);
        new Game(new Board(), new HumanPlayer("Первый", 'X', input, out),
                new HumanPlayer("Второй", 'O', input, out), out).startGame();
        return bytes.toString(StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        int[][][] lines = {
            {{0,0},{0,1},{0,2}}, {{1,0},{1,1},{1,2}}, {{2,0},{2,1},{2,2}},
            {{0,0},{1,0},{2,0}}, {{0,1},{1,1},{2,1}}, {{0,2},{1,2},{2,2}},
            {{0,0},{1,1},{2,2}}, {{0,2},{1,1},{2,0}}
        };
        for (char mark : new char[]{'X', 'O'}) {
            for (int[][] line : lines) {
                Board board = new Board();
                require(!board.checkWin(mark));
                for (int[] cell : line) require(board.placeMark(cell[0], cell[1], mark));
                require(board.checkWin(mark));
                require(!board.checkWin(mark == 'X' ? 'O' : 'X'));
            }
        }
        Board board = new Board();
        require(!board.checkWin('\0'));
        require(!board.placeMark(-1, 0, 'X'));
        require(!board.placeMark(3, 0, 'X'));
        require(!board.placeMark(0, 3, 'O'));
        require(!board.placeMark(0, 0, 'Z'));
        require(board.placeMark(0, 0, 'X'));
        require(!board.placeMark(0, 0, 'O'));
        require(board.emptyCells().size() == 8);
        require(play("1 1\n2 1\n1 2\n2 2\n1 3\n").contains("Победил Первый (X)!"));
        require(play("1 1\n2 1\n1 2\n2 2\n3 3\n2 3\n").contains("Победил Второй (O)!"));
        require(play("1 1\n1 2\n1 3\n2 2\n2 1\n2 3\n3 2\n3 1\n3 3\n").contains("Ничья!"));
        String invalid = play("abc\n9 9\n1 1\n1 1\n2 1\n1 2\n2 2\n1 3\n");
        require(invalid.contains("Недопустимый ход"));
        require(invalid.contains("Победил Первый (X)!"));
        require(play("").contains("Игра прервана"));
        require(play("q\n").contains("Игра прервана"));
        // Девятый ход даёт победу: заполненное поле не должно скрывать её ничьей.
        String lastMove = play("1 1\n1 2\n2 2\n1 3\n2 1\n2 3\n3 2\n3 1\n3 3\n");
        require(lastMove.contains("Победил Первый"));
        require(!lastMove.contains("Ничья"));
        PrintStream sink = new PrintStream(new ByteArrayOutputStream());
        AIPlayer ai = new AIPlayer("AI", 'O', new Random(42), sink);
        Board aiBoard = new Board();
        for (int i = 0; i < 9; i++) {
            require(ai.makeMove(aiBoard));
            require(aiBoard.emptyCells().size() == 8 - i);
        }
        require(aiBoard.isFull());
        require(!ai.makeMove(aiBoard));
        System.out.println("Все проверки пройдены.");
    }
}
