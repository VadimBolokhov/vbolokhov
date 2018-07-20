package ru.job4j.tracker;

import java.sql.*;
import java.util.*;
import static ru.job4j.tracker.SQLScript.*;

/**
 * Хранение заявок.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class Tracker implements AutoCloseable {
    /** Соединение с базой данных */
    private Connection connection;
    /** Скрипт для работы с базой данных */
    private PreparedStatement statement;

    Tracker() {
        try {
            this.connection = new DBConnector().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Tracker(Connection connection) {
        this.connection = connection;
    }

    /**
     * Добавляет заявку в список и присваивает ей иднетификатор
     * @param item заявка
     * @return добавленная заявка
     */
    public Item add(Item item) {
        try {
            insertItem(item);
            setNewItemID(item);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement();
        }
        return item;
    }

    private void insertItem(Item item) throws SQLException {
        String insert = new SQLScript().insert();
        this.statement = this.connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
        this.statement.setString(1, item.getName());
        this.statement.setString(2, item.getDesc());
        this.statement.executeUpdate();
    }

    private void setNewItemID(Item item) throws SQLException {
        ResultSet keys = null;
        try {
            keys = this.statement.getGeneratedKeys();
            keys.next();
            int id = keys.getInt(1);
            item.setId(String.valueOf(id));
        } finally {
            this.closeResultSet(keys);
        }
    }

    private void closeStatement() {
        try {
            if (this.statement != null) {
                this.statement.close();
                this.statement = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Заменяет заявку с заданным идентификатором
     * @param id идентификатор
     * @param replace замена
     */
    public void replace(String id, Item replace) {
        String replaceItem = new SQLScript().replace();
        try {
            this.statement = this.connection.prepareStatement(replaceItem);
            this.statement.setString(1, replace.getName());
            this.statement.setString(2, replace.getDesc());
            this.statement.setInt(3, Integer.parseInt(id));
            this.statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement();
        }
    }

    /**
     * Удаляет из списка заявку с заданным Id
     * @param id идентификатор заявки
     */
    public void delete(String id) {
        try {
            String delete = new SQLScript().delete();
            this.statement = this.connection.prepareStatement(delete);
            this.statement.setInt(1, Integer.parseInt(id));
            this.statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement();
        }
    }

    /**
     * Возвращает список всех заявок
     * @return все заявки
     */
    public List<Item> getAll() {
        List<Item> items = new LinkedList<>();
        ResultSet data = null;
        try {
            data = this.fetchData();
            items = this.convertToList(data);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(data);
            this.closeStatement();
        }
        return items;
    }

    private ResultSet fetchData() throws SQLException {
        String getItems = new SQLScript().getItems();
        this.statement = this.connection.prepareStatement(getItems);
        return statement.executeQuery();
    }

    private List<Item> convertToList(ResultSet data) throws SQLException {
        List<Item> result = new ArrayList<>();
        while (data.next()) {
            Item item = this.getItem(data);
            result.add(item);
        }
        return result;
    }

    private Item getItem(ResultSet data) throws SQLException {
        Item item = new Item(data.getString(ITEM_NAME),
                data.getString(ITEM_DESC),
                data.getDate(ITEM_CREATE_DATE).getTime());
        int id = data.getInt(ITEM_ID);
        item.setId(String.valueOf(id));
        return item;
    }

    private void closeResultSet(ResultSet data) {
        try {
            if (data != null) {
                data.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     /**
     * Находит все заявки с заданным именем
     * @param key имя для поиска
     * @return список всех найденных заявок
     */
    public List<Item> findByName(String key) {
        List<Item> items = new ArrayList<>();
        ResultSet data = null;
        try {
            data = fetchDataByName(key);
            items = this.convertToList(data);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(data);
            this.closeStatement();
        }
        return items;
    }

    private ResultSet fetchDataByName(String name) throws SQLException {
        String findByName = new SQLScript().findByName();
        this.statement = this.connection.prepareStatement(findByName);
        this.statement.setString(1, name);
        return this.statement.executeQuery();
    }

    /**
     * Поиск заявки по идентификатору
     * @param id идентификатор
     * @return результат поиска
     */
    public Optional<Item> findById(String id) {
        Item result = null;
        ResultSet data = null;
        try {
            data = fetchDataById(id);
            result = convertToItem(data);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(data);
            this.closeStatement();
        }
        return Optional.ofNullable(result);
    }

    private ResultSet fetchDataById(String id) throws SQLException {
        String find = new SQLScript().findById();
        this.statement = this.connection.prepareStatement(find);
        this.statement.setInt(1, Integer.parseInt(id));
        return this.statement.executeQuery();
    }

    private Item convertToItem(ResultSet data) throws SQLException {
        Item result = null;
        if (data.next()) {
            result = this.getItem(data);
        }
        return result;
    }

    public void setConnection(Connection con) {
        this.connection = con;
    }

    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void close() throws Exception {
        if (this.connection != null) {
            this.connection.close();
        }
    }
}
