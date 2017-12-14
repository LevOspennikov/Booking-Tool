package handler;

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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ChangeBookingHandler implements Handler {
    private Map<Long, Booking> bookingMap = new HashMap<>();
    private Set<Long> changedBookings = new HashSet<>();
    private Map<Long, String> parameters = new HashMap<>();
    private SqlManager sqlManager = SqlManager.getInstance();
    private boolean error = false;

    @Override
    public boolean matchCommand(Update update) {
        String message = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        if (Messages.CHANGE_BOOKING.equals(message)) {
            bookingMap.put(chatId, null);
            return true;
        } if (Validations.isBookingDescription(message)) {
            bookingMap.put(chatId, sqlManager.getBookingById(Parsers.parseBookingId(message)));
            return true;
        } if (Messages.NAME.equals(message) || Messages.PHONE.equals(message) ||
                Messages.TIME.equals(message) || Messages.COUNT.equals(message)) {
            parameters.put(chatId, message);
            return true;
        } else {
            Booking booking = bookingMap.get(chatId);
            if (booking == null) {
                return false;
            }
            if (Messages.NAME.equals(parameters.get(chatId)) && Validations.isName(message)) {
                sqlManager.changeUserName(booking.getUserId(), Parsers.parseName(message));
            } else if (Messages.PHONE.equals(parameters.get(chatId)) && Validations.isPhoneNumber(message)) {
                sqlManager.changeUserPhone(booking.getUserId(), Parsers.parsePhoneNumber(message));
            } else if (Messages.TIME.equals(parameters.get(chatId)) && Validations.isTime(message)) {
                String newTime = Parsers.parseTime(message);
                sqlManager.changeBookingTime(booking.getId(), newTime);
                booking.setTime(newTime);
            } else if (Messages.COUNT.equals(parameters.get(chatId)) && Validations.isPersonsCount(message)) {
                int newCount = Integer.parseInt(message);
                sqlManager.changeBookingCount(booking.getId(), newCount);
                booking.setPersonsCount(newCount);
            } else {
                error = true;
                return true;
            }
            changedBookings.add(chatId);
            return true;
        }
    }

    @Override
    public SendMessage handle(Update update) {
        if (error) {
            error = false;
            return Message.makeReplyMessage(update, Messages.INCORRECT_VALUE);
        }
        long chatId = update.getMessage().getChatId();
        Booking booking = bookingMap.get(chatId);
        if (booking == null) {
            List<Booking> bookings = sqlManager.getUserBookings(chatId);
            if (bookings.isEmpty()) {
                return Message.makeReplyMessage(update, Messages.NO_BOOKINGS_ERROR, Keyboard.DEFAULT_KEYBOARD);
            }
            List<String> buttons = bookings.stream().map(b ->
                    "Id: " + b.getId() + ", " + b.getTime() + ", " + b.getPersonsCount() + " чел.").collect(Collectors.toList());
            return Message.makeReplyMessage(update, Messages.SELECT_BOOKING_TO_CHANGE, Keyboard.getKeyboard(buttons));
        } else {
            if (!parameters.containsKey(chatId)) {
                return Message.makeReplyMessage(update, Messages.SELECT_PARAMETER_TO_CHANGE,
                        Keyboard.getKeyboard(Arrays.asList(Messages.NAME, Messages.PHONE, Messages.TIME, Messages.COUNT)));
            } else {
                if (!changedBookings.contains(chatId)) {
                    return Message.makeReplyMessage(update, Messages.ENTER_NEW_VAL);
                } else {
                    User user = sqlManager.getUserById(chatId);
                    StringBuilder builder = new StringBuilder();
                    builder.append("Ваше бронирование изменено. Информация по бронированию:\n")
                            .append("Имя: " + user.getName() + "\n")
                            .append("Телефон: " + user.getPhone() + "\n")
                            .append("Время: " + booking.getTime() + "\n")
                            .append("Количество человек: " + booking.getPersonsCount());
                    bookingMap.remove(chatId);
                    parameters.remove(chatId);
                    changedBookings.remove(chatId);
                    return Message.makeReplyMessage(update, builder.toString(), Keyboard.DEFAULT_KEYBOARD);
                }
            }
        }
    }
}
