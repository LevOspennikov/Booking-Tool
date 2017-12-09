package handler;

import resources.Keyboard;
import resources.Message;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Arrays;

public class BookingHandler implements Handler {

    @Override
    public boolean matchCommand(Update update) {
        return update.getMessage().getText().equals("Забронировать");
    }

    @Override
    public SendMessage handle(Update update) {
        try {
            return Message.makeReplyMessage(update, "Введите имя, телефон, время и колличество персон, на которых будет забронирован столик");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Message.makeReplyMessage(update, "Что-то пошло не так");
        }
    }
}