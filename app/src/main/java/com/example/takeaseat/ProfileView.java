package com.example.takeaseat;

import static java.lang.Math.max;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProfileView extends Fragment {

    View view;
    public MainActivity ma;
    TextView name, affiliation, id;

    ImageView photo;

    private FirebaseAuth mAuth;
    private Button logoutbtn;
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
        TreeMap<Long, List<String>> reservationsMap = new TreeMap<>(Collections.reverseOrder());

        String userName = ma.currentUser.getName();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        LinearLayout ResList = view.findViewById(R.id.pastReservationsList);
        mDatabase.child("reservations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot reservationSnapshot : dataSnapshot.getChildren()) {
                    String key = reservationSnapshot.getKey();
                    DataSnapshot userIdSnapshot = reservationSnapshot.child("userId");
                    String userIdValue = userIdSnapshot.getValue(String.class);

                    if (userIdValue != null && userIdValue.equals(userName)) {
                        DataSnapshot dateSnapshot = reservationSnapshot.child("date");
                        Long dateValue = dateSnapshot.getValue(Long.class);
                        if (dateValue != null) {
                            if (!reservationsMap.containsKey(dateValue)) {
                                reservationsMap.put(dateValue, new ArrayList<>());
                            }

                            List<String> reservationsList = reservationsMap.get(dateValue);
                            reservationsList.add(key);
                        }
                    }
                    TextView ReservationDisplay = new TextView(getContext());
                    for (Map.Entry<Long, List<String>> entry : reservationsMap.entrySet()) {
                        Long date = entry.getKey();
                        List<String> reservationKeys = entry.getValue();
                        for(String k : reservationKeys){

                            DataSnapshot snap = dataSnapshot.child(k);

                            final String[] buildingId = {snap.child("buildingId").getValue(String.class)};
                            String duration = snap.child("duration").getValue(String.class);
                            Boolean status = snap.child("status").getValue(Boolean.class);
                            String timeSlot = snap.child("timeSlot").getValue(String.class);
                            String userId = snap.child("userId").getValue(String.class);
                            final String[] buildingName = {""};
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
                                                ReservationDisplay.setText("Date: " + date +
                                                        ", Time Slot: " + timeSlot + ", Status: " + status + ", Duration: " + duration + ", Building ID: " + buildingName[0]);
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
                    ResList.addView(ReservationDisplay);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }
}