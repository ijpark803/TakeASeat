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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Login extends Fragment {

    // Initialize parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private DatabaseReference mDatabase;
    public MainActivity ma;

    EditText email, password;
    Button registerbtn, loginbtn;

    public Login() {
        // Constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */

    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        registerbtn = view.findViewById(R.id.registerbtn);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                replaceFragment(new Register());
                transaction.commit();
            }
        });
        ma = (MainActivity) getActivity();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        loginbtn = view.findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();
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
    // Determines if username exists, if so, does password exist, return true only if both are a match in the database
    public void checkUser()
    {
        mDatabase.child("users").child(email.getText().toString().replace(".","_")).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    // If task is unsuccessful, just return to the profile page and make the user do it again
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    replaceFragment(new Login());
                    transaction.commit();
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    // If task is successful, instantiate user class
                    String result = String.valueOf(task.getResult().getValue());
                    User temp = task.getResult().getValue(User.class);
                    ma.currentUser = temp;
                    ma.loggedIn = true;
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    replaceFragment(new ProfileView());
                    transaction.commit();
                }
            }
        });
    }
}