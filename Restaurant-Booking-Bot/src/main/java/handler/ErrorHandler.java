package handler;

import constants.Messages;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import resources.Keyboard;
import resources.Message;

public class ErrorHandler implements Handler {

    @Override
    public boolean matchCommand(Update update) {
        return true;
    }

    @Override
    public SendMessage handle(Update update) {
        return Message.makeReplyMessage(update, Messages.PARSER_ERROR, Keyboard.getKeyboard(getDefaultButtons()));
    }
}
