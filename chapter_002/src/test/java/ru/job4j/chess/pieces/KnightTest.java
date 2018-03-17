package ru.job4j.chess.pieces;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.chess.Cell;
import ru.job4j.chess.exceptions.ImpossibleMoveException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Knight Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class KnightTest {
    private Knight knight;
    private Cell source = new Cell(0, 0);

    @Before
    public void init() {
        this.knight = new Knight(this.source);
    }

    /**
     * Тест метода way()
     */
    @Test
    public void whenValidWaySetThenReturnArrayOfCells() {
        Cell dest = new Cell(2, 1);
        Cell[] expected = {dest};
        try {
            Cell[] result = this.knight.way(this.source, dest);
            assertThat(result, is(expected));
        } catch (ImpossibleMoveException ime) {
            System.out.println(ime);
        }
    }

    /**
     * Тест матода way() когда ход задан неверно
     */
    @Test
    public void whenInvalidWayThenExceptionThrown() {
        try {
            this.knight.way(this.source, new Cell(2, 2));
        } catch (ImpossibleMoveException ime) {
            assertThat(ime.getMessage(), is("Конь так не ходит!"));
        }
    }

    /**
     * Тест метода validPath()
     */
    @Test
    public void whenValidPath() {
        assertTrue(this.knight.validPath(this.source, new Cell(1, 2)));
        assertFalse(this.knight.validPath(this.source, new Cell(1, 1)));
    }
}