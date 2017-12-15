package bot;


import handler.*;
import notifier.SubscriberNotifier;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class RestaurantBookingBot extends TelegramLongPollingBot {
    private final String botUsername = "RestaurantBookingBot";
    private String botToken;

    private List<Handler> handlers = new ArrayList<>();


    public RestaurantBookingBot(String botToken) {
        this.botToken = botToken;
        SubscriberNotifier subscriberNotifier = new SubscriberNotifier(this);
        handlers.add(new StartHandler());
        handlers.add(new ChangeBookingHandler(subscriberNotifier));
        handlers.add(new BookingHandler(subscriberNotifier));
        handlers.add(new ErrorHandler());
    }

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            for (Handler handler : handlers) {
                if (handler.matchCommand(update)) {
                    try {
                        execute(handler.handle(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    public void setHandlers(List<Handler> handlers) {
        this.handlers = handlers;
    }

    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}

