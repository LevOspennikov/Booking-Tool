package notifier;

import model.Subscriber;

interface MessageSender {
    void sendMessage(Subscriber subscriber, String message);
}
