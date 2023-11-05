package com.example.takeaseat;

import java.util.HashMap;
import java.util.Map;

public class Building {
    String id;
    String name;
    String hours;
    String description;

    Integer totalseats;
    public HashMap<String, TimeSlot> timeSlots;
    //nested time slot class
    public static class TimeSlot {
        private int indoor_seatsAvailable;
        private int outdoor_seatsAvailable;
        //constructor
        public TimeSlot(){
            this.indoor_seatsAvailable = 0;
            this.outdoor_seatsAvailable = 0;
        }
        public TimeSlot(Integer In, Integer Out){
            this.indoor_seatsAvailable = In;
            this.outdoor_seatsAvailable = Out;
        }


        // Getters and setters
        public int getIndoor() {
            return indoor_seatsAvailable;
        }
        public int getOutdoor() {
            return outdoor_seatsAvailable;
        }
        public void setIndoor(int seatsAvailable) {
            this.indoor_seatsAvailable = seatsAvailable;
        }
        public void setOutdoor(int seatsAvailable) {
            this.outdoor_seatsAvailable = seatsAvailable;
        }
    }
//    Seat[] seats;

    public Building(){
        id = "";
        name = "";
        hours = "";
        description = "";
        totalseats = 0;
        timeSlots = new HashMap<>();
    }
    public Building(String id, String name, String hours, String description, Integer seats, HashMap<String, TimeSlot> timeSlots){
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.description = description;
        this.totalseats = seats;
        this.timeSlots = timeSlots;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }
    public String getHours(){
        return hours;
    }
    public String getDescription(){
        return description;
    }
    public Integer getTotalSeats(){
        return totalseats;
    }
//    public void setTimeSlots(Map<String, TimeSlot> timeSlots) {
//        this.timeSlots = timeSlots;
//    }



    public static void main(String[] args){

    }

    // this method will return the seat object given an integer
//    Seat getSeat(int index){
//        for(int i = 0; i < seats.size(); i++){
//            if(seats[i].seatId == index){
//                return seats[i];
//            }
//        }
//    }
}
