package utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

public class ParsersTest {

    @Test
    public void parsePhoneNumberTests() {
        Assert.assertEquals("8(981)951-00-00", Parsers.parsePhoneNumber("89819510000"));
        Assert.assertEquals("8(981)951-00-00", Parsers.parsePhoneNumber("9819510000"));
        Assert.assertEquals("8(981)951-00-00", Parsers.parsePhoneNumber("981-951-00-00"));
    }

    @Test
    public void parseNameTests() {
        Assert.assertEquals("Anna", Parsers.parseName("anna"));
        Assert.assertEquals("Anna", Parsers.parseName("Anna"));
    }

    @Test
    public void parseTimeTests() {
        Assert.assertEquals("2017-12-14 15:30", Parsers.parseTime("14.12 15:30"));
        Assert.assertEquals("2017-12-15 15:30", Parsers.parseTime("15.12 15.30"));
        Assert.assertEquals("2017-12-13 15:30", Parsers.parseTime("13.12 15-30"));
        String date = Calendar.getInstance().get(Calendar.YEAR) + "-" +
                (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-" + Calendar.getInstance().get(Calendar.DATE);
        Assert.assertEquals(date + " 15:30", Parsers.parseTime("15-30"));
        Assert.assertEquals(date + " 15:30", Parsers.parseTime("15.30"));
        Assert.assertEquals(date + " 15:30", Parsers.parseTime("15:30"));
    }

    @Test
    public void parseBookingIdTests() {
        Assert.assertEquals((Long) 4L, Parsers.parseBookingId("Id: 4, 2017-12-18 18:30, 6 чел."));
    }
}
