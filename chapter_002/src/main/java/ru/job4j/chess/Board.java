package ru.job4j.chess;

import ru.job4j.chess.exceptions.FigureNotFoundException;
import ru.job4j.chess.exceptions.ImpossibleMoveException;
import ru.job4j.chess.exceptions.IncorrectPositionException;
import ru.job4j.chess.exceptions.OccupiedWayException;
import ru.job4j.chess.pieces.NullFigure;

/**
 * Шахматная доска.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Board {
    private Figure[] figures = new Figure[32];
    private int position = 0;

    /**
     * Добавляет фигуру на доску. Допустимый диапазон координат [0, 7].
     * Фигуру можно поставить только на свободное поле.
     * @param figure шахматная фигура
     */
    public void add(Figure figure) throws IncorrectPositionException {
        if (this.getFigure(figure.position) instanceof NullFigure && figure.position.valid()) {
            this.figures[this.position++] = figure;
        } else {
            throw new IncorrectPositionException(
                    String.format("Невозможно поставить фигуру на поле %d %d",
                            figure.position.getX(),
                            figure.position.getY())
            );
        }
    }

    /**
     * Проверка какая фигура стоит на заданном поле
     * @param target клетка
     * @return фигура - если есть, NullFigure - если клетка пуста
     */
    public Figure getFigure(Cell target) {
        Figure result = new NullFigure(target);
        for(Figure fig : this.figures) {
            if(fig != null && fig.position.equals(target)) {
                result = fig;
                break;
            }
        }
        return result;
    }

    /**
     * Проверяет, нет ли на пути фигуры других фигур
     * @param path заданный путь
     * @return true - если путь свободен, false - если на пути есть фигуры
     */
    private boolean clearPath(Cell[] path) {
        boolean clear = true;
        for (Cell cell : path) {
            if (!(this.getFigure(cell) instanceof NullFigure)) {
                clear = false;
                break;
            }
        }
        return clear;
    }

    /**
     * Передвигает фигуру с одного поля на другое, если это возможно
     * @param source начальная позиция
     * @param dest куда поставить
     * @return true - если фигуру удалось передвинуть, false - в противном случае
     * @throws ImpossibleMoveException
     * @throws OccupiedWayException
     * @throws FigureNotFoundException
     */
    public boolean move(Cell source, Cell dest)
            throws ImpossibleMoveException,
            OccupiedWayException,
            FigureNotFoundException {
        boolean movable = false;
        for (int i = 0; i < this.figures.length; i++) {
            if (this.figures[i] != null && this.figures[i].position.equals(source)) {
                Cell[] path = this.figures[i].way(source, dest);
                if (!this.clearPath(path)) {
                    throw new OccupiedWayException("На пути есть фигуры");
                }
                this.figures[i] = this.figures[i].copy(dest);
                movable = true;
                break;
            } else {
                throw new FigureNotFoundException("На заданном поле нет фигуры.");
            }
        }
        return movable;
    }
}