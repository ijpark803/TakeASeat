package com.example.takeaseat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileView extends Fragment {

    View view;
    public MainActivity ma;
    TextView name, affiliation, id;

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
        return view;
    }
}