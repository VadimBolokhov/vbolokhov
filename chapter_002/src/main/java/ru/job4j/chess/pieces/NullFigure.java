package ru.job4j.chess.pieces;

import ru.job4j.chess.Cell;
import ru.job4j.chess.exceptions.NullFigureException;
import ru.job4j.chess.Figure;
import ru.job4j.chess.exceptions.ImpossibleMoveException;

/**
 * Несуществующая фигура на пустом поле.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class NullFigure extends Figure {

    public NullFigure(Cell cell) {
        super(cell);
    }

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        this.except(source);
        return new Cell[0];
    }

    @Override
    public Figure copy(Cell dest) {
        this.except(super.position);
        return null;
    }

    @Override
    public boolean validPath(Cell source, Cell dest) {
        this.except(source);
        return false;
    }

    @Override
    public String toString() {
        return "<Пустое поле>";
    }

    private void except(Cell target) {
        throw new NullFigureException(String.format("No figure at X%d Y%d", target.getX(), target.getY()));
    }
}
