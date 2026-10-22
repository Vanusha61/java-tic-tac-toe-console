import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length > 1 || (args.length == 1 && !args[0].equals("--ai"))) {
            System.out.println("Запуск: java -cp out Main [--ai]");
            return;
        }
        try (Scanner input = new Scanner(System.in)) {
            Player player1 = new HumanPlayer("Игрок 1", 'X', input, System.out);
            Player player2 = args.length == 1
                    ? new AIPlayer("Компьютер", 'O', new Random(), System.out)
                    : new HumanPlayer("Игрок 2", 'O', input, System.out);
            new Game(new Board(), player1, player2, System.out).startGame();
        }
    }
}
