package ru.job4j.chess.pieces;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.chess.Cell;
import ru.job4j.chess.exceptions.ImpossibleMoveException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Pawn Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class PawnTest {
    /**
     * Тест метода way()
     */
    @Test
    public void whenValidWaySetThenReturnArrayOfCells() {
        Cell source = new Cell(3, 6);
        Cell dest = new Cell(3, 4);
        Pawn pawn = new Pawn(source, -1);
        Cell[] expected = {new Cell(3, 5), dest};
        try {
            Cell[] result = pawn.way(source, dest);
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
        Cell source = new Cell(1, 1);
        Pawn pawn = new Pawn(source, 1);
        try {
            pawn.way(source, new Cell(2, 2));
        } catch (ImpossibleMoveException ime) {
            assertThat(ime.getMessage(), is("Пешка так не ходит!"));
        }
    }

    /**
     * Тест метода validPath()
     */
    @Test
    public void whenValidPath() {
        Cell source = new Cell(2, 2);
        Pawn pawn = new Pawn(source, 1);
        assertTrue(pawn.validPath(source, new Cell(2, 3)));
        assertFalse(pawn.validPath(source, new Cell(2, 4)));
    }
}