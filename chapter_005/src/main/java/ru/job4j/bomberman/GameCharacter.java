package ru.job4j.bomberman;

/**
 * Абстрактный класс игрового персонажа.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public abstract class GameCharacter extends Thread {
    /** Текущая позиция персонажа */
    protected Cell position;
    /** Направление движения персонажа */
    protected Facing direction;
    /** Игровое поле */
    protected final Board board;

    GameCharacter(Board board) {
        this.board = board;
    }

    public Cell getPosition() {
        return position;
    }

    public Facing getDirection() {
        return direction;
    }

    public Board getBoard() {
        return board;
    }

    public void setPosition(Cell position) {
        this.position = position;
    }

    public void setDirection(Facing direction) {
        this.direction = direction;
    }

    /**
     * Перемещение персонажа по игровому полю
     */
    abstract void performMove();

    @Override
    public final void run() {
        if (this.board.setStartingPositionSucceed(this.position)) {
            this.performMove();
        }
    }
}
