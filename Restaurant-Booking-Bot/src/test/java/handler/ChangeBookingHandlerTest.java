package handler;

import constants.Messages;
import database.SqlManager;
import model.Booking;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Collections;

import static org.mockito.Mockito.when;

public class ChangeBookingHandlerTest {
    private ChangeBookingHandler changeBookingHandler = new ChangeBookingHandler();
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private SqlManager sqlManager;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        long id = 1L;
        Booking booking = new Booking(id, 1L, "2017-12-18 19:00", 3);
        when(sqlManager.getBookingById(id)).thenReturn(booking);
        when(sqlManager.getUserBookings(id)).thenReturn(Collections.singletonList(booking));
        when(sqlManager.getUserById(id)).thenReturn(new User(1L, "Anna", "89819510000"));
        Whitebox.setInternalState(changeBookingHandler, "sqlManager", sqlManager);
        when(update.getMessage()).thenReturn(message);
    }

    @Test
    public void workflowTest() {
        workflow(Messages.TIME, "20.12 20.00", "incorrect145");
    }

    @Test
    public void workflowTest2() {
        workflow(Messages.COUNT, "3", "0");
    }

    @Test
    public void workflowTest3() {
        workflow(Messages.NAME, "anna", "");
    }

    @Test
    public void workflowTest4() {
        workflow(Messages.PHONE, "9919510000", "");
    }

    @Test
    public void unexpectedIdTest() {
        when(message.getChatId()).thenReturn(2L);
        when(message.getText()).thenReturn(Messages.CHANGE_BOOKING);
        Assert.assertTrue(changeBookingHandler.matchCommand(update));
        Assert.assertEquals(Messages.NO_BOOKINGS_ERROR, changeBookingHandler.handle(update).getText());
    }

    private void workflow(String parameter, String value, String incorrect) {
        when(message.getChatId()).thenReturn(1L);
        when(message.getText()).thenReturn(Messages.CHANGE_BOOKING);
        Assert.assertTrue(changeBookingHandler.matchCommand(update));
        Assert.assertEquals(Messages.SELECT_BOOKING_TO_CHANGE, changeBookingHandler.handle(update).getText());
        when(message.getText()).thenReturn("Id: 1, 2017-12-18 18:30, 6 чел.");
        Assert.assertTrue(changeBookingHandler.matchCommand(update));
        Assert.assertEquals(Messages.SELECT_PARAMETER_TO_CHANGE, changeBookingHandler.handle(update).getText());
        when(message.getText()).thenReturn(parameter);
        Assert.assertTrue(changeBookingHandler.matchCommand(update));
        Assert.assertEquals(Messages.ENTER_NEW_VAL, changeBookingHandler.handle(update).getText());
        if (!incorrect.isEmpty()) {
            when(message.getText()).thenReturn(incorrect);
            Assert.assertTrue(changeBookingHandler.matchCommand(update));
            Assert.assertEquals(Messages.INCORRECT_VALUE, changeBookingHandler.handle(update).getText());
        }
        when(message.getText()).thenReturn(value);
        Assert.assertTrue(changeBookingHandler.matchCommand(update));
        Assert.assertTrue(changeBookingHandler.handle(update).getText()
                .startsWith("Ваше бронирование изменено. Информация по бронированию:"));
    }
}
