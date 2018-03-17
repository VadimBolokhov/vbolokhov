package ru.job4j.chess.pieces;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.chess.Cell;
import ru.job4j.chess.exceptions.ImpossibleMoveException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Rook Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class RookTest {
    private Rook rook;
    private Cell source = new Cell(0, 0);

    @Before
    public void init() {
        this.rook = new Rook(this.source);
    }

    /**
     * Тест метода way()
     */
    @Test
    public void whenValidWaySetThenReturnArrayOfCells() {
        Cell dest = new Cell(2, 0);
        Cell[] expected = {new Cell(1, 0), dest};
        try {
            Cell[] result = this.rook.way(this.source, dest);
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
            this.rook.way(this.source, new Cell(1, 1));
        } catch (ImpossibleMoveException ime) {
            assertThat(ime.getMessage(), is("Ладья так не ходит!"));
        }
    }

    /**
     * Тест метода validPath()
     */
    @Test
    public void whenValidPath() {
        assertTrue(this.rook.validPath(this.source, new Cell(0, 2)));
        assertFalse(this.rook.validPath(this.source, new Cell(1, 1)));
    }
}