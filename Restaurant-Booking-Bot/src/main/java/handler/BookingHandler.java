package handler;

import database.SqlManager;
import model.Booking;
import model.User;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import resources.Message;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BookingHandler implements Handler {
    private Map<Long, User> usersMap = new HashMap<>();
    private Map<Long, Booking> bookingsMap = new HashMap<>();
    private SqlManager sqlManager = SqlManager.getInstance();

    @Override
    public boolean matchCommand(Update update) {
        long id = update.getMessage().getChatId();
        String message = update.getMessage().getText();
        if ("Забронировать".equals(message)) {
            User user = sqlManager.getUserById(id);
            if (user == null) {
                user = new User();
                user.setId(id);
                usersMap.put(id, user);
            }
            Booking booking = new Booking();
            booking.setUserId(id);
            bookingsMap.put(id, booking);
            return true;
        } else if (isNumber(message)) {
            usersMap.get(id).setPhone(parseNumber(message));
            return true;
        } else if (isName(message)) {
            usersMap.get(id).setName(parseName(message));
            return true;
        } else if (isTime(message)) {
            bookingsMap.get(id).setTime(parseTime(message));
            return true;
        } else if (isPersonsCount(message)) {
            bookingsMap.get(id).setPersonsCount(Integer.parseInt(message));
            return true;
        }
        return false;
    }

    @Override
    public SendMessage handle(Update update) {
        try {
            long id = update.getMessage().getChatId();
            User user = usersMap.get(id);
            boolean userExists = user == null;
            if (userExists) {
                user = sqlManager.getUserById(id);
            }
            if (user.getName() == null) {
                return Message.makeReplyMessage(update, "Пожалуйста, напишите Ваше имя");
            } else if (user.getPhone() == null) {
                return Message.makeReplyMessage(update, "Пожалуйста, укажите Ваш номер телефона");
            }
            Booking booking = bookingsMap.get(id);
            if (booking != null) {
                if (booking.getTime() == null) {
                    return Message.makeReplyMessage(update, "Пожалуйста, укажите время");
                } else if (booking.getPersonsCount() == 0) {
                    return Message.makeReplyMessage(update, "Пожалуйста, укажите колическтво персон");
                } else {
                    if (!userExists) {
                        sqlManager.addUser(user);
                    }
                    sqlManager.addBooking(booking);
                    StringBuilder builder = new StringBuilder();
                    builder.append("Ваше бронирование на имя " + user.getName() + " добавлено. Информация по бронированию:\n")
                           .append("Телефон: " + user.getPhone() + "\n")
                           .append("Время: " + booking.getTime() + "\n")
                           .append("Количество человек: " + booking.getPersonsCount() + "\n");
                    usersMap.remove(id);
                    bookingsMap.remove(id);
                    return Message.makeReplyMessage(update, builder.toString());
                }
            }
            return Message.makeReplyMessage(update, "Как ты сюда попал?");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Message.makeReplyMessage(update, "Что-то пошло не так");
        }
    }

    private boolean isNumber(String str) {
        return str.matches("\\d?\\(\\d{3}\\)\\d{3}-?\\d{2}-?\\d{2}") ||
                str.matches("\\d?-?\\d{3}-?\\d{3}-?\\d{2}-?\\d{2}");
    }

    private boolean isName(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isTime(String str) {
        return str.matches("(\\d{2}(\\.|-)\\d{2})?\\s*\\d{1,2}(-|:|\\.)\\d{1,2}");
    }

    private boolean isPersonsCount(String str) {
        return str.matches("\\d+") && !"0".equals(str);
    }

    private String parseNumber(String str) {
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                number.append(str.charAt(i));
            }
        }
        if (number.length() == 10) {
            number.insert(0, "8");
        }
        number.insert(1, "(");
        number.insert(5, ")");
        number.insert(9, "-");
        number.insert(12, "-");
        return number.toString();
    }

    private String parseName(String str) {
        String name = "";
        name += Character.toUpperCase(str.charAt(0));
        name += str.substring(1);
        return name;
    }

    private String parseTime(String str) {
        String[] dateAndTime = str.split("\\s+");
        String date;
        if (dateAndTime.length == 2) {
            String[] dateAndMonth = dateAndTime[0].split("(\\.|-)");
            date = Calendar.getInstance().get(Calendar.YEAR) + "-" + dateAndMonth[1] + "-" + dateAndMonth[0];
        } else {
            date = Calendar.getInstance().get(Calendar.YEAR) + "-" + Calendar.getInstance().get(Calendar.MONTH) + "-" + Calendar.getInstance().get(Calendar.DATE);
        }
        String time;
        int part = dateAndTime.length == 2 ? 1 : 0;
        String[] hoursAndMinutes =  dateAndTime[part].split("(-|:|\\.)");
        time = hoursAndMinutes[0] + ":" + hoursAndMinutes[1];
        return date + " " + time;
    }
}