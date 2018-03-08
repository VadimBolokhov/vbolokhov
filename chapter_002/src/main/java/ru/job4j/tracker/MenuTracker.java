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
     * Инициализация меню
     */
    public void init() {
        this.fillActions();
        while (!exit) {
            this.showMenu();
            int key = Integer.valueOf(this.input.ask("Введите пункт меню : "));
            this.actions[key].execute(this.input, this.tracker);
        }
    }

    /**
     * Заполняет массив пунктов меню
     */
    private void fillActions() {
        this.actions[0] = this.new AddItem();
        this.actions[1] = this.new ShowAllItems();
        this.actions[2] = new MenuTracker.EditItem();
        this.actions[3] = new DeleteItem();
        this.actions[4] = this.new FindById();
        this.actions[5] = this.new FindByName();
        this.actions[6] = this.new Exit();
    }

    /**
     * Выводит на экран меню команд
     */
    private void showMenu() {
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
    private class AddItem implements UserAction {
        @Override
        public int key() {
            return 0;
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

        @Override
        public String info() {
            return "Add new Item";
        }
    }

    /**
     * Реализация пункта меню "Show all items"
     */
    private class ShowAllItems implements UserAction {
        @Override
        public int key() {
            return 1;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            MenuTracker.this.showAllItems(tracker.getAll());
        }

        @Override
        public String info() {
            return "Show all items";
        }
    }

    /**
     * Реализация пункта меню "Edit item"
     */
    private static class EditItem implements UserAction {
        @Override
        public int key() {
            return 2;
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

        @Override
        public String info() {
            return "Edit item";
        }
    }

    /**
     * Реализация пункта меню "Find item by id"
     */
    private class FindById implements UserAction {
        @Override
        public int key() {
            return 4;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Найти заявку по id ------------");
            String id = input.ask("Введите id заявки : ");
            System.out.println(tracker.findById(id));
        }

        @Override
        public String info() {
            return "Find item by id";
        }
    }

    /**
     * Реализация пункта меню "Find items by name"
     */
    private class FindByName implements UserAction {
        @Override
        public int key() {
            return 5;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            System.out.println("------------ Найти заявки по имени ------------");
            String name = input.ask("Введите имя заявки : ");
            MenuTracker.this.showAllItems(tracker.findByName(name));
        }

        @Override
        public String info() {
            return "Find items by name";
        }
    }

    /**
     * Реализация пункта меню "Exit"
     */
    private class Exit implements UserAction {
        @Override
        public int key() {
            return 6;
        }

        @Override
        public void execute(Input input, Tracker tracker) {
            MenuTracker.this.exit = true;
        }

        @Override
        public String info() {
            return "Exit";
        }
    }
}

/**
 * Реализация пункта меню "Delete item"
 */
class DeleteItem implements UserAction {
    @Override
    public int key() {
        return 3;
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

    @Override
    public String info() {
        return "Delete Item";
    }
}
