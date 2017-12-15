package notifier;

import bot.RestaurantBookingBot;
import model.SubscribeType;
import model.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubscriberNotifier {

    private List<Subscriber> subscribers = new ArrayList<>();
    private Map<String, MessageSender> messageSenders = new HashMap<>();

    public SubscriberNotifier(RestaurantBookingBot bot) {
        this.messageSenders.put("TELEGRAM", new TelegramMessageSender(bot));
        this.messageSenders.put("MAIL", new MailMessageSender());
        this.subscribers.add(new Subscriber("ospennikovlev@gmail.com", "MAIL"));
    }

    public void notifySubscribers(String message) {
        for (Subscriber subscriber : subscribers) {
            messageSenders.get(subscriber.getSubscribeType()).sendMessage(subscriber, message);
        }
    }

    public void addSubscriber(String contact, SubscribeType type) {
        subscribers.add(new Subscriber(contact, type.toString()));
    }
}
