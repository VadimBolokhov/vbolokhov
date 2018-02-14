package ru.job4j.loop;

import java.util.function.BiPredicate;

/**
 * Pyramid.
 *
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Paint {
    /**
     * Рисует пирамиду из символов ^ и пробелов.
     *
     * @param height Высота пирамиды.
     * @return Пирамида.
     */
    public String pyramid(int height) {
        return this.loopBy(
                height,
                2 * height - 1,
                (row, column) -> row >= height - column - 1 && row + height - 1 >= column
        );
    }

    /**
     * Рисует правостороннюю пирамиду из символов ^ и пробелов.
     *
     * @param height Высота пирамиды.
     * @return Пирамида.
     */
    public String rightTrl(int height) {
        return this.loopBy(
                height,
                height,
                (row, column) -> row >= column
        );
    }

    /**
     * Рисует левостороннюю пирамиду из символов ^ и пробелов.
     *
     * @param height Высота пирамиды.
     * @return Пирамида.
     */
    public String leftTrl(int height) {
        return this.loopBy(
                height,
                height,
                (row, column) -> row >= height - column - 1
        );
    }

    /**
     * Рисует пирамиду различной формы, определяемой параметрами.
     *
     * @param height Высота пирамиды.
     * @param width Ширина пирамиды.
     * @param predict Определяет форму пирамиды.
     * @return Пирамида.
     */
    public String loopBy(int height, int width, BiPredicate<Integer, Integer> predict) {
        StringBuilder screen = new StringBuilder();
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != width; column++) {
                if (predict.test(row, column)) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }
}
