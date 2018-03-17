package ru.job4j.chess.pieces;

import ru.job4j.chess.Cell;
import ru.job4j.chess.Figure;
import ru.job4j.chess.exceptions.ImpossibleMoveException;

/**
 * Шахматная фигура - король.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class King extends Figure {

    /**
     * Конструктор - создание фигуры на заданном поле
     * @param position поле
     */
    public King(Cell position) {
        super(position);
    }

    /**
     * Конструктор - создание объекта с заданными координатами
     * @param x координата x
     * @param y координата y
     */
    public King(int x, int y) {
        super(x, y);
    }

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        if (!this.validPath(source, dest)) {
            throw new ImpossibleMoveException("Король так не ходит!");
        }
        return new Cell[] {dest};
    }

    @Override
    public Figure copy(Cell dest) {
        return new King(dest);
    }

    @Override
    public boolean validPath(Cell source, Cell dest) {
        int dx = Math.abs(dest.getX() - source.getX());
        int dy = Math.abs(dest.getY() - source.getY());
        return dest.valid() && !source.equals(dest) && dx < 2 && dy < 2;
    }

    /**
     * Возвращает название фигуры
     * @return Король
     */
    @Override
    public String toString() {
        return "Король";
    }
}
