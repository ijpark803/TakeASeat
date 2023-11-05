package com.example.takeaseat;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class User {
    // Data members
    public String email;
    public String name;
    public String password;
    public String uscID;
    public boolean activeReservation = false;
    //public Vector<Reservation> seatHistory = new Vector<Reservation>();
    public HashMap<String, Boolean> reservations; // <reservation id, reserved true/false>

    //    public String photoName;
    public String uscAffiliation;

    public User()
    {
        // Default Constructor required for calls to DataSnapshot.getValue(User.class)
        email = "";
        name = "";
        password = "";
        uscID = "";
        activeReservation = false;
        reservations = new HashMap<>();
        uscAffiliation = "";
    }

    public User(String email, String name, String password, Boolean active, String uscID, String uscAffiliation, HashMap<String, Boolean> reservations) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.uscID = uscID;
        this.activeReservation = active;
        this.reservations = reservations;
        this.uscAffiliation = uscAffiliation;
    }


    public String getEmail()
    {
        return email;
    }
    public String getUscID()
    {
        return uscID;
    }

    public boolean checkReservationStatus()
    {
        return activeReservation;
    }
    public void flipReservationStatus()
    {
        if (activeReservation)
        {
            activeReservation = false;
        }
        else
        {
            activeReservation = true;
        }
    }

    public String getPassword() {
        return password;
    }
    public String getName() {
        return name;
    }

    //    public void addSeatHistory(Reservation r)
//    {
//        seatHistory.add(r);
//    }
    public String getUscAffiliation()
    {
        return uscAffiliation;
    }
//    public String getPhotoName()
//    {
//        return photoName;
//    }

    public HashMap<String, Boolean> getReservations() {
        return reservations;
    }

    public void setReservations(HashMap<String, Boolean> reservations) {
        this.reservations = reservations;
    }
}

