package com.example.takeaseat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.model.LocalTime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingPage#newInstance} factory method to
 * create an instance of this fragment.
 */

public class BookingPage extends Fragment {

    //Parameters and variables
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public MainActivity ma;
    private String buildingId = "";
    private TextView description;
    Building currBuilding;
    HashSet<Integer> selectedSlots = new HashSet<>(); // keeps track of # of slots picked
    int maxSelections = 4;
    private DatabaseReference mDatabase;

    public BookingPage() {
        // Empty constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingPage.
     */

    public static BookingPage newInstance(String param1, String param2) {
        BookingPage fragment = new BookingPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ma = (MainActivity)getActivity();
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_booking_page, container, false);
        // Receive building data from Mapview.java
        Bundle bundle = getArguments();
        if (bundle != null) {
            buildingId = bundle.getString("buildingId");
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("buildings").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        Building building = snapshot.getValue(Building.class);
                        // find building id
                        if (building != null && building.getId().equals(buildingId)) {
                            currBuilding = building;
                            description = rootView.findViewById(R.id.description);
                            description.setText(building.getDescription());
                            break;
                        }
                    }

                }
            }
        });

        // add the time slots
        LinearLayout timeSlotsLayout = rootView.findViewById(R.id.timeSlotsLayout);
        int count = 0;

        // indoor and outdoor reserve buttons for seating
        Button indoorReserveButton = rootView.findViewById(R.id.indoorreservebtn);
        indoorReserveButton.setEnabled(false);
        Button outdoorReserveButton = rootView.findViewById(R.id.outdoorreservebtn);
        outdoorReserveButton.setEnabled(false);

        // add time slots from 8:00 AM to 5:00 PM with 30-minute intervals
        for (int hour = 8; hour < 17; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                // format time
                String time = String.format("%02d:%02d", hour, minute);
                CheckBox timeSlotTextView = new CheckBox(getContext());
                mDatabase.child("buildings").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                Building building = snapshot.getValue(Building.class);
                                // find building through id
                                if (building != null && building.getId().equals(buildingId)) {
                                    HashMap<String, Building.TimeSlot> ts = building.timeSlots;
                                    int indoor = ts.get(time).getIndoor();
                                    int outdoor = ts.get(time).getOutdoor();

                                    timeSlotTextView.setText(time + " Indoor: " + indoor + " Outdoor: " + outdoor);
                                    break;
                                }
                            }
                        }
                    }
                });

                timeSlotTextView.setId(count);
                count++;
                timeSlotsLayout.addView(timeSlotTextView);

                Date currentTime = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

                try {
                    // Parse the formatted time string to Date
                    Date specifiedTime = sdf.parse(time);

                    // Compare the times
                    if (currentTime.before(specifiedTime)) {
                        System.out.println("future");
                        timeSlotTextView.setEnabled(true);
                    } else if (currentTime.after(specifiedTime)) {
                        System.out.println("past]");
                        timeSlotTextView.setEnabled(false);
                    } else {
                        System.out.println("now");
                        timeSlotTextView.setEnabled(false);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Code to restrict user from selecting more than 4 slots
                timeSlotTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int slotId = timeSlotTextView.getId();
                        if (selectedSlots.size() <= maxSelections) {
                                // Slot is not selected and the limit is not reached
                                if (selectedSlots.contains(slotId)) {
                                    // Slot is already selected, deselect it
                                    selectedSlots.remove(slotId);
                                    timeSlotTextView.setChecked(false);
                                }
                                // not contained but size is already 4
                                else if (selectedSlots.size() == 4)
                                {
                                    // cannot add it
                                    timeSlotTextView.setChecked(false);
                                }
                                // not contained and size is not 4 so we can add it
                                else {
                                    selectedSlots.add(slotId);
                                    timeSlotTextView.setChecked(true);
                                }
                        }
                        else
                        {
                            timeSlotTextView.setChecked(false);
                        }
                        if (selectedSlots.size() <= maxSelections && checkConsecutivenessOfSelectedSlots() && selectedSlots.size() > 0) {
                            indoorReserveButton.setEnabled(true);
                            outdoorReserveButton.setEnabled(true);
                        }
                        else {
                            indoorReserveButton.setEnabled(false);
                            outdoorReserveButton.setEnabled(false);
                        }
                    }
                });

            }
        }

        indoorReserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ma.currentUser.activeReservation == true) {
                    Toast.makeText(getContext(), "You already have an active reservation", Toast.LENGTH_SHORT).show();
                    return; // cant reserve if already have active reservation
                }
                Reservation curr = new Reservation();
                if (selectedSlots.size() <= maxSelections && checkConsecutivenessOfSelectedSlots()) {
                    Object[] times = selectedSlots.toArray();
                    int startingIndex = (Integer) times[0];
                    CheckBox slotView = rootView.findViewById(startingIndex);
                    String slot_start = slotView.getText().toString();

                    List<Integer> selectedTimeSlots = new ArrayList<>(selectedSlots);
                    decrementIndoor(selectedTimeSlots);
                    // Perform reservation logic
                    if(selectedSlots.size() == 1){
                        curr = new Reservation(ma.currentUser.email, buildingId, slot_start.substring(0,5), true, ServerValue.TIMESTAMP, "0.5", true);
                    }
                    else if(selectedSlots.size() > 1){
                        double slot_duration = 0.5 * selectedSlots.size();
                        curr = new Reservation(ma.currentUser.email, buildingId, slot_start.substring(0,5), true, ServerValue.TIMESTAMP, slot_duration+"", true);
                    }
                    mDatabase.child("reservations").push().setValue(curr);
                    mDatabase.child("users").child(ma.currentUser.email.toString().replace(".","_")).child("activeReservation").setValue(true);
                }
            }
        });

        //add logic for other button
        outdoorReserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ma.currentUser.activeReservation == true) {
                    Toast.makeText(getContext(), "you already have an active reservation", Toast.LENGTH_SHORT).show();
                    return; // cant reserve if already have active reservation
                }
                Reservation curr = new Reservation();
                if (selectedSlots.size() <= maxSelections && checkConsecutivenessOfSelectedSlots()) {
                    Object[] times = selectedSlots.toArray();
                    int startingIndex = (Integer) times[0];
                    CheckBox slotView = rootView.findViewById(startingIndex);
                    String slot_start = slotView.getText().toString();
                    // Perform reservation logic
                    List<Integer> selectedTimeSlots = new ArrayList<>(selectedSlots);
                    decrementOutdoor(selectedTimeSlots);
                    if(selectedSlots.size() == 1){
                        curr = new Reservation(ma.currentUser.name, buildingId, slot_start.substring(0,5), true, ServerValue.TIMESTAMP, "0.5", false);
                    }
                    else if(selectedSlots.size() > 1){
                        double slot_duration = 0.5 * selectedSlots.size();
                        curr = new Reservation(ma.currentUser.name, buildingId, slot_start.substring(0,5), true, ServerValue.TIMESTAMP, slot_duration+"", false);
                    }
                    mDatabase.child("reservations").push().setValue(curr);
                    mDatabase.child("users").child(ma.currentUser.name).child("activeReservation").setValue(true);
                }
            }
        });
        return rootView;
    }

    private boolean isConsecutive(int slot1, int slot2) {
        // Implement logic to check if slots are consecutive
        return Math.abs(slot1 - slot2) == 1;
    }
    private boolean checkConsecutivenessOfSelectedSlots() {
        List<Integer> selectedSlotNumbers = new ArrayList<>();

        // Extract slot numbers from selectedSlots
        for (Integer selectedSlot : selectedSlots) {
            selectedSlotNumbers.add(selectedSlot);
        }

        // Sort the selected slot numbers
        Collections.sort(selectedSlotNumbers);

        // Check if the selected slots are consecutive
        for (int i = 0; i < selectedSlotNumbers.size() - 1; i++) {
            if (selectedSlotNumbers.get(i + 1) - selectedSlotNumbers.get(i) != 1) {
                return false;
            }
        }
        return true;
    }

    public void decrementOutdoor(List<Integer> selectedSlotNumbers)
    {
        for (int i : selectedSlotNumbers)
        {
            int hour = 8 + i / 2;
            int minute = (i % 2) * 30;
            // Time Formatting
            String time = String.format("%02d:%02d", hour, minute);
            int temp = currBuilding.timeSlots.get(time).getOutdoor();
            currBuilding.timeSlots.get(time).setOutdoor(temp-1);
        }
        mDatabase.child("buildings").child(currBuilding.getName()).setValue(currBuilding);
    }

    public void decrementIndoor(List<Integer> selectedSlotNumbers)
    {
        for (int i : selectedSlotNumbers)
        {
            int hour = 8 + i / 2;
            int minute = (i % 2) * 30;
            // Time Formatting
            String time = String.format("%02d:%02d", hour, minute);
            int temp = currBuilding.timeSlots.get(time).getIndoor();
            currBuilding.timeSlots.get(time).setIndoor(temp-1);
        }
        mDatabase.child("buildings").child(currBuilding.getName()).setValue(currBuilding);
    }
}
