package utils;


import java.util.Calendar;

public class Parsers {
    
    public static String parsePhoneNumber(String str) {
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

    public static String parseName(String str) {
        String name = "";
        name += Character.toUpperCase(str.charAt(0));
        name += str.substring(1);
        return name;
    }

    public static String parseTime(String str) {
        String[] dateAndTime = str.split("\\s+");
        String date;
        if (dateAndTime.length == 2) {
            String[] dateAndMonth = dateAndTime[0].split("(\\.|-)");
            date = Calendar.getInstance().get(Calendar.YEAR) + "-" + dateAndMonth[1] + "-" + dateAndMonth[0];
        } else {
            date = Calendar.getInstance().get(Calendar.YEAR) + "-" +
                    (Calendar.getInstance().get(Calendar.MONTH) + 1) + "-" + Calendar.getInstance().get(Calendar.DATE);
        }
        String time;
        int part = dateAndTime.length == 2 ? 1 : 0;
        String[] hoursAndMinutes =  dateAndTime[part].split("(-|:|\\.)");
        time = hoursAndMinutes[0] + ":" + hoursAndMinutes[1];
        return date + " " + time;
    }

    public static Long parseBookingId(String str) {
        String[] nums = str.split("\\D+");
        for (String num : nums) {
            if (num.length() > 0) {
                return Long.parseLong(num);
            }
        }
        return null;
    }
}
