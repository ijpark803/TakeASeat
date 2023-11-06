package com.example.takeaseat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

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
                        if (building != null && building.getId().equals(buildingId)) {
                            // Find the building with the matching ID
                            currBuilding = building;
                            description = rootView.findViewById(R.id.description);
                            description.setText(building.getDescription());
                            break; // Exit loop since you found the building
                        }
                        Log.d("Firebase", building.toString());
                    }

                } else {
                    Log.e("Firebase", "Error getting data", task.getException());
                }

            }
        });

        // Get the LinearLayout and add the time slots
        LinearLayout timeSlotsLayout = rootView.findViewById(R.id.timeSlotsLayout);
        int count = 0; // counter to assign id to each time slot
        Button reserveButton = rootView.findViewById(R.id.reservebtn);
        reserveButton.setEnabled(false);

        // Add time slots from 8:00 AM to 5:00 PM with 30-minute intervals
        for (int hour = 8; hour < 17; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                //Time formatting
                String time = String.format("%02d:%02d", hour, minute);
                CheckBox timeSlotTextView = new CheckBox(getContext());
                //Get the number of seats available
                mDatabase.child("buildings").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DataSnapshot snapshot : task.getResult().getChildren()) {
                                Building building = snapshot.getValue(Building.class);
                                if (building != null && building.getId().equals(buildingId)) {
                                    // found the building with the matching ID
                                    HashMap<String, Building.TimeSlot> ts = building.timeSlots;
                                    int indoor = ts.get(time).getIndoor();
                                    int outdoor = ts.get(time).getOutdoor();

                                    timeSlotTextView.setText(time + " Indoor: " + indoor + " Outdoor: " + outdoor);
                                    break; // Exit loop since you found the building
                                }
                                Log.d("Firebase", building.toString());
                            }

                        } else {
                            Log.e("Firebase", "Error getting data", task.getException());
                        }

                    }
                });


                timeSlotTextView.setId(count);
                count++;
                timeSlotsLayout.addView(timeSlotTextView);

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
                            reserveButton.setEnabled(true);
                        }
                        else {
                            reserveButton.setEnabled(false);
                        }
                    }
                });

            }
        }


        reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedSlots.size() <= maxSelections && checkConsecutivenessOfSelectedSlots()) {
                    // Perform reservation logic
                } else {
                    // Display a message or handle the case where constraints are not met
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

}
