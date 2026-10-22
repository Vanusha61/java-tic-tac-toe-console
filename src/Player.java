public abstract class Player {
    private final String name;
    private final char mark;

    protected Player(String name, char mark) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Имя не должно быть пустым");
        if (mark != 'X' && mark != 'O') throw new IllegalArgumentException("Метка должна быть X или O");
        this.name = name;
        this.mark = mark;
    }

    public String getName() { return name; }
    public char getMark() { return mark; }

    /** Выполняет допустимый ход; false означает завершение ввода или отмену. */
    public abstract boolean makeMove(Board board);
}
