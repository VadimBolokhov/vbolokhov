package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс пользовательского интерфейса.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class StartUI {
    /** Получение данных от полльзователя */
    private final Input input;
    /** Хранилище заявок */
    private final Tracker tracker;
    /** Ключи пунктов меню */
    private List<Integer> range;

    /**
     * Конструтор инициализирующий поля.
     * @param input ввод данных.
     * @param tracker хранилище заявок.
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Основной цикл программы
     */
    public void init() {
        MenuTracker menu = new MenuTracker(this.input, this.tracker);
        menu.fillActions();
        int key = -1;
        this.range = new ArrayList<>();
        for (UserAction action : menu.getActions()) {
            this.range.add(action.key());
        }
        while (!menu.getExit()) {
            menu.showMenu();
            key = this.input.ask("Введите пункт меню : ", this.range);
            menu.select(key);
        }
    }

    public static void main(String[] args) {
        new StartUI(new ValidateInput(new ConsoleInput()), new Tracker()).init();
    }
}
