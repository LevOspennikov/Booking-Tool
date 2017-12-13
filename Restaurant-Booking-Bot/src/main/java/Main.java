
import bot.RestaurantBookingBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    private static String PROPERTIES_FILE = ".properties";

    private static Properties readProperties() {
        Properties prop = new Properties();
        InputStream input;

        try {
            input = new FileInputStream(PROPERTIES_FILE);
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }

    public static void main(String[] args) {
        Properties properties = readProperties();
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new RestaurantBookingBot(properties.getProperty("apiKey")));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
