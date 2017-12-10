package model;

public class Booking {
    private int id;
    private long userId;
    private String time;
    private int personsCount;

    public Booking() {};

    public Booking(int id, long userId, String time, int personsCount) {
        this.id = id;
        this.userId = userId;
        this.time = time;
        this.personsCount = personsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPersonsCount() {
        return personsCount;
    }

    public void setPersonsCount(int personsCount) {
        this.personsCount = personsCount;
    }
}
