package resources;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class Message {
    public static SendMessage makeReplyMessage(Update update, String text, ReplyKeyboardMarkup keyboard) {
        return makeReplyMessage(update, text).setReplyMarkup(keyboard);
    }

    public static SendMessage makeReplyMessage(Update update, String text) {
        return new SendMessage().setChatId(update.getMessage().getChatId()).setText(text);
    }
}
