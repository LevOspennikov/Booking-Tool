package model;

import java.util.Date;

public class Booking {
    private int id;
    private String userName;
    private String userPhone;
    private Date date;
    private int personsCount;

    public Booking(int id, String userName, String userPhone, Date date, int personsCount) {
        this.id = id;
        this.userName = userName;
        this.userPhone = userPhone;
        this.date = date;
        this.personsCount = personsCount;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public Date getDate() {
        return date;
    }

    public int getPersonsCount() {
        return personsCount;
    }
}
