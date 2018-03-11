package ru.job4j.tracker;

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
    /** Массив пунктов меню */
    private UserAction[] actions = new UserAction[7];
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
     * Получить массив пунктов меню
     * @return все пункты меню
     */
    public UserAction[] getActions() {
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
        this.actions[key].execute(this.input, this.tracker);
    }

    /**
     * Заполняет массив пунктов меню
     */
    public void fillActions() {
        int key = 0;
        this.actions[key] = this.new AddItem(key++, "Add new Item");
        this.actions[key] = this.new ShowAllItems(key++, "Show all items");
        this.actions[key] = new MenuTracker.EditItem(key++, "Edit item");
        this.actions[key] = new DeleteItem(key++, "Delete item");
        this.actions[key] = this.new FindById(key++, "Find item by id");
        this.actions[key] = this.new FindByName(key++, "Find item by name");
        this.actions[key] = this.new Exit(key++, "Exit");
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
     * @param items массив заявок для вывода
     */
    private void showAllItems(Item[] items) {
        if (items.length == 0) {
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
            if (tracker.findById(id) instanceof EmptyItem) {
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
            System.out.println(tracker.findById(id));
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
        if (tracker.findById(id) instanceof EmptyItem) {
            System.out.println("Заявки с заданным id не существует");
        } else {
            tracker.delete(id);
            System.out.println("------------ Заявка удалена ------------");
        }
    }
}
