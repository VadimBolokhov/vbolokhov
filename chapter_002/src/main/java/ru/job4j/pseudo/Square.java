package ru.job4j.pseudo;

import java.util.StringJoiner;

/**
 * Рисование квадрата.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Square implements Shape {
    @Override
    public String draw() {
        StringJoiner pic = new StringJoiner(System.lineSeparator());
        pic.add("****");
        pic.add("*  *");
        pic.add("*  *");
        pic.add("****");
        return pic.toString();
    }
}
