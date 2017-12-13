package utils;


public class Validations {

    public static boolean isBookingDescription(String str) {
        return str.matches("Id: \\d+, \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}, \\d+ чел.");
    }

    public static boolean isPhoneNumber(String str) {
        return str.matches("\\d?\\(\\d{3}\\)\\d{3}-?\\d{2}-?\\d{2}") ||
                str.matches("\\d?-?\\d{3}-?\\d{3}-?\\d{2}-?\\d{2}");
    }

    public static boolean isName(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isTime(String str) {
        return str.matches("(\\d{2}(\\.|-)\\d{2})?\\s*\\d{1,2}(-|:|\\.)\\d{1,2}");
    }

    public static boolean isPersonsCount(String str) {
        return str.matches("\\d+") && !"0".equals(str);
    }
}
