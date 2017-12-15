package handler;

import constants.Messages;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import resources.Keyboard;
import resources.Message;

public class StartHandler implements Handler {

    @Override
    public boolean matchCommand(Update update) {
        return update.getMessage().getText().equals("/start");
    }

    @Override
    public SendMessage handle(Update update) {
        try {
            return Message.makeReplyMessage(update, Messages.GREETING, Keyboard.DEFAULT_KEYBOARD);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Message.makeReplyMessage(update, Messages.INTERNAL_ERROR);
        }
    }
}
