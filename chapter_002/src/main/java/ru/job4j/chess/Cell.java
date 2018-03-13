package ru.job4j.chess;

/**
 * Клетка шахматной доски.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Cell {
    private int x, y;

    /**
     * Конструктор - создание объекта с заданными координатами
     * @param x координата x
     * @param y координата y
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }


    public int getY() {
        return this.y;
    }

    /**
     * Проверка, что ячейка находится на шахматной доске 8x8
     * @return true - если обе координаты в диапазоне [0, 7],
     * false - в противном случае
     */
    public boolean valid() {
        return 0 <= this.x && this.x < 8 && 0 <= this.y && this.y < 8;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof Cell) {
            Cell cell = (Cell) obj;
            result = this.x == cell.getX() && this.y == cell.getY();
        }
        return result;
    }
}
