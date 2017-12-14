package utils;

import org.junit.Assert;
import org.junit.Test;

public class ValidationsTest {

    @Test
    public void isPhoneNumberTests() {
        Assert.assertTrue(Validations.isPhoneNumber("89819510000"));
        Assert.assertTrue(Validations.isPhoneNumber("9819510000"));
        Assert.assertTrue(Validations.isPhoneNumber("981-951-00-00"));
        Assert.assertTrue(Validations.isPhoneNumber("(981)951-00-00"));
        Assert.assertTrue(Validations.isPhoneNumber("(981)9510000"));
        Assert.assertFalse(Validations.isPhoneNumber("111111"));
    }

    @Test
    public void isNameTests() {
        Assert.assertTrue(Validations.isName("anna"));
        Assert.assertTrue(Validations.isName("Anna"));
        Assert.assertFalse(Validations.isName("Anna123"));
    }

    @Test
    public void isTimeTests() {
        Assert.assertTrue(Validations.isTime("15.00"));
        Assert.assertTrue(Validations.isTime("18:35"));
        Assert.assertTrue(Validations.isTime("00-47"));
        Assert.assertTrue(Validations.isTime("18.12 00.23"));
        Assert.assertTrue(Validations.isTime("19-12 03.56"));
        Assert.assertFalse(Validations.isTime("115.00"));
    }

    @Test
    public void isPersonsCountTests() {
        Assert.assertTrue(Validations.isPersonsCount("8"));
        Assert.assertFalse(Validations.isPersonsCount("0"));
        Assert.assertFalse(Validations.isPersonsCount("1.5"));

    }

    @Test
    public void isBookingDescriptionTests() {
        Assert.assertTrue(Validations.isBookingDescription("Id: 4, 2017-12-18 18:30, 6 чел."));
        Assert.assertTrue(Validations.isBookingDescription("Id: 18, 2017-12-29 15:40, 18 чел."));
    }
}
