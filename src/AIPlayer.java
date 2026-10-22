import java.io.PrintStream;
import java.util.List;
import java.util.Random;

/** Дополнительный режим: случайный выбор из свободных клеток. */
public class AIPlayer extends Player {
    private final Random random;
    private final PrintStream out;

    public AIPlayer(String name, char mark, Random random, PrintStream out) {
        super(name, mark);
        this.random = random;
        this.out = out;
    }

    @Override
    public boolean makeMove(Board board) {
        List<int[]> cells = board.emptyCells();
        if (cells.isEmpty()) return false;
        int[] cell = cells.get(random.nextInt(cells.size()));
        board.placeMark(cell[0], cell[1], getMark());
        out.printf("%s (%c): %d %d%n", getName(), getMark(), cell[0] + 1, cell[1] + 1);
        return true;
    }
}
