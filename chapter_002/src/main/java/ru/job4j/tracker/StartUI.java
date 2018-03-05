package ru.job4j.tracker;

import java.util.StringJoiner;

/**
 * Класс пользовательского интерфейса.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class StartUI {
    /** Константа меню для добавления новой заявки */
    private static final String ADD = "0";
    /** Константа для вывода списка заявок на экран */
    private static final String SHOW = "1";
    /** Константа для редактирования заявки */
    private static final String EDIT = "2";
    /** Константа для удаления заявки */
    private static final String DELETE = "3";
    /** Константа для поиска по id */
    private static final String FIND_BY_ID = "4";
    /** Константа для поиска заявок по имени */
    private static final String FIND_BY_NAME = "5";
    /** Константа для выхода из цикла */
    private static final String EXIT = "6";
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
        boolean exit = false;
        while (!exit) {
            this.showMenu();
            String answer = this.input.ask("Введите пункт меню : ");
            if (ADD.equals(answer)) {
                this.createItem();
            } else if (SHOW.equals(answer)) {
                this.showAllItems(this.tracker.getAll());
            } else if (EDIT.equals(answer)) {
                this.editItem();
            } else if (DELETE.equals(answer)) {
                this.deleteItem();
            } else if (FIND_BY_ID.equals(answer)) {
                this.findItemById();
            } else if (FIND_BY_NAME.equals(answer)) {
                this.findItemByName();
            } else if (EXIT.equals(answer)) {
                exit = true;
            }
        }
    }

    /**
     * Создание новой заявки
     */
    private void createItem() {
        System.out.println("------------ Добавление новой заявки ------------");
        String name = this.input.ask("Введите имя заявки : ");
        String desc = this.input.ask("Введите описание заявки : ");
        Item item = new Item(name, desc);
        this.tracker.add(item);
        System.out.println("------------ Новая заявка с Id : " + item.getId() + "------------");
    }

    /**
     * Выводит имя, описание и id заявки
     * @param item заявка
     */
    private void showItem(Item item) {
        System.out.println(item);
    }

    /**
     * Вывод на консоль списка заявок
     * @param items массив заявок для вывода
     */
    private void showAllItems(Item[] items) {
        if (items.length == 0) {
            System.out.println("Заявок нет");
        } else {
            System.out.println("*");
            for (Item item : items) {
                showItem(item);
                System.out.println("*");
            }
        }
    }

    /**
     * Редактирование заявки
     */
    private void editItem() {
        System.out.println("------------ Редактирование заявки ------------");
        String id = this.input.ask("Введите id заявки : ");
        if (this.tracker.findById(id) == null) {
            System.out.println("Заявки с заданным id не существует");
        } else {
            String name = this.input.ask("Введите новое имя заявки : ");
            String desc = this.input.ask("Введите описание заявки : ");
            Item item = new Item(name, desc);
            this.tracker.replace(id, item);
            System.out.println("------------ Заявка отредактирована ------------");
        }
    }

    /**
     * Удаление заявки
     */
    private void deleteItem() {
        System.out.println("------------ Удаление заявки ------------");
        String id = this.input.ask("Введите id заявки : ");
        if (this.tracker.findById(id) == null) {
            System.out.println("Заявки с заданным id не существует");
        } else {
            this.tracker.delete(id);
            System.out.println("------------ Заявка удалена ------------");
        }
    }

    /**
     * Поиск заявки по id
     */
    private void findItemById() {
        System.out.println("------------ Найти заявку по id ------------");
        String id = this.input.ask("Введите id заявки : ");
        System.out.println(this.tracker.findById(id));
    }

    /**
     * Поиск заявки по имени
     */
    private void findItemByName() {
        System.out.println("------------ Найти заявки по имени ------------");
        String name = this.input.ask("Введите имя заявки : ");
        this.showAllItems(this.tracker.findByName(name));
    }

    /**
     * Вывод меню пльзователя на экран
     */
    private void showMenu() {
        String menu = new StringJoiner(System.lineSeparator())
                .add("Меню")
                .add("0. Add new Item")
                .add("1. Show all items")
                .add("2. Edit item")
                .add("3. Delete item")
                .add("4. Find item by Id")
                .add("5. Find items by name")
                .add("6. Exit Program")
                .toString();
        System.out.println(menu);
    }

    public static void main(String[] args) {
        new StartUI(new ConsoleInput(), new Tracker()).init();
    }
}
