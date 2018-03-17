package ru.job4j.chess;

import org.junit.Before;
import org.junit.Test;
import ru.job4j.chess.exceptions.IncorrectPositionException;
import ru.job4j.chess.pieces.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Board Test.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class BoardTest {
    private Board chess;
    private final Cell source = new Cell(0, 0);

    /**
     * Инициализация полей
     */
    @Before
    public void init() {
        this.chess = new Board();
    }

    /**
     * Тест метода move(), когда ход слоном сделан корректно
     */
    @Test
    public void whenMoveBishop() {
        Cell dest = new Cell(3, 3);
        this.chess.add(new Bishop(this.source));
        try {
            this.chess.move(this.source, dest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        assertTrue(this.chess.getFigure(dest).get() instanceof Bishop);
    }

    /**
     * Тест метода move(), когда на заданном поле фигура не найдена
     */
    @Test
    public void whenMoveEmptySpace() {
        try {
            this.chess.move(this.source, new Cell(1, 1));
        } catch (Exception e) {
            assertThat(e.getMessage(), is("На заданном поле нет фигуры."));
        }
    }

    /**
     * Тест метода move(), при попытке сделать некорректный ход
     */
    @Test
    public void whenMoveToWrongPosition() {
        this.chess.add(new Bishop(this.source));
        try {
            this.chess.move(this.source, new Cell(1, 0));
        } catch (Exception e) {
            assertThat(e.getMessage(), is("Слон так не ходит!"));
        }
    }

    /**
     * Тест метода move(), когда путь фигуры заблокирован другой фигурой
     */
    @Test
    public void whenMovePathIsBlocked() {
        Cell dest = new Cell(1, 1);
        this.chess.add(new Bishop(this.source));
        this.chess.add(new Bishop(dest));
        try {
            this.chess.move(this.source, dest);
        } catch (Exception e) {
            assertThat(e.getMessage(), is("На пути есть фигуры"));
        }
    }

    /**
     * Тест метода add()
     */
    @Test
    public void whenAddFigureThenBoardHasSameFigure() {
        Figure bishop = new Bishop(this.source);
        this.chess.add(bishop);
        assertThat(this.chess.getFigure(this.source).get(), is(bishop));
    }

    /**
     * Тест метода add() при попытке неправильного расположения фигуры
     */
    @Test
    public void whenAddFigureToIncorrectSpace() {
        this.chess.add(new Bishop(this.source));
        try {
            this.chess.add(new Bishop(new Cell(0, 8)));
        } catch (IncorrectPositionException ipe) {
            assertThat(ipe.getMessage(), is("Невозможно поставить фигуру на поле [0, 8]"));
        }
        try {
            this.chess.add(new Bishop(this.source));
        } catch (IncorrectPositionException ipe) {
            assertThat(ipe.getMessage(), is("Невозможно поставить фигуру на поле [0, 0]"));
        }
    }
}