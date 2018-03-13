package ru.job4j.chess.exceptions;

/**
 * Генерируется при попытке сдвинуть фигуру на пустом поле.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class FigureNotFoundException extends Exception {
    public FigureNotFoundException(String msg) {
        super(msg);
    }
}
