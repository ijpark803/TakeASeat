package com.example.takeaseat;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Register#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Register extends Fragment {

    //Initialize parameters
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private EditText name, email, password, affiliation, id;
    public Uri photourl;
    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    public MainActivity ma;

    public ActivityResultLauncher<Intent> pickImageLauncher;
    public StorageReference storageReference;

    public Register() {
        // Constructor
    }

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
        storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseApp.initializeApp(getContext());

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

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        photourl = imageUri;
                    }
                });


        Button uploadButton = view.findViewById(R.id.btnUpload);
        uploadButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });



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
        HashMap<String, Boolean> reservations = new HashMap<>();
        User user = new User(
                email.getText().toString(),
                name.getText().toString(),
                password.getText().toString(),
                false,
                id.getText().toString(),
                affiliation.getText().toString(),
                reservations,
                photourl.toString()
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