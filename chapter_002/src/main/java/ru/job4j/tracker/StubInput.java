package ru.job4j.tracker;

/**
 * Замена ввода пользователя.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class StubInput implements Input {
    /** Это поле содержит последовательность ответов пользователя */
    private final String[] value;
    /** Поле считает количество вызовов метода ask */
    private int position;

    /**
     * Конструктор - создание нового объекта с параметром
     * @param value массив ответов пользователя
     */
    public StubInput(final String[] value) {
        this.value = value;
    }

    @Override
    public String ask(String quesiton) {
        return this.value[this.position++];
    }
}
