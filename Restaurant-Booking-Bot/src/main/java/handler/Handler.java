package handler;


import constants.Messages;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Arrays;
import java.util.List;

public interface Handler {

    boolean matchCommand(Update update);

    SendMessage handle(Update update);
}

