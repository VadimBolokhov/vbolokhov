package ru.job4j.condition;

/**
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Вычисляет расстояние между двумя точками на плоскости
     * @param that - Точка, до которой вычисляется расстояние
     * @return Результат вычисления
     */
    public double dinstanceTo(Point that) {
        double result = Math.sqrt(
                Math.pow(that.x - this.x, 2) + Math.pow(that.y - this.y, 2)
        );
        return result;
    }
}
