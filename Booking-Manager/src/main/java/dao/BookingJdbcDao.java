package dao;

import model.Booking;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.sql.DataSource;
import java.util.List;

public class BookingJdbcDao extends JdbcDaoSupport implements BookingDao {

    public BookingJdbcDao(DataSource dataSource) {
        super();
        setDataSource(dataSource);
        initDatabase();
    }

    @Override
    public List<Booking> getBookingsInNextDay() {
        return null;
    }

    private int initDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                "id             INTEGER   PRIMARY KEY," +
                "name           TEXT      NOT NULL," +
                "phone          TEXT      NOT NULL" +
                ");" +
                "CREATE TABLE IF NOT EXISTS Bookings (" +
                "id             INTEGER   PRIMARY KEY AUTOINCREMENT," +
                "userId         INTEGER   NOT NULL," +
                "personsCount   TEXT      NOT NULL," +
                "time           DATETIME  NOT NULL" +
                ");";
        return getJdbcTemplate().update(sql);
    }
}
