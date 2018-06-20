package ru.job4j.bomberman;

import java.util.Objects;

/**
 * Ячейка игрового поля.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Cell {
    private final int x;
    private final int y;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("[%d, %d]", this.x, this.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
