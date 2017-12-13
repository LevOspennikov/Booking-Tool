package handler;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import resources.Keyboard;
import resources.Message;

import java.util.Arrays;

public class ErrorHandler implements Handler {

    @Override
    public boolean matchCommand(Update update) {
        return true;
    }

    @Override
    public SendMessage handle(Update update) {
        return Message.makeReplyMessage(update, "Не удалось распознать запрос. Пожалуйста, попробуйте снова",
                Keyboard.getKeyboard(Arrays.asList("Забронировать", "Изменить бронирование")));
    }
}
