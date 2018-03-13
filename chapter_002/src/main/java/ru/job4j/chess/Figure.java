package ru.job4j.chess;

import ru.job4j.chess.exceptions.ImpossibleMoveException;

/**
 * Абстрактная шахматная фигура
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public abstract class Figure {
    /** Позиция фигуры на доске */
    public final Cell position;

    /**
     * Конструктор - создание объекта с заданным параметром
     * @param position поле шахматной доски
     */
    public Figure(Cell position) {
        this.position = position;
    }
    /**
     * Конструктор - создание объекта с заданными координатами
     * @param x координата X
     * @param y координата Y
     */
    public Figure(int x, int y) {
        this.position = new Cell(x, y);
    }

    /**
     * Путь фигуры от одного поля до другого
     * @param source начальное поле
     * @param dest конечное поле
     * @return массив клеток пути
     * @throws ImpossibleMoveException
     */
    public abstract Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException;

    /**
     * Создает копию фигуры на заданном поле
     * @param dest поле, куда поставить копию
     * @return фигура
     */
    public abstract Figure copy(Cell dest);

    /**
     * Проверяет может ли данная фигура пойти на заданное поле
     * @param source начальное поле
     * @param dest конечное поле
     * @return true - если фигура так ходит, false - если походить не может
     */
    public abstract boolean validPath(Cell source, Cell dest);

    @Override
    public abstract String toString();
}
