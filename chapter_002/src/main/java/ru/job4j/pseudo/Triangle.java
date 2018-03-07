package ru.job4j.pseudo;

import java.util.StringJoiner;

/**
 * Рисование треугольника.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Triangle implements Shape {
    @Override
    public String draw() {
        StringJoiner pic = new StringJoiner(System.lineSeparator());
        pic.add("  ^  ");
        pic.add(" / \\ ");
        pic.add("/___\\");
        return pic.toString();
    }
}
