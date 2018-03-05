package ru.job4j.pseudo;

/**
 * Рисование фигур.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Paint {
    /**
     * Рисует фигуру заданной формы
     * @param shape форма
     */
    public void draw(Shape shape) {
        System.out.println(shape.draw());
    }
}

