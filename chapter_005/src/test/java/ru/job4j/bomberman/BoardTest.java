package ru.job4j.bomberman;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Board Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
@Ignore
public class BoardTest {
    @Test
    public void whenGameRunningThenCharacterMoving() throws Exception {
        Board board = new Board(3, 3);
        Hero hero = new Hero(board);
        hero.setInput(new PatrolController(hero));
        board.addHero(hero, new Cell(1, 1), new Facing(Facing.Direction.EAST));
        board.startGame();
        Thread.sleep(1100);
        assertThat(board.getCharacters().get(0).getPosition(), is(new Cell(2, 1)));
        board.abortGame();
    }
}