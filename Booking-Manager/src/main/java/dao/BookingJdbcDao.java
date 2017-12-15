package dao;

import model.Booking;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookingJdbcDao extends JdbcDaoSupport implements BookingDao {

    public BookingJdbcDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
        initDatabase();
    }

    @Override
    public List<Booking> getAvailableBookings() {
        String sql =
                "SELECT b.id AS bookingId, name AS userName, phone AS userPhone, personsCount, time\n" +
                "FROM Bookings b, Users u WHERE\n" +
                "b.userId = u.id AND\n" +
                "time >= DATETIME('now')";
        List<Booking> bookings = new ArrayList<>();
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
        for (Map row : rows) {
            Booking booking = new Booking((Integer) row.get("bookingId"),
                                          (String) row.get("userName"),
                                          (String) row.get("userPhone"),
                                          (String) row.get("time"),
                                          (Integer) row.get("personsCount") );
            bookings.add(booking);
        }
        return bookings;
    }

    @Override
    public int deleteBookingById(long id) {
        String sql = "DELETE FROM Bookings WHERE id = ?";
        return getJdbcTemplate().update(sql, id);
    }

    @Override
    public Booking getBookingById(long id) {
        String sql =
            "SELECT b.id AS bookingId, name AS userName, phone AS userPhone, personsCount, time\n" +
            "FROM Bookings b, Users u WHERE\n" +
            "b.userId = u.id AND\n" +
            "b.id = ?";
        return getJdbcTemplate().queryForObject(
                sql,
                new Object[] { id },
                (rs, rowNum) -> new Booking(rs.getLong("bookingId"),
                        rs.getString("userName"),
                        rs.getString("userPhone"),
                        rs.getString("time"),
                        rs.getInt("personsCount")));
    }

    @Override
    public void changeBookingTime(long id, String newTime) {
        String sql = "UPDATE Bookings SET time = ? WHERE id = ?";
        getJdbcTemplate().update(sql, newTime, id);
    }

    @Override
    public void changeBookingCount(long id, int newCount) {
        String sql = "UPDATE Bookings SET personsCount = ? WHERE id = ?";
        getJdbcTemplate().update(sql, newCount, id);
    }

    private int initDatabase() {
        String sql =
                "CREATE TABLE IF NOT EXISTS Users (" +
                    "id             INTEGER   PRIMARY KEY," +
                    "name           TEXT      NOT NULL," +
                    "phone          TEXT      NOT NULL" +
                ");\n" +
                "CREATE TABLE IF NOT EXISTS Bookings (" +
                    "id             INTEGER   PRIMARY KEY AUTOINCREMENT," +
                    "userId         INTEGER   NOT NULL," +
                    "personsCount   INTEGER   NOT NULL," +
                    "time           DATETIME  NOT NULL," +
                    "FOREIGN KEY (userId) REFERENCES Users (id)" +
                ");\n" +
                "CREATE TABLE IF NOT EXISTS Subscribers (" +
                    "id      INTEGER   PRIMARY KEY AUTOINCREMENT," +
                    "type    INTEGER   NOT NULL," +
                    "value   INTEGER   NOT NULL," +
                    "FOREIGN KEY (type) REFERENCES SubscriptionType (id)" +
                ");\n" +
                "CREATE TABLE IF NOT EXISTS SubscriptionType (" +
                    "id      INTEGER   PRIMARY KEY AUTOINCREMENT," +
                    "value   INTEGER   NOT NULL" +
                ");";
//                "INSERT INTO SubscriptionType(value) VALUES (\"TELEGRAM\");\n" +
//                "INSERT INTO SubscriptionType(value) VALUES (\"MAIL\");" +
//                "INSERT INTO Subscribers(type, value) VALUES (1, \"162350627\");" +
//                "INSERT INTO Subscribers(type, value) VALUES (2, \"ospennikovlev@gmail.com\");";
        return getJdbcTemplate().update(sql);
    }
}
