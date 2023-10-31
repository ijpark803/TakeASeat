package com.example.takeaseat;
public class User {
    // data members
    public String username;
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

    public User(String username, String name, String password, String uscID, String uscAffiliation) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.uscID = uscID;
        this.uscAffiliation = uscAffiliation;
    }

    public void writeNewUser(String username, String name, String password, String uscID, String uscAffiliation) {
        User user = new User(username, name, password, uscID, uscAffiliation);
//        mDatabase.child("users").child(userId).setValue(user);
    }
    public String getUsername()
    {
        return username;
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

