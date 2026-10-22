import java.io.PrintStream;

public class Game {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private final PrintStream out;

    public Game(Board board, Player player1, Player player2, PrintStream out) {
        if (player1.getMark() != 'X' || player2.getMark() != 'O') {
            throw new IllegalArgumentException("Первый игрок использует X, второй — O");
        }
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.out = out;
    }

    public void startGame() {
        out.println("Крестики-нолики. Первым ходит X.");
        board.displayBoard(out);
        while (true) {
            if (!currentPlayer.makeMove(board)) {
                out.println("Игра прервана.");
                return;
            }
            board.displayBoard(out);
            if (checkForWinOrDraw()) return;
            switchPlayer();
        }
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }

    public boolean checkForWinOrDraw() {
        if (board.checkWin(currentPlayer.getMark())) {
            out.printf("Победил %s (%c)!%n", currentPlayer.getName(), currentPlayer.getMark());
            return true;
        }
        if (board.isFull()) {
            out.println("Ничья!");
            return true;
        }
        return false;
    }
}
