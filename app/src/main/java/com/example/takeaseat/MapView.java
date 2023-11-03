package com.example.takeaseat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.maps.android.ui.IconGenerator;

import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class MapView extends Fragment implements OnMapReadyCallback {

    View view;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    public MainActivity ma;
    public Vector<Integer> SeatsAvail;

    public Vector<Building> buildings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        buildings = ["WAS", ]
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.fragment_map_view, container, false);

        // Initialize map fragment
        SupportMapFragment supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.maps);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng ll = new LatLng(34.021596, -118.286369);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ll, 16));

        // code to get seat number available from each building from firebase - broken right now
//        SeatsAvail = new Vector<Integer>();
//            for (int i = 1; i <= 10; i++) {
//            mDatabase.child("buildings").child(Integer.toString(i)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<DataSnapshot> task) {
//                    if (task.isSuccessful()) {
//                        DataSnapshot snapshot = task.getResult();
//                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                        Building temp = task.getResult().getValue(Building.class);
//                        SeatsAvail.add(temp.getTotalSeats());
//                    } else {
//                        // Handle the error
//                        Log.e("firebase", "Error getting data", task.getException());
//
//                    }
//                }
//            });
//        }


        IconGenerator iconGenerator = new IconGenerator(getContext());
        Bitmap annen = iconGenerator.makeIcon("Annenberg | ");

        LatLng annenberg = new LatLng(34.02086719918009, -118.28700277916016);
        googleMap.addMarker(new MarkerOptions()
                .position(annenberg)
                .title("Annenberg")
                .icon(BitmapDescriptorFactory.fromBitmap(annen)));

        LatLng viterbi = new LatLng(34.020723787589866, -118.28948746114057);
        googleMap.addMarker(new MarkerOptions()
                .position(viterbi)
                .title("Viterbi"));

        LatLng leavey = new LatLng(34.02135997615666, -118.28399659246597);
        googleMap.addMarker(new MarkerOptions()
                .position(leavey)
                .title("Leavey"));

        LatLng jff = new LatLng(34.01878218780673, -118.28239699277154);
        googleMap.addMarker(new MarkerOptions()
                .position(jff)
                .title("Fertitta Hall"));

        LatLng vpd = new LatLng(34.01899061599517, -118.28389231584627);
        googleMap.addMarker(new MarkerOptions()
                .position(vpd)
                .title("VPD"));

        LatLng rtcc = new LatLng(34.020397492896734, -118.28634410686136);
        googleMap.addMarker(new MarkerOptions()
                .position(rtcc)
                .title("RTCC"));

        LatLng science = new LatLng(34.01965771721866, -118.28880617108945);
        googleMap.addMarker(new MarkerOptions()
                .position(science)
                .title("Science Library"));

        LatLng sal = new LatLng(34.01955967359724, -118.28953071680795);
        googleMap.addMarker(new MarkerOptions()
                .position(sal)
                .title("SAL"));

        LatLng doheny = new LatLng(34.020204513244394, -118.28369019521888);
        googleMap.addMarker(new MarkerOptions()
                .position(doheny)
                .title("Doheny"));

        LatLng gfs = new LatLng(34.02139310725714, -118.28801391813457);
        googleMap.addMarker(new MarkerOptions()
                .position(gfs)
                .title("GFS"));

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.maps, new BookingPage());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;
            }
        });
    }

    public void addBuilding(Building building){

    }

}