package com.example.takeaseat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    // first fragmentbtn is map button
    // second fragmentbtn is profile button
    Button firstFragmentBtn, secondFragmentBtn;
    User currentUser = new User();
    public boolean loggedIn = false;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    public MainActivity ma;
    public Vector<Building> buildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseApp.initializeApp(this);

        buildings = new Vector<>();

        //populate the database, if not already done.
        Building.TimeSlot timeSlot = new Building.TimeSlot();
        timeSlot.setIndoor(5);
        timeSlot.setOutdoor(5);

        HashMap<String, Building.TimeSlot> timeSlots = new HashMap<>();
        for (int hour = 8; hour < 17; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                String time = String.format("%02d:%02d", hour, minute);
                timeSlots.put(time, timeSlot);
            }
        }

        Building WAS = new Building(
                "1",
                "Annenberg",
                "09:09",
                "Wallis Annenberg Hall, an 88,000-square-foot edifice that blends tradition with technology. Incoming students, faculty and staff in the public relations, journalism and communications programs will have access to fast wifi, a converged media center, multimedia content creation classrooms, a café and numerous meeting and seating pods.",
                5,
                timeSlots
        );
        buildings.add(WAS);
        Building OHE = new Building(
                "2",
                "Olin Hall of Engineering",
                "09:09",
                "description of olin hall",
                5,
                timeSlots
        );
        buildings.add(OHE);

        Building LVL = new Building(
                "3",
                "Leavey Library",
                "09:09",
                "Leavey Library is a dedicated library for undergraduate students",
                5,
                timeSlots
        );
        buildings.add(LVL);

        Building JFF = new Building(
                "4",
                "Fertitta Hall",
                "09:09",
                "description of Fertitta hall",
                5,
                timeSlots
        );
        buildings.add(JFF);

        Building VPD = new Building(
                "5",
                "Verna and Peter Dauterive Hall",
                "09:09",
                "description of VPD hall",
                5,
                timeSlots
        );
        buildings.add(VPD);

        Building RTCC = new Building(
                "6",
                "Ronald Tutor",
                "09:09",
                "description of RTCC",
                5,
                timeSlots
        );
        buildings.add(RTCC);

        Building SEL = new Building(
                "7",
                "Science and Engineering Library",
                "09:09",
                "description of SEL",
                5,
                timeSlots
        );
        buildings.add(SEL);

        Building SAL = new Building(
                "8",
                "Salvatori Computer Science Center",
                "09:09",
                "description of SAL",
                5,
                timeSlots
        );
        buildings.add(SAL);

        Building DML = new Building(
                "9",
                "Doheny Library",
                "09:09",
                "description of DML",
                5,
                timeSlots
        );
        buildings.add(DML);

        Building GFS = new Building(
                "10",
                "Grace Ford Salvatori Hall",
                "09:09",
                "description of GFS",
                5,
                timeSlots
        );
        buildings.add(GFS);

        for(int i = 0; i < buildings.size(); i++){
            Building curr = buildings.elementAt(i);
            mDatabase.child("buildings").child(curr.getName()).setValue(curr);
        }


        // Create an instance of Fragment1 which is our map view, display this on start
        Fragment fragment1 = new MapView();

        // Begin a fragment transaction
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment1)  // Replace the default fragment with Fragment1
                .commit();

        firstFragmentBtn = findViewById(R.id.fragment1btn);
        secondFragmentBtn = findViewById(R.id.fragment2btn);

        firstFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                replaceFragment(new MapView());

            }
        });

        secondFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // Check if user is logged in
                if (loggedIn) {
                    // User is logged in, show profile fragment
                    replaceFragment(new ProfileView());
                } else {
                    // User is not logged in, show login fragment
                    replaceFragment(new Login());
                }
//                // Check if user is logged in
//                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//                    // User is logged in, show profile fragment
//                    replaceFragment(new ProfileView());
//                } else {
//                    // User is not logged in, show login fragment
//                    replaceFragment(new Login());
//                }

                transaction.commit();

            }
        });


    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }

    public Vector<Building> getBuildings() {
        return buildings;
    }

}