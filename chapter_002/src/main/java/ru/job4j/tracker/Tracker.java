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
        String insert = new SQLScript().insert();
        try (PreparedStatement ps = this.connection.prepareStatement(
                insert,
                Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getName());
            ps.setString(2, item.getDesc());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                int id = rs.getInt(1);
                item.setId(String.valueOf(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    /**
     * Заменяет заявку с заданным идентификатором
     * @param id идентификатор
     * @param replace замена
     */
    public void replace(String id, Item replace) {
        String replaceItem = new SQLScript().replace();
        try (PreparedStatement ps = this.connection.prepareStatement(replaceItem)) {
            ps.setString(1, replace.getName());
            ps.setString(2, replace.getDesc());
            ps.setInt(3, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Удаляет из списка заявку с заданным Id
     * @param id идентификатор заявки
     */
    public void delete(String id) {
        String delete = new SQLScript().delete();
        try (PreparedStatement ps = this.connection.prepareStatement(delete)) {
            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Возвращает список всех заявок
     * @return все заявки
     */
    public List<Item> getAll() {
        List<Item> items = new LinkedList<>();
        String getItems = new SQLScript().getItems();
        try (PreparedStatement ps = this.connection.prepareStatement(getItems);
                ResultSet data = ps.executeQuery()) {
            items = this.convertToList(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
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

     /**
     * Находит все заявки с заданным именем
     * @param key имя для поиска
     * @return список всех найденных заявок
     */
    public List<Item> findByName(String key) {
        List<Item> items = new ArrayList<>();
        String findByName = new SQLScript().findByName();
        try (PreparedStatement ps = this.connection.prepareStatement(findByName)) {
            ps.setString(1, key);
            try (ResultSet rs = ps.executeQuery()) {
                items = this.convertToList(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

     /**
     * Поиск заявки по идентификатору
     * @param id идентификатор
     * @return результат поиска
     */
    public Optional<Item> findById(String id) {
        Item result = null;
        String find = new SQLScript().findById();
        try (PreparedStatement ps = this.connection.prepareStatement(find)) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = ps.executeQuery()) {
                result = convertToItem(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(result);
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
