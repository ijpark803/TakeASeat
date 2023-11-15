package com.example.takeaseat;

import java.util.Date;

public class Reservation {
    public String userId;
    public String buildingId;
    public String timeSlot;
    public Boolean status;

    public String duration;

    public String date;
    public Boolean indoor;


    public static void main(String[] args){

    }
    public Reservation() {
        userId = "";
        buildingId = "";
        timeSlot = "";
        status = false;
        date = "";
        indoor = false;

    }
    public Reservation(String userId, String buildingId, String timeSlot, Boolean status, String date, String duration, Boolean indoor) {
        this.userId = userId;
        this.buildingId = buildingId;
        this.timeSlot = timeSlot;
        this.status = status;
        this.date = date;
        this.duration = duration;
        this.indoor = indoor;

    }


}
