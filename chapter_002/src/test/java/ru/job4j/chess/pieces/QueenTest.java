package ru.job4j.chess.pieces;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.chess.Cell;
import ru.job4j.chess.exceptions.ImpossibleMoveException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Queen Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class QueenTest {
    private Queen queen;
    private Cell source = new Cell(0, 0);

    @Before
    public void init() {
        this.queen = new Queen(this.source);
    }

    /**
     * Тест метода way()
     */
    @Test
    public void whenValidWaySetThenReturnArrayOfCells() {
        Cell dest = new Cell(2, 2);
        Cell[] expected = {new Cell(1, 1), dest};
        try {
            Cell[] result = this.queen.way(this.source, dest);
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
            this.queen.way(this.source, new Cell(2, 1));
        } catch (ImpossibleMoveException ime) {
            assertThat(ime.getMessage(), is("Ферзь так не ходит!"));
        }
    }

    /**
     * Тест метода validPath()
     */
    @Test
    public void whenValidPath() {
        assertTrue(this.queen.validPath(this.source, new Cell(1, 1)));
        assertFalse(this.queen.validPath(this.source, new Cell(1, 2)));
    }
}