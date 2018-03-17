package ru.job4j.chess.pieces;

import ru.job4j.chess.Cell;
import ru.job4j.chess.Figure;
import ru.job4j.chess.exceptions.ImpossibleMoveException;

public class PathFinder {
    /**
     * Производит рассчёт пути заданной фигуры от одного поля до другого.
     * Используется для фигур: слон, ладья, ферзь
     * @param figure фигура, для которой требуется провести рассчёт
     * @param source начальное поле
     * @param dest конечное поле
     * @return массив клеток пути
     * @throws ImpossibleMoveException если фигура не пожет пойти на заданное поле
     */
    public Cell[] getPath(Figure figure, Cell source, Cell dest) throws ImpossibleMoveException {
        if (!figure.validPath(source, dest)) {
            throw new ImpossibleMoveException(String.format("%s так не ходит!", figure));
        }
        int dx = dest.getX() - source.getX();
        int dy = dest.getY() - source.getY();
        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        Cell[] path = new Cell[steps];
        for (int i = 0; i < path.length; i++) {
            path[i] = new Cell(
                    source.getX() + Integer.compare(dest.getX(), source.getX()) * (i + 1),
                    source.getY() + Integer.compare(dest.getY(), source.getY()) * (i + 1)
            );
        }
        return path;
    }
}
