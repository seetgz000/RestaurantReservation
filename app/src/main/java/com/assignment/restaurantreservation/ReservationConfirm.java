package com.assignment.restaurantreservation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ReservationConfirm extends Fragment {

    private TextView showdate, showtime, showseat;
    private Button confirm, cancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_reservationconfirm, null);

        showdate = view.findViewById(R.id.showdate);
        showtime = view.findViewById(R.id.showtime);
        showseat = view.findViewById(R.id.showseat);

        Bundle bundle = getArguments();

        String SHOWDATE = bundle.getString("DATE");
        String SHOWTIME = bundle.getString("TIME");
        String SHOWSEAT = bundle.getString("SEAT");

        showdate.setText(SHOWDATE);
        showtime.setText(SHOWTIME);
        showseat.setText(SHOWSEAT);

        confirm = view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

        cancel = view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });



        return view;
    }
}
