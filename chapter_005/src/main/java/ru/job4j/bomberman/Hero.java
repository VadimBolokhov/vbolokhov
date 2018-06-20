package ru.job4j.bomberman;

/**
 * Класс героя игры "Бомбермэн".
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Hero extends GameCharacter {
    Controller input;

    public Hero(Board board) {
        super(board);
    }

    public void setInput(Controller input) {
        this.input = input;
    }

    @Override
    void performMove() {
        if (this.input != null) {
            this.input.walk();
        }
    }
}
