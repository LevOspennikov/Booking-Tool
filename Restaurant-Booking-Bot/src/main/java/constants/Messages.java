package constants;

public class Messages {
    public static final String GREETING = "Приветствуем Вас в нашем ресторане \"Библиотека\" " +
            "по адресу г. Санкт-Петербург, пер. Вяземский, 3. Здесь Вы можете быстро и удобно забронировать столик.";
    public static final String BOOK = "Забронировать";
    public static final String CHANGE_BOOKING = "Изменить бронирование";
    public static final String ENTER_NAME = "Пожалуйста, напишите Ваше имя";
    public static final String ENTER_PHONE = "Пожалуйста, укажите Ваш номер телефона";
    public static final String ENTER_TIME = "Пожалуйста, укажите время";
    public static final String ENTER_COUNT = "Пожалуйста, укажите колическтво персон";
    public static final String ENTER_NEW_VAL = "Введите новое значение";
    public static final String NAME = "Имя";
    public static final String PHONE = "Телефон";
    public static final String TIME = "Время";
    public static final String COUNT = "Количество человек";
    public static final String SELECT_BOOKING_TO_CHANGE = "Выберите бронирование, которое хотите изменить";
    public static final String SELECT_PARAMETER_TO_CHANGE = "Выберите, какой параметр нужно изменить";
    public static final String NO_BOOKINGS_ERROR = "У Вас нет активных бронирований";
    public static final String INTERNAL_ERROR = "Произошла внутренняя ошибка. Пожалуйста, попробуйте еще раз";
    public static final String PARSER_ERROR = "Не удалось распознать запрос. Пожалуйста, попробуйте снова";
    public static final String INCORRECT_VALUE = "Введенное значение некорректно. Пожалуйста, попробуйте снова";

    public static String addBookingMessage(String user, String phone, String time, String count) {
        StringBuilder builder = new StringBuilder();
        builder.append("Ваше бронирование на имя ").append(user).append(" добавлено. Информация по бронированию:\n");
        return endPartBookingMessage(builder, phone, time, count);
    }

    public static String newBookingMessage(String user, String phone, String time, String count) {
        StringBuilder builder = new StringBuilder();
        builder.append("Добавлено новое бронирование на имя ").append(user).append(". Информация по бронированию:\n");
        return endPartBookingMessage(builder, phone, time, count);
    }

    public static String changeBookingMessage(String user, String phone, String time, String count) {
        StringBuilder builder = new StringBuilder();
        builder.append("Ваше бронирование изменено. Информация по бронированию:\n")
                .append("Имя: ").append(user).append("\n");
        return endPartBookingMessage(builder, phone, time, count);
    }

    private static String endPartBookingMessage(StringBuilder builder, String phone, String time, String count) {
        builder.append("Телефон: ").append(phone).append("\n")
                .append("Время: ").append(time).append("\n")
                .append("Количество человек: ").append(count);
        return builder.toString();
    }
}
