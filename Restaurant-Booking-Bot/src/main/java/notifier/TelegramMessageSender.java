package notifier;

import bot.RestaurantBookingBot;
import model.Subscriber;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import resources.Message;

class TelegramMessageSender implements MessageSender {

    private RestaurantBookingBot bot;

    TelegramMessageSender(RestaurantBookingBot bot) {
        this.bot = bot;
    }

    public void sendMessage(Subscriber subscriber, String message) {
        try {
            bot.sendMessage(Message.makeReplyMessageFromId(new Long(subscriber.getSubscriberContact()), message));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
