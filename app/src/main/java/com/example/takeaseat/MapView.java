package com.example.takeaseat;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
        SeatsAvail = new Vector<Integer>();
        //mDatabase = FirebaseDatabase.getInstance().getReference();
//        buildings = ["WAS", ]
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.fragment_map_view, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();


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

        mDatabase.child("buildings").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot snapshot : task.getResult().getChildren()) {
                        Long seats = snapshot.child("totalSeats").getValue(Long.class);
                        Log.d("Firebase", " Total Seats: " + seats);
                        Integer curr_seats = Math.toIntExact(seats);
                        SeatsAvail.add(curr_seats);
                    }

                } else {
                    Log.e("Firebase", "Error getting data", task.getException());
                }
                IconGenerator iconGenerator = new IconGenerator(getContext());
                Bitmap annen = iconGenerator.makeIcon("Annenberg | " + SeatsAvail.elementAt(0));

                LatLng annenberg = new LatLng(34.02086719918009, -118.28700277916016);
                googleMap.addMarker(new MarkerOptions()
                        .position(annenberg)
                        .title("1")
                        .icon(BitmapDescriptorFactory.fromBitmap(annen)));

                Bitmap vit = iconGenerator.makeIcon("Viterbi | " + SeatsAvail.elementAt(1));
                LatLng viterbi = new LatLng(34.020723787589866, -118.28948746114057);
                googleMap.addMarker(new MarkerOptions()
                        .position(viterbi)
                        .title("2")
                        .icon(BitmapDescriptorFactory.fromBitmap(vit)));

                Bitmap lvl = iconGenerator.makeIcon("Leavey | " + SeatsAvail.elementAt(2));
                LatLng leavey = new LatLng(34.02135997615666, -118.28399659246597);
                googleMap.addMarker(new MarkerOptions()
                        .position(leavey)
                        .title("3")
                        .icon(BitmapDescriptorFactory.fromBitmap(lvl)));

                Bitmap jf = iconGenerator.makeIcon("Fertitta | " + SeatsAvail.elementAt(3));
                LatLng jff = new LatLng(34.01878218780673, -118.28239699277154);
                googleMap.addMarker(new MarkerOptions()
                        .position(jff)
                        .title("4")
                        .icon(BitmapDescriptorFactory.fromBitmap(jf)));

                Bitmap vana = iconGenerator.makeIcon("VPD | " + SeatsAvail.elementAt(4));
                LatLng vpd = new LatLng(34.01899061599517, -118.28389231584627);
                googleMap.addMarker(new MarkerOptions()
                        .position(vpd)
                        .title("5")
                        .icon(BitmapDescriptorFactory.fromBitmap(vana)));

                Bitmap ronald = iconGenerator.makeIcon("RTCC | " + SeatsAvail.elementAt(5));
                LatLng rtcc = new LatLng(34.020397492896734, -118.28634410686136);
                googleMap.addMarker(new MarkerOptions()
                        .position(rtcc)
                        .title("6")
                        .icon(BitmapDescriptorFactory.fromBitmap(ronald)));

                Bitmap sci = iconGenerator.makeIcon("Science Lib | " + SeatsAvail.elementAt(6));
                LatLng science = new LatLng(34.01965771721866, -118.28880617108945);
                googleMap.addMarker(new MarkerOptions()
                        .position(science)
                        .title("7")
                        .icon(BitmapDescriptorFactory.fromBitmap(sci)));

                Bitmap sl = iconGenerator.makeIcon("SAL | " + SeatsAvail.elementAt(7));
                LatLng sal = new LatLng(34.01955967359724, -118.28953071680795);
                googleMap.addMarker(new MarkerOptions()
                        .position(sal)
                        .title("8")
                        .icon(BitmapDescriptorFactory.fromBitmap(sl)));

                Bitmap doh = iconGenerator.makeIcon("Doheny | " + SeatsAvail.elementAt(8));
                LatLng doheny = new LatLng(34.020204513244394, -118.28369019521888);
                googleMap.addMarker(new MarkerOptions()
                        .position(doheny)
                        .title("9")
                        .icon(BitmapDescriptorFactory.fromBitmap(doh)));

                Bitmap g = iconGenerator.makeIcon("GFS | " + SeatsAvail.elementAt(9));
                LatLng gfs = new LatLng(34.02139310725714, -118.28801391813457);
                googleMap.addMarker(new MarkerOptions()
                        .position(gfs)
                        .title("10")
                        .icon(BitmapDescriptorFactory.fromBitmap(g)));
            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = new Bundle();
                bundle.putString("buildingId", marker.getTitle()); // Replace with the actual building name
                Fragment bookingPageFragment = new BookingPage();
                bookingPageFragment.setArguments(bundle);
                replaceFragment(bookingPageFragment);
                return true;
            }
        });
    }

    public void addBuilding(Building building){

    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }

}