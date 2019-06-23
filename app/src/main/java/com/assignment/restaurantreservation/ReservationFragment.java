package com.assignment.restaurantreservation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.assignment.restaurantreservation.models.Reservation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReservationFragment extends Fragment implements View.OnClickListener  {

    private static final String TAG = "Reservation";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private FirebaseFirestore mFirestore;
    // [END declare_database_ref]

    private EditText mNumSeat;
    private EditText mDateReserve, mTimeReserve;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_reservation, null);

        mNumSeat = rootView.findViewById(R.id.num_seat);
        mDateReserve = rootView.findViewById(R.id.date_reserve);
        mTimeReserve = rootView.findViewById(R.id.time_reserve);

        rootView.findViewById(R.id.btnSubmitReservation).setOnClickListener(this);

        initFirestore();

        return rootView;
    }

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmitReservation:
                onSubmitClicked(v);
                break;
        }
    }

    private void onSubmitClicked(View view) {

        String valueNumSeat = mNumSeat.getText().toString();
        int numSeat = Integer.parseInt(valueNumSeat);
        String seat_type = "Near Window";
        String date = mDateReserve.getText().toString();
        String time = mTimeReserve.getText().toString();

        Reservation reservation = new Reservation(numSeat,seat_type , new Timestamp(new Date()), date, time);
        mFirestore.collection("reservations")
                .add(reservation);
    }
}
