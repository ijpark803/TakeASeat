package com.example.takeaseat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class MapView extends Fragment implements OnMapReadyCallback {

    View view;

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

        LatLng annenberg = new LatLng(34.02086719918009, -118.28700277916016);
        googleMap.addMarker(new MarkerOptions()
                .position(annenberg)
                .title("Marker for Annenberg"));

        LatLng viterbi = new LatLng(34.020723787589866, -118.28948746114057);
        googleMap.addMarker(new MarkerOptions()
                .position(viterbi)
                .title("Marker for Viterbi"));

        LatLng leavey = new LatLng(34.02135997615666, -118.28399659246597);
        googleMap.addMarker(new MarkerOptions()
                .position(leavey)
                .title("Marker for Leavey"));

        LatLng jff = new LatLng(34.01878218780673, -118.28239699277154);
        googleMap.addMarker(new MarkerOptions()
                .position(jff)
                .title("Marker for Fertitta Hall"));

        LatLng vpd = new LatLng(34.01899061599517, -118.28389231584627);
        googleMap.addMarker(new MarkerOptions()
                .position(vpd)
                .title("Marker for VPD"));

        LatLng rtcc = new LatLng(34.020397492896734, -118.28634410686136);
        googleMap.addMarker(new MarkerOptions()
                .position(rtcc)
                .title("Marker for RTCC"));

        LatLng science = new LatLng(34.01965771721866, -118.28880617108945);
        googleMap.addMarker(new MarkerOptions()
                .position(science)
                .title("Marker for Science Library"));

        LatLng sal = new LatLng(34.01955967359724, -118.28953071680795);
        googleMap.addMarker(new MarkerOptions()
                .position(sal)
                .title("Marker for SAL"));

        LatLng doheny = new LatLng(34.020204513244394, -118.28369019521888);
        googleMap.addMarker(new MarkerOptions()
                .position(doheny)
                .title("Marker for Doheny"));

        LatLng gfs = new LatLng(34.02139310725714, -118.28801391813457);
        googleMap.addMarker(new MarkerOptions()
                .position(gfs)
                .title("Marker for GFS"));

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

}