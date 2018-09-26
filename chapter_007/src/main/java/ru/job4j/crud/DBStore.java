package ru.job4j.crud;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Database store.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class DBStore implements Store {
    /** Database connection pool */
    private static final BasicDataSource SOURCE = new BasicDataSource();
    /** Singleton instance */
    private static DBStore INSTANCE = new DBStore();

    public DBStore() {
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setUrl("jdbc:postgresql://localhost/users");
        SOURCE.setUsername("postgres");
        SOURCE.setPassword("password");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
    }

    public static DBStore getInstance() {
        return INSTANCE;
    }

    @Override
    public User add(User user) {
        String addUser =
                "INSERT INTO users(login, name, email, create_date) VALUES (?, ?, ?, CURRENT_DATE);";
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(addUser, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, user.getLogin());
            st.setString(2, user.getName());
            st.setString(3, user.getEmail());
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) {
                rs.next();
                String id = String.valueOf(rs.getInt(1));
                user.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void update(String id, User newUser) {
        String updateUser = "UPDATE users SET name=?, email=? WHERE id=?";
        String name = newUser.getName();
        String email = newUser.getEmail();
        try (Connection con = SOURCE.getConnection();
             PreparedStatement ps = con.prepareStatement(updateUser)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setInt(3, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        try (Connection con = SOURCE.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM users WHERE id=?;")) {
            ps.setInt(1, Integer.parseInt(id));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new LinkedList<>();
        try (Connection con = SOURCE.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM users ORDER BY id;")) {
            users = this.generateUserList(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    private List<User> generateUserList(ResultSet rs) throws SQLException {
        List<User> result = new LinkedList<>();
        while (rs.next()) {
            String id = rs.getString("id");
            String login = rs.getString("login");
            String name = rs.getString("name");
            String email = rs.getString("email");
            LocalDate createDate = rs.getObject("create_date", LocalDate.class);
            result.add(new User(id, name, login, email, createDate));
        }
        return result;
    }

    @Override
    public Optional<User> findById(String id) {
        Optional<User> result = Optional.empty();
        try (Connection con = SOURCE.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id=?;")) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet entry = ps.executeQuery()) {
                if (entry.next()) {
                    result = this.getUserFromEntry(id, entry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private Optional<User> getUserFromEntry(String id, ResultSet entry) throws SQLException {
        String login = entry.getString("login");
        String name = entry.getString("name");
        String email = entry.getString("email");
        LocalDate createDate = entry.getObject("create_date", LocalDate.class);
        return  Optional.of(new User(id, name, login, email, createDate));
    }
}
