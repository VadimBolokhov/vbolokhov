package ru.job4j.chess.pieces;

import ru.job4j.chess.Cell;
import ru.job4j.chess.Figure;
import ru.job4j.chess.exceptions.ImpossibleMoveException;

/**
 * Шахматная фигура - пешка.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Pawn extends Figure {
    /** Сторона, за которую играет пешка */
    private final int side;
    /** True - если пешка в начальной позиции */
    private boolean start;

    /**
     * Конструктор - создание фигуры на заданном поле
     * @param position поле
     */
    public Pawn(Cell position) {
        this(position, 1);
    }

    /**
     * Конструктор - создание фигуры с заданными параметрами
     * @param position поле
     * @param side сторона: -1 или 1
     */
    public Pawn(Cell position, int side) {
        super(position);
        this.side = this.side(side);
        this.start = this.start();
    }

    /**
     * Конструктор - создание объекта с заданными координатами
     * @param x координата x
     * @param y координата y
     */
    public Pawn(int x, int y) {
        this(new Cell(x, y));
    }

    /**
     * Задать начальное значение для стороны
     * @param side сторона(-1 или 1)
     * @return результат
     */
    private int side(int side) {
        return side < 0 ? -1 : 1;
    }

    /**
     * Определяет стоит ли пешка на начальной позиции
     * @return true - если пешка в начальной позиции, false - в противном случае
     */
    private boolean start() {
        boolean result = false;
        if (this.position.getY() == (this.side > 0 ? 1 : 6)) {
            result = true;
        }
        return result;
    }

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        if (!this.validPath(source, dest)) {
            throw new ImpossibleMoveException(String.format("Пешка так не ходит!"));
        }
        int steps = Math.abs(dest.getY() - source.getY());
        Cell[] path = new Cell[steps];
        for (int i = 0; i < path.length; i++) {
            path[i] = new Cell(source.getX(), source.getY() + this.side * (i + 1));
        }
        return path;
    }

    @Override
    public Figure copy(Cell dest) {
        return new Pawn(dest);
    }

    @Override
    public boolean validPath(Cell source, Cell dest) {
        int dx = dest.getX() - source.getX();
        int dy = dest.getY() - source.getY();
        return dest.valid() && dx == 0 && ((this.start && dy == 2 * this.side) || (dy == this.side));
    }

    /**
     * Возвращает название фигуры
     * @return Пешка
     */
    @Override
    public String toString() {
        return "Пешка";
    }
}
