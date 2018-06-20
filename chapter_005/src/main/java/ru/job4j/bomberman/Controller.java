package ru.job4j.bomberman;

/**
 * Управление персонажем.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public abstract class Controller {
    protected final GameCharacter character;

    Controller(GameCharacter character) {
        this.character = character;
    }

    /**
     * Меняет направление движения персонажа на 90 градусов влево
     */
    void turnLeft() {
        character.getDirection().turnLeft();
    }

    /**
     * Меняет направление движения персонажа на 90 градусов вправо
     */
    void turnRight() {
        character.getDirection().turnRight();
    }

    /**
     * Попытка сделать шаг вперед
     * @return {@code true} - если попытка удалась, {@code false} - в противном случае
     */
    boolean moveForward() {
        boolean result = false;
        Cell nextPosition = this.nextCell();
        if (character.getBoard().move(character.getPosition(), nextPosition)) {
            character.setPosition(nextPosition);
            result = true;
        }
        return result;
    }

    private Cell nextCell() {
        return new Cell(character.getPosition().getX() + character.getDirection().dx(),
                character.getPosition().getY() + character.getDirection().dy());
    }

    public GameCharacter character() {
        return this.character;
    }

    /**
     * Управляет перемещением персонажа по игровому полю
     */
    abstract void walk();
}
