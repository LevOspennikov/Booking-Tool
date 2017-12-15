package handler;

import constants.Messages;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import static org.mockito.Mockito.when;

public class ErrorHandlerTest {
    private ErrorHandler errorHandler = new ErrorHandler();
    @Mock
    private Update update;
    @Mock
    private Message message;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(update.getMessage()).thenReturn(message);
    }

    @Test
    public void workflowTest() {
        Assert.assertTrue(errorHandler.matchCommand(update));
        Assert.assertEquals(Messages.PARSER_ERROR, errorHandler.handle(update).getText());
    }
}
