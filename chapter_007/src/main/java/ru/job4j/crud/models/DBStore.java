package ru.job4j.crud.models;

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
    private static final DBStore INSTANCE = new DBStore();

    public DBStore() {
        SOURCE.setDriverClassName("org.postgresql.Driver");
        SOURCE.setUrl("jdbc:postgresql://localhost/users");
        SOURCE.setUsername("postgres");
        SOURCE.setPassword("password");
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
        this.createTables();
    }

    public static DBStore getInstance() {
        return INSTANCE;
    }

    private void createTables() {
        try (Connection con = SOURCE.getConnection();
        Statement st = con.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS roles("
                    + "id serial PRIMARY KEY,"
                    + " role VARCHAR(10) UNIQUE NOT NULL);");
            st.execute("CREATE TABLE IF NOT EXISTS users("
                    + "id serial PRIMARY KEY,"
                    + " login VARCHAR(50) UNIQUE NOT NULL,"
                    + " password VARCHAR(50) NOT NULL,"
                    + " name VARCHAR(50) NOT NULL,"
                    + " email VARCHAR(50),"
                    + " create_date DATE NOT NULL,"
                    + " role_id INTEGER REFERENCES roles(id));");
            String insertRoles = String.format("INSERT INTO roles (role) VALUES ('%s'), ('%s')"
                    + " ON CONFLICT (role) DO NOTHING;", Role.ADMIN, Role.USER);
            st.execute(insertRoles);
            st.execute("INSERT INTO users (login, password, name, create_date, role_id)"
                    + " VALUES ('root', 'password', 'root', CURRENT_DATE, 1)"
                    + " ON CONFLICT (login) DO NOTHING;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User add(User user) {
        String addUser =
                "INSERT INTO users(login, password, name, email, create_date, role_id)"
                        + "VALUES (?, ?, ?, ?, CURRENT_DATE, ?);";
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(addUser, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());
            st.setString(3, user.getName());
            st.setString(4, user.getEmail());
            int roleId = user.getRole().ordinal() + 1;
            st.setInt(5, roleId);
            st.executeUpdate();
            try (ResultSet rs = st.getGeneratedKeys()) {
                rs.next();
                String id = String.valueOf(rs.getInt(1));
                user = new User.Builder().id(id).login(user.getLogin()).password(user.getPassword())
                        .name(user.getName()).email(user.getEmail()).role(user.getRole()).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void update(String id, User newUser) {
        String updateUser = "UPDATE users SET name=?, email=?, password=?, role_id=? WHERE id=?";
        String name = newUser.getName();
        String email = newUser.getEmail();
        String password = newUser.getPassword();
        try (Connection con = SOURCE.getConnection();
             PreparedStatement ps = con.prepareStatement(updateUser)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            int roleId = newUser.getRole().ordinal() + 1;
            ps.setInt(4, roleId);
            ps.setInt(5, Integer.parseInt(id));
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
        String select = "SELECT u.*, r.role FROM users AS u"
                + " INNER JOIN roles AS r ON u.role_id = r.id ORDER BY u.id;";
        try (Connection con = SOURCE.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(select)) {
            users = this.generateUserList(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    private List<User> generateUserList(ResultSet rs) throws SQLException {
        List<User> result = new LinkedList<>();
        while (rs.next()) {
            result.add(this.generateUser(rs));
        }
        return result;
    }

    private User generateUser(ResultSet entry) throws SQLException {
        String id = entry.getString("id");
        String login = entry.getString("login");
        String password = entry.getString("password");
        String name = entry.getString("name");
        String email = entry.getString("email");
        Role role = Role.valueOf(entry.getString("role"));
        LocalDate createDate = entry.getObject("create_date", LocalDate.class);
        return new User.Builder().id(id).login(login).password(password).name(name)
                .email(email).createDate(createDate).role(role)
                .build();
    }

    @Override
    public Optional<User> findById(String id) {
        Optional<User> result = Optional.empty();
        try (Connection con = SOURCE.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT u.*, r.role FROM users AS u"
                     + " INNER JOIN roles AS r ON u.role_id = r.id WHERE u.id=?;")) {
            ps.setInt(1, Integer.parseInt(id));
            try (ResultSet entry = ps.executeQuery()) {
                if (entry.next()) {
                    result = Optional.of(this.generateUser(entry));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
