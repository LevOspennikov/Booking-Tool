package handler;

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

    @Override
    public boolean matchCommand(Update update) {
        String message = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        if ("Изменить бронирование".equals(message)) {
            bookingMap.put(chatId, null);
            return true;
        } if (Validations.isBookingDescription(message)) {
            bookingMap.put(chatId, sqlManager.getBookingById(Parsers.parseBookingId(message)));
            return true;
        } if ("Имя".equals(message) || "Телефон".equals(message) ||
                "Время".equals(message) || "Количество человек".equals(message)) {
            parameters.put(chatId, message);
            return true;
        } else {
            Booking booking = bookingMap.get(chatId);
            if (booking == null) {
                return false;
            }
            if ("Имя".equals(parameters.get(chatId)) && Validations.isName(message)) {
                sqlManager.changeUserName(booking.getUserId(), Parsers.parseName(message));
            } else if ("Телефон".equals(parameters.get(chatId)) && Validations.isPhoneNumber(message)) {
                sqlManager.changeUserPhone(booking.getUserId(), Parsers.parsePhoneNumber(message));
            } else if ("Время".equals(parameters.get(chatId)) && Validations.isTime(message)) {
                String newTime = Parsers.parseTime(message);
                sqlManager.changeBookingTime(booking.getId(), newTime);
                booking.setTime(newTime);
            } else if ("Количество человек".equals(parameters.get(chatId)) && Validations.isPersonsCount(message)) {
                int newCount = Integer.parseInt(message);
                sqlManager.changeBookingCount(booking.getId(), newCount);
                booking.setPersonsCount(newCount);
            } else {
                return false;
            }
            changedBookings.add(chatId);
            return true;
        }
    }

    @Override
    public SendMessage handle(Update update) {
        long chatId = update.getMessage().getChatId();
        Booking booking = bookingMap.get(chatId);
        if (booking == null) {
            List<Booking> bookings = sqlManager.getUserBookings(chatId);
            if (bookings.isEmpty()) {
                return Message.makeReplyMessage(update, "У Вас нет активных бронирований",
                        Keyboard.getKeyboard(Arrays.asList("Забронировать", "Изменить бронирование")));
            }
            List<String> buttons = bookings.stream().map(b ->
                    "Id: " + b.getId() + ", " + b.getTime() + ", " + b.getPersonsCount() + " чел.").collect(Collectors.toList());
            return Message.makeReplyMessage(update,
                    "Выберите бронирование, которое хотите изменить", Keyboard.getKeyboard(buttons));
        } else {
            if (!parameters.containsKey(chatId)) {
                return Message.makeReplyMessage(update, "Выберите, какой параметр нужно изменить",
                        Keyboard.getKeyboard(Arrays.asList("Имя", "Телефон", "Время", "Количество человек")));
            } else {
                if (!changedBookings.contains(chatId)) {
                    return Message.makeReplyMessage(update, "Введите новое значение");
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
                    return Message.makeReplyMessage(update, builder.toString(),
                            Keyboard.getKeyboard(Arrays.asList("Забронировать", "Изменить бронирование")));
                }
            }
        }
    }
}
