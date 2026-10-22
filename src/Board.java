import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/** Поле хранит состояние и проверяет правила размещения меток. */
public class Board {
    public static final int SIZE = 3;
    private final char[][] grid = new char[SIZE][SIZE];

    public boolean placeMark(int row, int col, char mark) {
        if ((mark != 'X' && mark != 'O') || row < 0 || row >= SIZE
                || col < 0 || col >= SIZE || grid[row][col] != '\0') {
            return false;
        }
        grid[row][col] = mark;
        return true;
    }

    public List<int[]> emptyCells() {
        List<int[]> cells = new ArrayList<>();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (grid[r][c] == '\0') cells.add(new int[]{r, c});
            }
        }
        return cells;
    }

    public boolean isFull() { return emptyCells().isEmpty(); }

    public boolean checkWin(char mark) {
        if (mark != 'X' && mark != 'O') return false;
        for (int i = 0; i < SIZE; i++) {
            if (grid[i][0] == mark && grid[i][1] == mark && grid[i][2] == mark) return true;
            if (grid[0][i] == mark && grid[1][i] == mark && grid[2][i] == mark) return true;
        }
        return (grid[0][0] == mark && grid[1][1] == mark && grid[2][2] == mark)
                || (grid[0][2] == mark && grid[1][1] == mark && grid[2][0] == mark);
    }

    public void displayBoard() { displayBoard(System.out); }

    public void displayBoard(PrintStream out) {
        out.println("    1   2   3");
        for (int r = 0; r < SIZE; r++) {
            out.print((r + 1) + "   ");
            for (int c = 0; c < SIZE; c++) {
                out.print(grid[r][c] == '\0' ? ' ' : grid[r][c]);
                if (c < SIZE - 1) out.print(" | ");
            }
            out.println();
            if (r < SIZE - 1) out.println("   ---+---+---");
        }
    }
}
