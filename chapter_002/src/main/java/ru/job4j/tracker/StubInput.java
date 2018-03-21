package ru.job4j.tracker;

import java.util.List;
import java.util.ListIterator;

/**
 * Замена ввода пользователя.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class StubInput implements Input {
    /** Это поле содержит последовательность ответов пользователя */
    private final List<String> value;
    /** Поле считает количество вызовов метода ask */
    private int position;


    private ListIterator<String> it;

    /**
     * Конструктор - создание нового объекта с параметром
     * @param value список ответов пользователя
     */
    public StubInput(final List<String> value) {
        this.value = value;
        it = value.listIterator();
    }

    @Override
    public String ask(String quesiton) {
        return this.it.next();
    }

    @Override
    public int ask(String question, List<Integer> range) {
        int key = Integer.valueOf(this.it.next());
        boolean exist = false;
        for (int i : range) {
            if (key == i) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            throw new MenuOutException("Пункта меню не существует");
        }
        return key;
    }
}
