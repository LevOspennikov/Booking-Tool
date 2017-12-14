package controller;

import dao.BookingDao;
import model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookingsController {
    @Autowired
    BookingDao bookingDao;

    @RequestMapping(value = "/get-bookings", method = RequestMethod.GET)
    public String getBookings(ModelMap map) {
        map.addAttribute("bookings", bookingDao.getAvailableBookings());
        map.addAttribute("booking", new Booking());
        return "bookings";
    }

    @RequestMapping(value = "/remove-booking", method = RequestMethod.GET)
    public String deleteBooking(@ModelAttribute("booking") Booking booking) {
        bookingDao.deleteBookingById(booking.getId());
        return "redirect:/get-bookings";
    }

    @RequestMapping(value = "/edit-booking", method = RequestMethod.GET)
    public String editBooking(@ModelAttribute("booking") Booking booking, ModelMap map) {
        map.addAttribute("booking", bookingDao.getBookingById(booking.getId()));
        return "edit";
    }

    @RequestMapping(value = "/change-booking", method = RequestMethod.POST)
    public String changeBooking(@ModelAttribute("booking") Booking booking) {
        if (booking.getDate() != null && !booking.getDate().isEmpty()) {
            bookingDao.changeBookingTime(booking.getId(), booking.getDate());
        }
        if (booking.getPersonsCount() != 0) {
            bookingDao.changeBookingCount(booking.getId(), booking.getPersonsCount());
        }
        return "redirect:/get-bookings";
    }
}
