package ru.job4j.tracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Меню программы.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class MenuTracker {
    /** Интерфейс пользовательского ввода */
    private Input input;
    /** Хранилище заявок */
    private Tracker tracker;
    /** Список пунктов меню */
    private List<UserAction> actions = new ArrayList<>();
    /** Переменная для завершения работы программы */
    private boolean exit = false;

    /**
     * Конструктор - создание объекта с заданными параметрами
     * @param input интерфейс ввода
     * @param tracker хранилище заявок
     */
    public MenuTracker(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Получить список пунктов меню
     * @return все пункты меню
     */
    public List<UserAction> getActions() {
        return this.actions;
    }

    /**
     * Получить значение переменной для завершения работы программы
     * @return false - во время работы программы, true - при завершении
     */
    public boolean getExit() {
        return this.exit;
    }

    /**
     * Выполнение выбранного пункта меню
     */
    public void select(int key) {
        this.actions.get(key).execute(this.input, this.tracker);
    }

    /**
     * Заполняет список пунктов меню
     */
    public void fillActions() {
        int key = 0;
        this.actions.add(this.new AddItem(key++, "Add new Item"));
        this.actions.add(this.new ShowAllItems(key++, "Show all items"));
        this.actions.add(new MenuTracker.EditItem(key++, "Edit item"));
        this.actions.add(new DeleteItem(key++, "Delete item"));
        this.actions.add(this.new FindById(key++, "Find item by id"));
        this.actions.add(this.new FindByName(key++, "Find item by name"));
        this.actions.add(this.new Exit(key++, "Exit"));
    }

    /**
     * Выводит на экран меню команд
     */
    public void showMenu() {
        System.out.println("Меню");
        for (UserAction action : this.actions) {
            if (action != null) {
                System.out.println(String.format("%s. %s", action.key(), action.info()));
            }
        }
    }

    /**
     * Вывод на консоль списка заявок
     * @param items список заявок для вывода
     */
    private void showAllItems(List<Item> items) {
        if (items.size() == 0) {
            System.out.println("Заявок нет");
        } else {
            System.out.println("*");
            for (Item item : items) {
                System.out.println(item);
                System.out.println("*");
            }
        }
    }

    /**
     * Реализация пункта меню "Add new Item"
     */
    private class AddItem extends BaseAction {

        public AddItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Добавление новой заявки ------------");
            String name = input.ask("Введите имя заявки : ");
            String desc = input.ask("Введите описание заявки : ");
            Item item = new Item(name, desc);
            tracker.add(item);
            System.out.println("------------ Новая заявка с Id : " + item.getId() + "------------");
        }
    }

    /**
     * Реализация пункта меню "Show all items"
     */
    private class ShowAllItems extends BaseAction {

        public ShowAllItems(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            MenuTracker.this.showAllItems(tracker.getAll());
        }
    }

    /**
     * Реализация пункта меню "Edit item"
     */
    private static class EditItem extends BaseAction {

        public EditItem(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Редактирование заявки ------------");
            String id = input.ask("Введите id заявки : ");
            if (!tracker.findById(id).isPresent()) {
                System.out.println("Заявки с заданным id не существует");
            } else {
                String name = input.ask("Введите новое имя заявки : ");
                String desc = input.ask("Введите описание заявки : ");
                Item item = new Item(name, desc);
                tracker.replace(id, item);
                System.out.println("------------ Заявка отредактирована ------------");
            }
        }
    }

    /**
     * Реализация пункта меню "Find item by id"
     */
    private class FindById extends BaseAction {

        public FindById(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Найти заявку по id ------------");
            String id = input.ask("Введите id заявки : ");
            if (tracker.findById(id).isPresent()) {
                System.out.println(tracker.findById(id).get());
            } else {
                System.out.println("Заявки не существует");
            }
        }
    }

    /**
     * Реализация пункта меню "Find items by name"
     */
    private class FindByName extends BaseAction {

        public FindByName(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Найти заявки по имени ------------");
            String name = input.ask("Введите имя заявки : ");
            MenuTracker.this.showAllItems(tracker.findByName(name));
        }
    }

    /**
     * Реализация пункта меню "Exit"
     */
    private class Exit extends BaseAction {

        public Exit(int key, String name) {
            super(key, name);
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            MenuTracker.this.exit = true;
        }
    }
}

/**
 * Реализация пункта меню "Delete item"
 */
class DeleteItem extends BaseAction {

    public DeleteItem(int key, String name) {
        super(key, name);
    }

    @Override
    public void execute(Input input, Tracker tracker) {
        System.out.println("------------ Удаление заявки ------------");
        String id = input.ask("Введите id заявки : ");
        if (!tracker.findById(id).isPresent()) {
            System.out.println("Заявки с заданным id не существует");
        } else {
            tracker.delete(id);
            System.out.println("------------ Заявка удалена ------------");
        }
    }
}
