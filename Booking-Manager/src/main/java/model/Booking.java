package model;

public class Booking {
    private long id;
    private String userName;
    private String userPhone;
    private String date;
    private int personsCount;

    public Booking() {}

    public Booking(long id, String userName, String userPhone, String date, int personsCount) {
        this.id = id;
        this.userName = userName;
        this.userPhone = userPhone;
        this.date = date;
        this.personsCount = personsCount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPersonsCount() {
        return personsCount;
    }

    public void setPersonsCount(int personsCount) {
        this.personsCount = personsCount;
    }

    public String getBookingDescription() {
        return "Id " + id + ": " + userName + ", " + date;
    }
}
