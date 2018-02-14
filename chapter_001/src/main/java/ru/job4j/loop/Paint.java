package ru.job4j.loop;

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
        StringBuilder screen = new StringBuilder();
        int width = 2 * height - 1;
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != width; column++) {
                if (row >= height - column - 1 && row + height - 1 >= column) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }

    /**
     * Рисует правостороннюю пирамиду из символов ^ и пробелов.
     *
     * @param height Высота пирамиды.
     * @return Пирамида.
     */
    public String rightTrl(int height) {
        StringBuilder screen = new StringBuilder();
        int width = height;
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != width; column++) {
                if (row >= column) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }

    /**
     * Рисует левостороннюю пирамиду из символов ^ и пробелов.
     *
     * @param height Высота пирамиды.
     * @return Пирамида.
     */
    public String leftTrl(int height) {
        StringBuilder screen = new StringBuilder();
        int width = height;
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != width; column++) {
                if (row >= width - column - 1) {
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
