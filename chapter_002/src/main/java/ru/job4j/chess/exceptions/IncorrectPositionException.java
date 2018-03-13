package ru.job4j.chess.exceptions;

/**
 * Исключение выбрасывается при попытке поставить фигуру
 * мимо доски либо на занятое поле.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class IncorrectPositionException extends RuntimeException {
    public IncorrectPositionException(String msg) {
        super(msg);
    }
}
