package handler;

import model.SubscribeType;
import notifier.SubscriberNotifier;
import constants.Messages;
import database.SqlManager;
import model.Booking;
import model.User;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import resources.Keyboard;
import resources.Message;
import utils.Parsers;
import utils.Validations;

import java.util.HashMap;
import java.util.Map;

public class BookingHandler implements Handler {
    private Map<Long, User> usersMap = new HashMap<>();
    private Map<Long, Booking> bookingsMap = new HashMap<>();
    private SqlManager sqlManager = SqlManager.getInstance();
    private boolean error = false;
    private SubscriberNotifier subscriberNotifier;

    public BookingHandler() {
    }

    public BookingHandler(SubscriberNotifier info) {
        subscriberNotifier = info;
    }

    @Override
    public boolean matchCommand(Update update) {
        long id = update.getMessage().getChatId();
        String message = update.getMessage().getText();
        if (Messages.BOOK.equals(message)) {
            User user = sqlManager.getUserById(id);
            if (user == null) {
                user = new User();
                user.setId(id);
                usersMap.put(id, user);
            }
            Booking booking = new Booking();
            booking.setUserId(id);
            bookingsMap.put(id, booking);
            return true;
        } else {
            User user = usersMap.get(id);
            Booking booking = bookingsMap.get(id);
            if (booking == null) {
                return false;
            }
            if (user != null && Validations.isPhoneNumber(message)) {
               user.setPhone(Parsers.parsePhoneNumber(message));
            } else if (user != null && Validations.isName(message)) {
                user.setName(Parsers.parseName(message));
            } else if (Validations.isTime(message)) {
                booking.setTime(Parsers.parseTime(message));
            } else if (Validations.isPersonsCount(message)) {
                booking.setPersonsCount(Integer.parseInt(message));
            } else {
                error = true;
            }
            return true;
        }
    }

    @Override
    public SendMessage handle(Update update) {
        try {
            if (error) {
                error = false;
                return Message.makeReplyMessage(update, Messages.INCORRECT_VALUE);
            }
            long id = update.getMessage().getChatId();
            User user = usersMap.get(id);
            boolean userExists = user == null;
            if (userExists) {
                user = sqlManager.getUserById(id);
            }
            if (user.getName() == null) {
                return Message.makeReplyMessage(update, Messages.ENTER_NAME);
            } else if (user.getPhone() == null) {
                return Message.makeReplyMessage(update, Messages.ENTER_PHONE);
            }
            Booking booking = bookingsMap.get(id);
            if (booking != null) {
                if (booking.getTime() == null) {
                    return Message.makeReplyMessage(update, Messages.ENTER_TIME);
                } else if (booking.getPersonsCount() == 0) {
                    return Message.makeReplyMessage(update, Messages.ENTER_COUNT);
                } else {
                    if (!userExists) {
                        sqlManager.addUser(user);
                    }
                    sqlManager.addBooking(booking);
                    StringBuilder builder = new StringBuilder();
                    builder.append("Ваше бронирование на имя " + user.getName() + " добавлено. Информация по бронированию:\n")
                           .append("Телефон: " + user.getPhone() + "\n")
                           .append("Время: " + booking.getTime() + "\n")
                           .append("Количество человек: " + booking.getPersonsCount());
                    usersMap.remove(id);
                    bookingsMap.remove(id);
                    subscriberNotifier.addSubscriber(Long.toString(user.getId()), SubscribeType.TELEGRAM);
                    subscriberNotifier.notifySubscribers(builder.toString());
                    return Message.makeReplyMessage(update, builder.toString(), Keyboard.getKeyboard(getDefaultButtons()));
                }
            }
            return Message.makeReplyMessage(update, Messages.PARSER_ERROR, Keyboard.getKeyboard(getDefaultButtons()));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Message.makeReplyMessage(update, Messages.INTERNAL_ERROR, Keyboard.getKeyboard(getDefaultButtons()));
        }
    }
}