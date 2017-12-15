package notifier;

import model.Subscriber;
import utils.PropertyReader;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailMessageSender implements MessageSender {
    private String mailPassword;
    private String mailUser;

    MailMessageSender() {
        mailPassword = PropertyReader.readProperties().getProperty("mailPassword");
        mailUser = PropertyReader.readProperties().getProperty("mailUser");
    }

    public void sendMessage(Subscriber subscriber, String text) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.yandex.ru");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mailUser, mailPassword);
                    }
                });

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(mailUser + "@yandex.ru"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(subscriber.getSubscriberContact()));
            message.setSubject("Бронирование");
            message.setText(text);

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
