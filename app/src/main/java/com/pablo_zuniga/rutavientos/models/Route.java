package com.pablo_zuniga.rutavientos.models;

import com.pablo_zuniga.rutavientos.app.MyApplication;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Route extends RealmObject {
    @PrimaryKey
    private int id;

    private String destiny;
    private String origin;
    private int freeSeats;
    private Date dateHour;
    private int driver;

    public Route(){}

    public Route(String origin, String destiny, int freeSeats, Date dateHour, int driver) {
        this.id = MyApplication.routeId.incrementAndGet();
        this.destiny = destiny;
        this.origin = origin;
        this.freeSeats = freeSeats;
        this.dateHour = dateHour;
        this.driver = driver;
    }

    public int getId() {
        return id;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getFreeSeats() { return freeSeats; }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public String getDateHour() { return String.valueOf(dateHour.getHours()) + ":" + String.valueOf(dateHour.getMinutes()); }

    public void setDateHour(Date dateHour) { this.dateHour = dateHour; }

    public int getDriver() { return driver; }

    public void setDriver(int driver) { this.driver = driver; }
}
