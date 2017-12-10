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
                "time >= DATETIME('now');";
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

    private int initDatabase() {
        String sql =
                "CREATE TABLE IF NOT EXISTS Users (" +
                    "id             INTEGER   PRIMARY KEY," +
                    "name           TEXT      NOT NULL," +
                    "phone          TEXT      NOT NULL" +
                ");" +
                "CREATE TABLE IF NOT EXISTS Bookings (" +
                    "id             INTEGER   PRIMARY KEY AUTOINCREMENT," +
                    "userId         INTEGER   NOT NULL," +
                    "personsCount   INTEGER   NOT NULL," +
                    "time           DATETIME  NOT NULL," +
                    "FOREIGN KEY (userId) REFERENCES Users (id)" +
                ");";
        return getJdbcTemplate().update(sql);
    }
}
