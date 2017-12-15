
import bot.RestaurantBookingBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import utils.PropertyReader;

import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Properties properties = PropertyReader.readProperties();
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new RestaurantBookingBot(properties.getProperty("apiKey")));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
