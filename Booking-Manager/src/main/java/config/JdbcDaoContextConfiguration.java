package config;

import dao.BookingDao;
import dao.BookingJdbcDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class JdbcDaoContextConfiguration {

    @Bean
    public BookingDao bookingJdbcDao(DataSource dataSource) {
        return new BookingJdbcDao(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:../bookings.db");
        Properties properties = new Properties();
        properties.setProperty("foreign_keys", "true");
        dataSource.setConnectionProperties(properties);
        dataSource.setUsername("");
        dataSource.setPassword("");
        dataSource.setCatalog("../");
        return dataSource;
    }
}
