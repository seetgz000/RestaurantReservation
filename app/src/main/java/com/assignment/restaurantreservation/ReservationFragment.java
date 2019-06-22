package com.assignment.restaurantreservation;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.assignment.restaurantreservation.models.Reservation;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;

public class ReservationFragment extends Fragment implements View.OnClickListener,DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener  {

    private static final String TAG = "Reservation";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private FirebaseFirestore mFirestore;
    // [END declare_database_ref]

    Button btnDatepick, btnTimepick;
    TextView mDateReserve, mTimeReserve;
    Spinner mPreferredSeat, mNumSeat;

    String Noofseat[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
    String Prefseatdropdown[] = {"None", "Corner","Near Wathroom","New Window","New Entrance"};
    String hehe = "hest";
    ArrayAdapter<String> myadapter;
    int day, month, year, hour, minute;
    int day_x, month_x, year_x, hour_x, minute_x;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_reservation, null);

        mDateReserve = view.findViewById(R.id.date_reserve);
        mTimeReserve = view.findViewById(R.id.time_reserve);



        view.findViewById(R.id.btnSubmitReservation).setOnClickListener(this);

        btnDatepick = view.findViewById(R.id.DatePick);
        btnDatepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        ReservationFragment.this, year, month, day);
                datePickerDialog.show();
            }
        });

        btnTimepick = view.findViewById(R.id.TimePick);
        btnTimepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR);
                minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        ReservationFragment.this, hour, minute, true);
                timePickerDialog.show();

            }
        });

        //Number seat drop down
        mNumSeat = view.findViewById(R.id.num_seat);
        myadapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,Noofseat);
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mNumSeat.setAdapter(myadapter);


        //prefered seat drop down
        mPreferredSeat = view.findViewById(R.id.preferred_seat);
        myadapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,Prefseatdropdown);
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPreferredSeat.setAdapter(myadapter);

        initFirestore();

        return view;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        year_x = year;
        month_x = month + 1;
        day_x = dayOfMonth;
        mDateReserve.setText(day_x + "/" + month_x + "/" + year_x);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hour_x = hourOfDay;
        minute_x = minute;
        mTimeReserve.setText(hourOfDay + ":" + minute_x);

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

        String valueNumSeat = mNumSeat.getSelectedItem().toString();
        int numSeat = Integer.parseInt(valueNumSeat);
        String seat_type = "Near Window";

        Reservation reservation = new Reservation(numSeat,seat_type , new Timestamp(new Date()));
        mFirestore.collection("reservations")
                .add(reservation);
    }
}
