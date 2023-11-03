package com.example.takeaseat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookingPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingPage extends Fragment {

    // Existing parameters and variables
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public BookingPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookingPage.
     */
    public static BookingPage newInstance(String param1, String param2) {
        BookingPage fragment = new BookingPage();
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
        View rootView = inflater.inflate(R.layout.fragment_booking_page, container, false);

        // Get the LinearLayout and add the time slots
        LinearLayout timeSlotsLayout = rootView.findViewById(R.id.timeSlotsLayout);

        // Add time slots from 8:00 AM to 5:00 PM with 30-minute intervals
        for (int hour = 8; hour < 17; hour++) {
            for (int minute = 0; minute < 60; minute += 30) {
                String time = String.format("%02d:%02d", hour, minute);
                TextView timeSlotTextView = new TextView(getContext());
                timeSlotTextView.setText(time);
                timeSlotsLayout.addView(timeSlotTextView);
            }
        }

        return rootView;
    }
}
