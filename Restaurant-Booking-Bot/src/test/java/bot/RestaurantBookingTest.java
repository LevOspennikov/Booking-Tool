package bot;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import utils.PropertyReader;


public class RestaurantBookingTest {

    @Test
    public void BotTest() {
        new RestaurantBookingBot(PropertyReader.readProperties().getProperty("apiKey"));
    }
}
