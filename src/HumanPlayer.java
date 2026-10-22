import java.io.PrintStream;
import java.util.Scanner;

public class HumanPlayer extends Player {
    private final Scanner input;
    private final PrintStream out;

    public HumanPlayer(String name, char mark, Scanner input, PrintStream out) {
        super(name, mark);
        this.input = input;
        this.out = out;
    }

    @Override
    public boolean makeMove(Board board) {
        while (true) {
            out.printf("%s (%c), введите строку и столбец (1–3), либо q для выхода: %n", getName(), getMark());
            if (!input.hasNextLine()) return false;
            String line = input.nextLine().trim();
            if (line.equalsIgnoreCase("q")) return false;
            String[] parts = line.split("\\s+");
            try {
                if (parts.length == 2 && board.placeMark(Integer.parseInt(parts[0]) - 1,
                        Integer.parseInt(parts[1]) - 1, getMark())) return true;
            } catch (NumberFormatException ignored) {
                // Повторяем запрос, сохраняя очередь текущего игрока.
            }
            out.println("Недопустимый ход. Введите два числа от 1 до 3 и выберите свободную клетку.");
        }
    }
}
