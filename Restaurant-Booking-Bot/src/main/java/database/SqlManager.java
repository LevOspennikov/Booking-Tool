package database;

import model.Booking;
import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class SqlManager {
    private static SqlManager instance;

    private SqlManager() {}

    public static SqlManager getInstance() {
        if (instance == null) {
            instance = new SqlManager();
        }
        return instance;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO Users(id, name, phone) VALUES (%d, \"%s\", \"%s\")";
        executeUpdate(String.format(sql, user.getId(), user.getName(), user.getPhone()));
    }

    public void addBooking(Booking booking) {
        String sql = "INSERT INTO Bookings(userId, personsCount, time) VALUES (%d, \"%s\", \"%s\")";
        executeUpdate(String.format(sql, booking.getUserId(), booking.getPersonsCount(), booking.getTime()));
    }

    public User getUserById(Long id) {
        String sql = "SELECT * FROM Users WHERE id = %d";
        ResultSet rs = executeQuery(String.format(sql, id));
        try {
            if (!rs.next()) {
                return null;
            }
            return new User(rs.getLong("id"), rs.getString("name"), rs.getString("phone"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Booking> getUserBookings(Long userId) {
        String sql = "SELECT * FROM Bookings WHERE userId = %d AND time >= DATETIME('now')";
        ResultSet rs = executeQuery(String.format(sql, userId));
        List<Booking> bookings = new ArrayList<>();
        try {
            while (rs.next()) {
                    bookings.add(new Booking(rs.getInt("id"), rs.getLong("userId"),
                            rs.getString("time"), rs.getInt("personsCount")));
            }
            return bookings;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeUpdate(String query) {
        execute(query, (statement, sqlQuery) -> {
            try {
                return statement.executeUpdate(sqlQuery);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private ResultSet executeQuery(String query) {
        return execute(query, (statement, sqlQuery) -> {
            try {
                return statement.executeQuery(sqlQuery);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> T execute(String query, BiFunction<Statement, String, T> function) {
        String address = "jdbc:sqlite:../bookings.db";
        try {
            Connection connection = DriverManager.getConnection(address);
            Statement stmt = connection.createStatement();
            return function.apply(stmt, query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
