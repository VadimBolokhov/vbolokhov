package ru.job4j.chess.exceptions;

/**
 * Генерируется, когда фигура не может сделать заданный ход.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ImpossibleMoveException extends Exception {
    public ImpossibleMoveException(String msg) {
        super(msg);
    }
}
