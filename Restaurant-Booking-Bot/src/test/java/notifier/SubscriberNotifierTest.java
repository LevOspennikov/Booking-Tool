package notifier;


import bot.RestaurantBookingBot;
import model.Subscriber;
import model.SubscriptionType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SubscriberNotifierTest {
    @Mock
    private RestaurantBookingBot bot;
    @Mock
    private Map<SubscriptionType, MessageSender> messageSenders;
    @Mock
    private TelegramMessageSender telegramMessageSender = new TelegramMessageSender(bot);
    @Mock
    private MailMessageSender mailMessageSender = new MailMessageSender();

    private SubscriberNotifier subscriberNotifier = new SubscriberNotifier(bot);

    @Before
    public void init() throws TelegramApiException {
        MockitoAnnotations.initMocks(this);
        Whitebox.setInternalState(subscriberNotifier, "messageSenders", messageSenders);
        when(messageSenders.get(SubscriptionType.TELEGRAM)).thenReturn(telegramMessageSender);
        when(messageSenders.get(SubscriptionType.MAIL)).thenReturn(mailMessageSender);
        Whitebox.setInternalState(subscriberNotifier, "subscribers",
                Arrays.asList(new Subscriber("1234567", SubscriptionType.TELEGRAM.toString()),
                new Subscriber("aavava@gmail.ru", SubscriptionType.MAIL.toString())));
    }

    @Test
    public void SubscriberNotifierTest() {
        subscriberNotifier.notifySubscribers("Text");
        verify(telegramMessageSender).sendMessage(anyObject(), anyObject());
    }
}
