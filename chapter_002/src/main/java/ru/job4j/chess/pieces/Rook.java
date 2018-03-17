package ru.job4j.chess.pieces;

import ru.job4j.chess.Cell;
import ru.job4j.chess.Figure;
import ru.job4j.chess.exceptions.ImpossibleMoveException;

/**
 * Шахматная фигура - ладья.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Rook extends Figure {

    /**
     * Конструктор - создание фигуры на заданном поле
     * @param position поле
     */
    public Rook(Cell position) {
        super(position);
    }

    /**
     * Конструктор - создание объекта с заданными координатами
     * @param x координата x
     * @param y координата y
     */
    public Rook(int x, int y) {
        super(x, y);
    }

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        return new PathFinder().getPath(this, source, dest);
    }

    @Override
    public Figure copy(Cell dest) {
        return new Rook(dest);
    }

    @Override
    public boolean validPath(Cell source, Cell dest) {
        int dx = dest.getX() - source.getX();
        int dy = dest.getY() - source.getY();
        return dest.valid() && dx != dy && dx * dy == 0;
    }

    /**
     * Возвращает название фигуры
     * @return Ладья
     */
    @Override
    public String toString() {
        return "Ладья";
    }
}
