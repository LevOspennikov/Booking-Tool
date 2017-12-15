package notifier;


import bot.RestaurantBookingBot;
import model.Subscriber;
import model.SubscriptionType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.internal.util.reflection.Whitebox;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MailMessageSenderTest {
    @Mock
    private RestaurantBookingBot bot;

    @Spy
    private MailMessageSender mailMessageSender = new MailMessageSender();


    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        doNothing().when(mailMessageSender).send(anyObject());
    }

    @Test
    public void TelegramSenderTest() throws TelegramApiException {
        mailMessageSender.sendMessage(new Subscriber("alsdad@gmail.ru", SubscriptionType.MAIL.toString()), "test");
    }
}
