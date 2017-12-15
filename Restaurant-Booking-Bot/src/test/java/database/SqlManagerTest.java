package database;

import model.Booking;
import model.Subscriber;
import model.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class SqlManagerTest {
    private SqlManager sqlManager = SqlManager.getInstance();

    @Test
    public void correctnessTest() {
        List<User> users = sqlManager.getAllUsers();
        for (User user : users) {
            User userById = sqlManager.getUserById(user.getId());
            Assert.assertNotNull(userById);
            Assert.assertEquals(user.getId(), userById.getId());
            Assert.assertEquals(user.getName(), userById.getName());
            Assert.assertEquals(user.getPhone(), userById.getPhone());
        }
        for (User user : users) {
            List<Booking> bookings = sqlManager.getUserBookings(user.getId());
            for (Booking booking : bookings) {
                Booking bookingById = sqlManager.getBookingById(booking.getId());
                Assert.assertNotNull(bookingById);
                Assert.assertEquals(booking.getId(), bookingById.getId());
                Assert.assertEquals(booking.getUserId(), bookingById.getUserId());
                Assert.assertEquals(user.getId(), bookingById.getUserId());
                Assert.assertEquals(booking.getTime(), bookingById.getTime());
                Assert.assertEquals(booking.getPersonsCount(), bookingById.getPersonsCount());
            }
        }
    }

    @Test
    public void subscribersTest() {
        List<Subscriber> subscribers = sqlManager.getSubscribers();
        Assert.assertNotNull(subscribers);
        for (Subscriber subscriber : subscribers) {
            Assert.assertNotNull(subscriber.getSubscriberContact());
            Assert.assertFalse(subscriber.getSubscriberContact().isEmpty());
            Assert.assertNotNull(subscriber.getSubscriptionType());
        }
    }
}
