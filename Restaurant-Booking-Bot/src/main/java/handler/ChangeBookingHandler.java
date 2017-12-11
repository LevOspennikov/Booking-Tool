package handler;

import database.SqlManager;
import model.Booking;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import resources.Keyboard;
import resources.Message;

import java.util.*;
import java.util.stream.Collectors;

public class ChangeBookingHandler implements Handler {
    Map<Long, Booking> bookingMap = new HashMap<>();
    private SqlManager sqlManager = SqlManager.getInstance();

    @Override
    public boolean matchCommand(Update update) {
        String message = update.getMessage().getText();
        long id = update.getMessage().getChatId();
        if ("Изменить бронирование".equals(message)) {
            bookingMap.put(id, null);
            return true;
        }
        // TODO
        return false;
    }

    @Override
    public SendMessage handle(Update update) {
        long id = update.getMessage().getChatId();
        Booking booking = bookingMap.get(id);
        if (booking == null) {
            List<Booking> bookings = sqlManager.getUserBookings(id);
            List<String> buttons = bookings.stream().map(b ->
                    "Id: " + b.getId() + ", " + b.getTime() + ", " + b.getPersonsCount() + " чел.").collect(Collectors.toList());
            return Message.makeReplyMessage(update,
                    "Выберите бронирование, которое хотите изменить", Keyboard.getKeyboard(buttons));
        }
        return null;
    }
}
