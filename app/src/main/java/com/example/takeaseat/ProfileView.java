package com.example.takeaseat;

import static java.lang.Math.max;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class ProfileView extends Fragment {

    View view;
    public MainActivity ma;
    TextView name, affiliation, id;

    ImageView photo;

    private FirebaseAuth mAuth;
    private Button logoutbtn;
    private Button cancelbtn;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile_view, container, false);
        ma = (MainActivity) getActivity();
        name = (TextView) view.findViewById(R.id.uscname);
        affiliation = (TextView) view.findViewById(R.id.uscaffiliation);
        id = (TextView) view.findViewById(R.id.uscid);
        photo = view.findViewById(R.id.photo);
        // On create display name, affiliation, and id of current user
        name.setText(ma.currentUser.name);
        affiliation.setText(ma.currentUser.uscAffiliation);
        id.setText(ma.currentUser.uscID);
        String photoUriString = ma.currentUser.getPhotoName();
        logoutbtn = view.findViewById(R.id.logoutbtn);
        cancelbtn = view.findViewById(R.id.cancelbtn);

        Uri photoUri = Uri.parse(photoUriString);
        Glide.with(requireContext()).load(photoUri).into(photo);

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                ma.loggedIn = false;
                replaceFragment(new Login());

            }
        });
        TreeMap<String, List<String>> reservationsMap = new TreeMap<>(Collections.reverseOrder());

        String userName = ma.currentUser.getName();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        LinearLayout containerLayout = view.findViewById(R.id.pastReservationsList);
        mDatabase.child("reservations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                containerLayout.removeAllViews();
                for (DataSnapshot reservationSnapshot : dataSnapshot.getChildren()) {
                    String key = reservationSnapshot.getKey();
                    DataSnapshot userIdSnapshot = reservationSnapshot.child("userId");
                    String userIdValue = userIdSnapshot.getValue(String.class);

                    if (userIdValue != null && userIdValue.equals(userName)) {
                        DataSnapshot dateSnapshot = reservationSnapshot.child("date");
                        String dateValue = dateSnapshot.getValue(String.class);
                        if (dateValue != null) {
                            if (!reservationsMap.containsKey(dateValue)) {
                                reservationsMap.put(dateValue, new ArrayList<>());
                            }

                            List<String> reservationsList = reservationsMap.get(dateValue);
                            reservationsList.add(key);
                        }
                    }

                    for (Map.Entry<String, List<String>> entry : reservationsMap.entrySet()) {
//                        String date = entry.getKey();
//                        Date d = new Date(date);
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//                        String formattedDate = sdf.format(date);
                        List<String> reservationKeys = entry.getValue();
                        for(String k : reservationKeys){
                            TextView ReservationDisplay = new TextView(getContext());
                            DataSnapshot snap = dataSnapshot.child(k);
                            String reservationDate = snap.child("date").getValue(String.class);
                            final String[] buildingId = {snap.child("buildingId").getValue(String.class)};
                            String duration = snap.child("duration").getValue(String.class);
                            Boolean status = snap.child("status").getValue(Boolean.class);
                            String timeSlot = snap.child("timeSlot").getValue(String.class);
                            String userId = snap.child("userId").getValue(String.class);
                            Boolean location = snap.child("indoor").getValue(Boolean.class);
                            String indoor = "";
                            if(location != null && location == true) {
                                indoor = "Indoor";
                            }
                            else if(location != null && location == false){
                                indoor = "Outdoor";
                            }
                            final String[] buildingName = {""};
                            // check if date of res. passed. then status can be changed to false.

                            Date currentDate = new Date();
                            Date resDate = new Date();

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                            Log.d("look", reservationDate);
                            try {
                                resDate = dateFormat.parse(reservationDate);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            Log.d("COMPARE", resDate + "VS" + currentDate);

                            if(resDate.after(currentDate)){
                                //res date is after currendate. so status is active
                                status = true;
                                Log.d("compare", "is true");
                            }
                            else if(resDate.before(currentDate)){
                                status = false;
                            }
                            else{
                                //compare the times, since date is same.
                                Date specifiedTime = parseTime(timeSlot);
                                Date currentTime = Calendar.getInstance().getTime();

                                // Compare the specified time with the current time
                                if (specifiedTime.before(currentTime)) {
                                    status = false;
                                    // timeslot is before current time == false
                                    if(ma.currentUser.activeReservation){
                                        ma.currentUser.activeReservation = false;
                                    }
                                } else if (specifiedTime.after(currentTime)) {
                                    // time slot is after current time == true
                                    status = true;
                                } else {
                                    status = true;
                                }
                                Log.d("compare!!", currentDate + currentTime.toString() + "vs" + resDate + timeSlot);

                            }

                            DatabaseReference reservationRef = dataSnapshot.getRef().child(k).child("status");
                            reservationRef.setValue(status);
                            mDatabase.child("users").child(ma.currentUser.getEmail().replace(".","_")).child("activeReservation").setValue(status);
                            Boolean finalStatus = status;
                            String finalIndoor = indoor;
                            mDatabase.child("buildings").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                            Building building = snapshot.getValue(Building.class);
                                            if (building != null && building.getId().equals(buildingId[0])) {
                                                // found the building with the matching ID
                                                HashMap<String, Building.TimeSlot> ts = building.timeSlots;
                                                buildingName[0] = building.getName();
                                                String rez = ("Date: " + reservationDate +
                                                        ", Time Slot: " + timeSlot + ", Status: " + finalStatus + ", Duration: " + duration + "hours, Building ID: " + buildingName[0]);
                                                Log.d("reservation", rez);
                                                ReservationDisplay.setText("Date: " + reservationDate +
                                                        ", Time Slot: " + timeSlot + ", Status: " + finalStatus + ", Duration: " + duration + "hours, Building ID: " + buildingName[0] + ", Location: " + finalIndoor);
                                                containerLayout.addView(ReservationDisplay);
                                                break;
                                            }
                                            Log.d("Firebase", building.toString());
                                        }

                                    } else {
                                        Log.e("Firebase", "Error getting data", task.getException());
                                    }

                                }
                            });


                        }
                    }
                    //containerLayout.addView(ReservationDisplay);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // when cancel button is clicked, we should go through all reservations for a user and flip them to inactive
                // if we want to keep note of whether it was cancelled, we can create a new field for the reservation in the database "wasCancelled"
                mDatabase.child("reservations").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot reservationSnapshot : dataSnapshot.getChildren()) {
                            String key = reservationSnapshot.getKey();
                            DataSnapshot userIdSnapshot = reservationSnapshot.child("userId");
                            String userIdValue = userIdSnapshot.getValue(String.class);
                            // check if the userName matches
                            if (userIdValue != null && userIdValue.equals(userName)) {
                                DataSnapshot statusSnapshot = reservationSnapshot.child("status");
                                Boolean statusValue = statusSnapshot.getValue(Boolean.class);
                                // if status was true, we want to set it to false
                                // create a new field was cancelled
                                if (statusValue) {
                                    mDatabase.child("reservations").child(key).child("status").setValue(false);
                                    mDatabase.child("reservations").child(key).child("wasCancelled").setValue(true);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                replaceFragment(new ProfileView());
            }
        });

        return view;
    }
    public void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }
    public static Date parseTime(String timeString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.US);
            return dateFormat.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}