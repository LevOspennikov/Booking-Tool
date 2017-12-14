package dao;

import model.Booking;

import java.util.List;

public interface BookingDao {
    List<Booking> getAvailableBookings();
    int deleteBookingById(long id);
    Booking getBookingById(long id);
    void changeBookingTime(long id, String newTime);
    void changeBookingCount(long id, int newCount);
}
