package notifier;

import bot.RestaurantBookingBot;
import database.SqlManager;
import model.SubscriptionType;
import model.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriberNotifier {
    private List<Subscriber> subscribers;
    private Map<SubscriptionType, MessageSender> messageSenders = new HashMap<>();

    public SubscriberNotifier(RestaurantBookingBot bot) {
        messageSenders.put(SubscriptionType.TELEGRAM, new TelegramMessageSender(bot));
        messageSenders.put(SubscriptionType.MAIL, new MailMessageSender());
    }

    public void notifySubscribers(String message) {
        if (subscribers == null) {
            subscribers = SqlManager.getInstance().getSubscribers();
        }
        for (Subscriber subscriber : subscribers) {
            messageSenders.get(subscriber.getSubscriptionType()).sendMessage(subscriber, message);
        }
    }
}
