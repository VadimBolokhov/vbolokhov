package ru.job4j.chess.pieces;

import ru.job4j.chess.Cell;
import ru.job4j.chess.Figure;
import ru.job4j.chess.exceptions.ImpossibleMoveException;

/**
 * Шахматная фигура - ферзь.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Queen extends Figure {
    /**
     * Конструктор - создание фигуры на заданном поле
     * @param position поле
     */
    public Queen(Cell position) {
        super(position);
    }

    /**
     * Конструктор - создание объекта с заданными координатами
     * @param x координата x
     * @param y координата y
     */
    public Queen(int x, int y) {
        super(x, y);
    }

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        return new PathFinder().getPath(this, source, dest);
    }

    @Override
    public Figure copy(Cell dest) {
        return new Queen(dest);
    }

    @Override
    public boolean validPath(Cell source, Cell dest) {
        int dx = Math.abs(dest.getX() - source.getX());
        int dy = Math.abs(dest.getY() - source.getY());
        return dest.valid() && dx * dy == 0 || dx == dy;
    }

    /**
     * Возвращает название фигуры
     * @return Ферзь
     */
    @Override
    public String toString() {
        return "Ферзь";
    }
}
