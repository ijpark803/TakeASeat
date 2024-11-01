package com.example.takeaseat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    // First fragment button is map button
    // Second fragment button is profile button
    Button firstFragmentBtn, secondFragmentBtn;
    public User currentUser;
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

        //Populate the database if not already done
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
                "Wallis Annenberg Hall blends tradition with technology. Incoming students, faculty and staff in the public relations, journalism and communications programs will have access to fast wifi, a converged media center, a café and meeting and seating pods.",
                10,
                timeSlots
        );
        buildings.add(WAS);
        Building OHE = new Building(
                "2",
                "Olin Hall of Engineering",
                "09:09",
                "The Olin Hall of Engineering is a complex of structures tied together by elevated exterior corridors, plazas, and lush landscaping. Designed in 1963, Olin Hall is the first building Architect William Pereira designed at USC and reflects his belief in a strong indoor-outdoor relationship.",
                10,
                timeSlots
        );
        buildings.add(OHE);

        Building LVL = new Building(
                "3",
                "Leavey Library",
                "09:09",
                "Leavey Library provides a state-of-the-art learning environment. Leavey’s print and electronic collections, services, and technology are designed to facilitate coursework, studying, and research. Experts are available 24 hours a day to provide assistance with research and computing.",
                10,
                timeSlots
        );
        buildings.add(LVL);

        Building JFF = new Building(
                "4",
                "Fertitta Hall",
                "09:09",
                "Fertitta Hall provides significant outdoor space via an outdoor courtyard as well as gathering spaces outside on Childs Way for the students to meet, interact, and study. The building is certified LEED Gold, reinforcing the social responsibility mission of the school.",
                10,
                timeSlots
        );
        buildings.add(JFF);

        Building VPD = new Building(
                "5",
                "Verna and Peter Dauterive Hall",
                "09:09",
                "USC's first interdisciplinary social sciences building is a place where faculty and students from across the university come together to tackle the most pressing social problems affecting our region and our global community.",
                10,
                timeSlots
        );
        buildings.add(VPD);

        Building RTCC = new Building(
                "6",
                "Ronald Tutor",
                "09:09",
                "The Ronald Tutor Campus Center is a six-level institutional facility. It is a mixed-use building consisting of meeting rooms, classrooms, administration and food services. The exterior of the building has a campus focal point with classic stone, grand staircase and fountains.",
                10,
                timeSlots
        );
        buildings.add(RTCC);

        Building SEL = new Building(
                "7",
                "Science and Engineering Library",
                "09:09",
                "The Science & Engineering Library provides essential support to students in the life sciences, physical sciences, earth sciences, mathematics, and engineering. The library holds 70,000 bound journals on the second floor and approximately 80,000 books on its third floor.",
                10,
                timeSlots
        );
        buildings.add(SEL);

        Building SAL = new Building(
                "8",
                "Salvatori Computer Science Center",
                "09:09",
                "The Salvatori Computer Science Center is a study spot for engineering students that now provides newer computer technology and improved study spaces for students after the university and Information Technology Services upgraded the computer lab this summer.",
                10,
                timeSlots
        );
        buildings.add(SAL);

        Building DML = new Building(
                "9",
                "Doheny Library",
                "09:09",
                "Doheny Jr. Memorial Library has served as an intellectual center for generations of students, faculty and staff since it opened in 1932. In addition to housing a vast book and journal collection, Doheny also has spaces for studying, collaborative work, and special events.",
                10,
                timeSlots
        );
        buildings.add(DML);

        Building GFS = new Building(
                "10",
                "Grace Ford Salvatori Hall",
                "09:09",
                "A hub for the USC College of Letters, Arts and Sciences, this building is home to many entry-level classes and discussion groups.",
                10,
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

    public void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }

    public Vector<Building> getBuildings() {
        return buildings;
    }

}