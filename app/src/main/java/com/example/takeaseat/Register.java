package com.example.takeaseat;

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
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Register extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    // Variables added ****
    private EditText name, email, password, affiliation, id;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    public MainActivity ma;

    public Register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Register.
     */
    // TODO: Rename and change types and number of parameters
    public static Register newInstance(String param1, String param2) {
        Register fragment = new Register();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        // Inflate the layout for this fragment
        name = (EditText) view.findViewById(R.id.name);
        email = (EditText) view.findViewById(R.id.email);
        id = (EditText) view.findViewById(R.id.uscid);
        affiliation = (EditText) view.findViewById(R.id.affiliation);
        password = (EditText) view.findViewById(R.id.password);
        Button registerButton = (Button) view.findViewById(R.id.registerbtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData(v);
            }
        });
        ma = (MainActivity) getActivity();
        return view;
    }

    public void sendData(View view)
    {
        writeNewUser();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        replaceFragment(new ProfileView());
        transaction.commit();
    }

    public void writeNewUser() {
        User user = new User(
                email.getText().toString(),
                name.getText().toString(),
                password.getText().toString(),
                id.getText().toString(),
                affiliation.getText().toString(),
                new HashMap<>()
        );
        ma.currentUser = user;
        mDatabase.child("users").child(email.getText().toString().replace(".","_")).setValue(user);
        // access the main activity and log in a user
        ma.loggedIn = true;
//        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString());
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }
}