package com.example.takeaseat;

public class Reservation {
    public String userId;
    public String buildingId;
    public String timeSlot;
    public Boolean status;

    public String duration;

    public Object date;

    public static void main(String[] args){

    }
    public Reservation() {
        userId = "";
        buildingId = "";
        timeSlot = "";
        status = false;
        date = null;
    }
    public Reservation(String userId, String buildingId, String timeSlot, Boolean status, Object date, String duration) {
        this.userId = userId;
        this.buildingId = buildingId;
        this.timeSlot = timeSlot;
        this.status = status;
        this.date = date;
        this.duration = duration;
    }


}
