package handler;

import constants.Messages;
import database.SqlManager;
import notifier.SubscriberNotifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class BookingHandlerTest {
    private BookingHandler bookingHandler = new BookingHandler();
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private SqlManager sqlManager;
    @Mock
    private SubscriberNotifier subscriberNotifier;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(sqlManager.getUserById(anyLong())).thenReturn(null);
        Whitebox.setInternalState(bookingHandler, "sqlManager", sqlManager);
        Whitebox.setInternalState(bookingHandler, "subscriberNotifier", subscriberNotifier);
        when(update.getMessage()).thenReturn(message);
    }

    @Test
    public void workflowTest() {
        when(message.getChatId()).thenReturn(1L);
        when(message.getText()).thenReturn(Messages.BOOK);
        Assert.assertTrue(bookingHandler.matchCommand(update));
        Assert.assertEquals(Messages.ENTER_NAME, bookingHandler.handle(update).getText());
        when(message.getText()).thenReturn("anna");
        Assert.assertTrue(bookingHandler.matchCommand(update));
        Assert.assertEquals(Messages.ENTER_PHONE, bookingHandler.handle(update).getText());
        when(message.getText()).thenReturn("9819510000");
        Assert.assertTrue(bookingHandler.matchCommand(update));
        Assert.assertEquals(Messages.ENTER_TIME, bookingHandler.handle(update).getText());
        when(message.getText()).thenReturn("18.45");
        Assert.assertTrue(bookingHandler.matchCommand(update));
        Assert.assertEquals(Messages.ENTER_COUNT, bookingHandler.handle(update).getText());
        when(message.getText()).thenReturn("0");
        Assert.assertTrue(bookingHandler.matchCommand(update));
        Assert.assertEquals(Messages.INCORRECT_VALUE, bookingHandler.handle(update).getText());
        when(message.getText()).thenReturn("-2");
        Assert.assertTrue(bookingHandler.matchCommand(update));
        Assert.assertEquals(Messages.INCORRECT_VALUE, bookingHandler.handle(update).getText());
        when(message.getText()).thenReturn("lev96");
        Assert.assertTrue(bookingHandler.matchCommand(update));
        Assert.assertEquals(Messages.INCORRECT_VALUE, bookingHandler.handle(update).getText());
        when(message.getText()).thenReturn("5");
        Assert.assertTrue(bookingHandler.matchCommand(update));
        Assert.assertTrue(bookingHandler.handle(update).getText().startsWith("Ваше бронирование на имя "));
    }

    @Test
    public void unexpectedIdTest() {
        when(message.getChatId()).thenReturn(2L);
        when(message.getText()).thenReturn("9819510000");
        Assert.assertFalse(bookingHandler.matchCommand(update));
    }
}
