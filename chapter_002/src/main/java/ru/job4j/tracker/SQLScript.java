package ru.job4j.tracker;

/**
 * Скрипты для работы с базой данных.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SQLScript {

    static final String ITEM_ID = "id";
    static final String ITEM_NAME = "name";
    static final String ITEM_DESC = "description";
    static final String ITEM_CREATE_DATE = "create_date";
    static final String COMM_ID = "id";
    static final String COMM_TEXT = "text";
    static final String COMM_ITEM_ID = "item_id";

    /**
     * Создание таблицы {@code items}, если не существует
     * @return скрипт
     */
    public String createItemsTable() {
        return new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS items(")
                .append(ITEM_ID).append(" serial PRIMARY KEY, ")
                .append(ITEM_NAME).append(" varchar(50) NOT NULL, ")
                .append(ITEM_DESC).append(" varchar(255), ")
                .append(ITEM_CREATE_DATE).append(" date NOT NULL);")
                .toString();
    }

    /**
     * Создание таблицы {@code comments}, если не существует
     * @return скрипт
     */
    public String createCommentsTable() {
        return new StringBuilder()
                .append("CREATE TABLE IF NOT EXISTS comments(")
                .append(COMM_ID).append(" serial PRIMARY KEY, ")
                .append(COMM_TEXT).append(" varchar(255) NOT NULL, ")
                .append(COMM_ITEM_ID).append(" int NOT NULL REFERENCES items(id));")
                .toString();
    }

    /**
     * Возвращает скрипт для добавления элемента в таблицу {@code items}
     * @return скрипт
     */
    public String insert() {
        return String.format("INSERT INTO items(%s, %s, %s) VALUES (?, ?, CURRENT_DATE);",
                ITEM_NAME, ITEM_DESC, ITEM_CREATE_DATE
        );
    }

    /**
     * Скрипт для возвращения всех элементов из таблицы {@code items}
     * @return скрипт
     */
    public String getItems() {
        return String.format("SELECT * FROM items ORDER BY %s, %s;",
                ITEM_CREATE_DATE, ITEM_ID
        );
    }

    /**
     * Скрипт для поиска элемента по идентификатору
     * @return скрипт
     */
    public String findById() {
        return String.format("SELECT * FROM items WHERE %s = ?;", ITEM_ID);
    }

    /**
     * Скрипт для удаления элемента из таблицы {@code items} по идентификатору
     * @return скрипт
     */
    public String delete() {
        return String.format("DELETE FROM items WHERE %s = ?;", ITEM_ID);
    }

    /**
     * Скрипт для получения всех заявок с заданным именем
     * @return скрипт
     */
    public String findByName() {
        return String.format("SELECT * FROM items WHERE %s = ? ORDER BY %s, %s;",
                ITEM_NAME, ITEM_CREATE_DATE, ITEM_ID
        );
    }

    /**
     * Скрипт для замены заявки в таблице {@code items}
     * @return скрипт
     */
    public String replace() {
        return String.format("UPDATE items SET %s = ?, %s = ? WHERE %s = ?;",
                ITEM_NAME, ITEM_DESC, ITEM_ID
        );
    }
}
