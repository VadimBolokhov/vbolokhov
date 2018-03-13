package ru.job4j.chess.exceptions;

/**
 * Генерируется, когда путь фигуры заблокирован.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class OccupiedWayException extends Exception {
    public OccupiedWayException(String msg) {
        super(msg);
    }
}
