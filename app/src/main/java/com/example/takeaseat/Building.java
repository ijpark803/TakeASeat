package com.example.takeaseat;

public class Building {
    String id;
    String name;
    String hours;
    String description;

    Integer totalseats;
//    Seat[] seats;

    public Building(){
        id = "";
        name = "";
        hours = "";
        description = "";
        totalseats = 0;
    }
    public Building(String id, String name, String hours, String description, Integer seats){
        this.id = id;
        this.name = name;
        this.hours = hours;
        this.description = description;
        this.totalseats = seats;
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
