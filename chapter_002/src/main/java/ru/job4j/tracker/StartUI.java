package ru.job4j.tracker;

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
        while (!menu.getExit()) {
            menu.showMenu();
            int key = Integer.valueOf(this.input.ask("Введите пункт меню : "));
            menu.select(key);
        }
    }

    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}
