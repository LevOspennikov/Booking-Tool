package controller;

import dao.BookingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class BookingsController {
    @Autowired
    BookingDao bookingDao;

    @RequestMapping(value = "/get-bookings", method = RequestMethod.GET)
    public String getBookings(ModelMap map) {
        map.addAttribute("bookings", bookingDao.getAvailableBookings());
        return "index";
    }
}
