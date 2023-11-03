package com.example.takeaseat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileView extends Fragment {

    View view;
    public MainActivity ma;
    TextView name, affiliation, id;

    private FirebaseAuth mAuth;
    private Button logoutbtn;

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
        // on create display name, affiliation, and id of current user
        name.setText(ma.currentUser.name);
        affiliation.setText(ma.currentUser.uscAffiliation);
        id.setText(ma.currentUser.uscID);

        logoutbtn = view.findViewById(R.id.logoutbtn);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                ma.loggedIn = false;
                replaceFragment(new Login());
<<<<<<< Updated upstream
                ma.finish();
=======

>>>>>>> Stashed changes
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