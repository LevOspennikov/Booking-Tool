package dao;

import model.Booking;

import java.util.List;

public interface BookingDao {
    List<Booking> getAvailableBookings();
}
