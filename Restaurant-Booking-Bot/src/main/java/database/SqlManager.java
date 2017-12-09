package database;

import model.Booking;
import model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        String sql = "INSERT INTO Bookings(id, userId, personsCount, time) VALUES (%d, \"%s\", \"%s\", %t)";
        executeUpdate(String.format(sql, booking.getId(), booking.getUserId(),
                booking.getPersonsCount(), booking.getTime()));
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
