package ru.job4j.chess.exceptions;

/**
 * Генерируется при попытке обращения к методам класса NullFigure().
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class NullFigureException extends RuntimeException {
    public NullFigureException(String msg) {
        super(msg);
    }
}
