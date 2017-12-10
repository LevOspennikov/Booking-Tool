package handler;

import resources.Keyboard;
import resources.Message;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Arrays;

public class StartHandler implements Handler {

    @Override
    public boolean matchCommand(Update update) {
        return update.getMessage().getText().equals("/start") || update.getMessage().getText().equals("/back");
    }

    @Override
    public SendMessage handle(Update update) {
        try {
            return Message.makeReplyMessage(update, "Наш классный ресторан, можете забронировать столик(вынести куда-нибудь константу)",
                    Keyboard.getKeyboard(Arrays.asList("Забронировать", "Информация")));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Message.makeReplyMessage(update, "Что-то пошло не так");
        }
    }
}
