package com.example.takeaseat;
public class User {
    // data members
    public String email;
    public String name;
    public String password;
    public String uscID;
    public boolean activeReservation = false;
//    public Vector<Reservation> seatHistory = new Vector<Reservation>();
//    public String photoName;
    public String uscAffiliation;

    public User()
    {
        // Default Constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String name, String password, String uscID, String uscAffiliation) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.uscID = uscID;
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
}

