package ru.job4j.cinema.persistance;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.cinema.service.Ticket;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * SQL processor for persistence layer.
 * @author Vadim Bolokhov
 */
public enum CinemaDBProcessor implements Persistence {
    INSTANCE;

    /** Database connection pool */
    private final BasicDataSource source = new BasicDataSource();
    /** Logger */
    private static final Logger LOG = LogManager.getLogger(CinemaDBProcessor.class.getName());

    CinemaDBProcessor() {
        this.setDBProperties();
    }

    private void setDBProperties() {
        source.setDriverClassName("org.postgresql.Driver");
        source.setUrl("jdbc:postgresql://localhost/cinema");
        source.setUsername("postgres");
        source.setPassword("password");
        source.setMinIdle(5);
        source.setMaxIdle(10);
        source.setMaxOpenPreparedStatements(100);
    }

    public void initDB() {
        String createTables = "CREATE TABLE IF NOT EXISTS accounts ("
                + "id SERIAL PRIMARY KEY,"
                + " username VARCHAR(50) NOT NULL,"
                + " phone VARCHAR(10));\n"
                + "CREATE TABLE IF NOT EXISTS halls ("
                + "id serial PRIMARY KEY,"
                + " seat INT UNIQUE NOT NULL,"
                + " reserved BOOLEAN DEFAULT FALSE,"
                + " price REAL DEFAULT 500,"
                + " account_id INTEGER REFERENCES accounts(id));";
        this.executeSQLStatement(createTables);
    }

    private void executeSQLStatement(String statement) {
        try (Connection con = source.getConnection();
             Statement st = con.createStatement()) {
            st.execute(statement);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public void addSeats(int rows, int seats) {
        StringJoiner sj =
                new StringJoiner(", ", "INSERT INTO halls (seat) VALUES ", " ON CONFLICT (seat) DO NOTHING;");
        for (int row = 1; row <= rows; row++) {
            for (int seat = 1; seat <= seats; seat++) {
                int seatId = 100 * row + seat;
                sj.add(String.format("(%d)", seatId));
            }
        }
        String addSeats = sj.toString();
        this.executeSQLStatement(addSeats);
    }

    @Override
    public List<Ticket> getAllSeats() {
        List<Ticket> result = new LinkedList<>();
        String fetchSeatsFromDB = "SELECT seat, reserved FROM halls ORDER BY seat";
        try (Connection con = source.getConnection();
             Statement st = con.createStatement();
             ResultSet data = st.executeQuery(fetchSeatsFromDB)) {
            while (data.next()) {
                int seatId = data.getInt("seat");
                boolean reserved = data.getBoolean("reserved");
                result.add(new Ticket(seatId, reserved));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean reserveSeat(Ticket ticket) {
        boolean result = false;
        String updateHalls = "UPDATE halls SET reserved = TRUE, account_id = ? WHERE seat = ?";
        try (Connection con = this.source.getConnection();
             PreparedStatement updateStatement = con.prepareStatement(updateHalls)) {
            int accountId = this.insertAccount(con, ticket);
            updateStatement.setInt(1, accountId);
            updateStatement.setInt(2, ticket.getId());
            updateStatement.executeUpdate();
            con.commit();
            result = true;
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return result;
    }

    private int insertAccount(Connection con, Ticket ticket) throws SQLException {
        String insertAccount = "INSERT INTO accounts (username, phone) VALUES (?, ?);";
        int accountId = -1;
        try (PreparedStatement ps = con.prepareStatement(insertAccount, Statement.RETURN_GENERATED_KEYS)) {
            con.setAutoCommit(false);
            ps.setString(1, ticket.getUsername());
            ps.setString(2, ticket.getPhone());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                accountId = generatedKeys.getInt("id");
            }
        } catch (SQLException e) {
            con.rollback();
            LOG.info("DB error occurred, rolling back data.");
            throw new SQLException(e.getMessage(), e);
        }
        return accountId;
    }

    @Override
    public Optional<Ticket> getSeatInfo(int seat) {
        Ticket ticket = new Ticket(seat);
        String getInfo = "SELECT h.seat, h.reserved, h.price, a.username, a.phone"
                + " FROM halls AS h LEFT OUTER JOIN accounts AS a ON h.account_id = a.id"
                + " WHERE h.seat = ?;";
        try (Connection con = this.source.getConnection();
             PreparedStatement ps = con.prepareStatement(getInfo)) {
            ps.setInt(1, seat);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ticket.setReserved(rs.getBoolean("reserved"));
                    ticket.setPrice(rs.getDouble("price"));
                    ticket.setUsername(rs.getString("username"));
                    ticket.setPhone(rs.getString("phone"));
                }
            }
        } catch (SQLException e) {
            LOG.info(e.getMessage());
        }
        return Optional.of(ticket);
    }
}
