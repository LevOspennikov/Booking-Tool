package model;

import java.util.Date;

public class Booking {
    private int id;
    private int userId;
    private Date time;
    private int personsCount;

    public Booking(int id, int userId, Date time, int personsCount) {
        this.id = id;
        this.userId = userId;
        this.time = time;
        this.personsCount = personsCount;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public Date getTime() {
        return time;
    }

    public int getPersonsCount() {
        return personsCount;
    }
}
