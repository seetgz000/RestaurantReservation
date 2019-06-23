package com.assignment.restaurantreservation;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.restaurantreservation.models.Reservation;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReservationFragment extends Fragment
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "Reservation";
    private static final String REQUIRED = "Required";

    // [START declare_database_ref]
    private FirebaseFirestore mFirestore;
    // [END declare_database_ref]

    Button btnDatepick, btn_submit;
    TextView mDateReserve;
    Spinner mPreferredSeat, mNumSeat, mTimeReserve;
    EditText mComment;

    String Time [] = {"10:00 AM","10:30 AM","11:00 AM","11:30 AM","12:00 PM","12:30 AM","1:00 PM","1:30 PM"
                                ,"2:00 PM","2:30 PM","3:00 PM","3:30 PM","4:00 PM","4:30 PM","5:00 PM","5:30 PM","6:00 PM","6:30 PM"
                                ,"7:00 PM","7:30 PM","8:00 PM","8:30 PM","9:00 PM","9:30 PM"};
    String Noofseat[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
    String Prefseatdropdown[] = {"None", "Corner","Near Washroom","Near Window","Near Entrance"};
    ArrayAdapter<String> myadapter;
    int day, month, year, hour, minute;
    int day_x, month_x, year_x, hour_x, minute_x;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_reservation, null);

        mDateReserve = view.findViewById(R.id.date_reserve);
        mComment = view.findViewById(R.id.Comment);

        view.findViewById(R.id.btnSubmitReservation).setOnClickListener(this);

        //Date Picker Button
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

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DAY_OF_MONTH);

        mDateReserve.setText(day + "/" + month + "/" + year);

        //Time drop down
        mTimeReserve = view.findViewById(R.id._fullnameEP);
        myadapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,Time);
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTimeReserve.setAdapter(myadapter);

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

        // Initialize a new GradientDrawable instance
        GradientDrawable gd = new GradientDrawable();
        // Set the gradient drawable background to transparent
        gd.setColor(Color.parseColor("#00ffffff"));
        // Set a border for the gradient drawable
        gd.setStroke(2,Color.BLACK);
        // Finally, apply the gradient drawable to the edit text background
        mComment.setBackground(gd);

        //submit button
        btn_submit = view.findViewById(R.id.btnSubmitReservation);


        initFirestore();

        return view;
    }

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        year_x = year;
        month_x = month + 1;
        day_x = dayOfMonth;
        mDateReserve.setText(day_x + "/" + month_x + "/" + year_x);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmitReservation:

                ReservationConfirm RC = new ReservationConfirm();

                String date = mDateReserve.getText().toString();
                String time = mTimeReserve.getSelectedItem().toString();
                String seat = mNumSeat.getSelectedItem().toString();

                Bundle bundle = new Bundle();
                bundle.putString("DATE",date);
                bundle.putString("TIME",time);
                bundle.putString("SEAT",seat);
                RC.setArguments(bundle);

                        FragmentTransaction fr = getFragmentManager().beginTransaction();
                        fr.replace(R.id.fragment_container, RC);
                fr.addToBackStack(null);
                        fr.commit();

                break;
        }
    }

    private void onSubmitClicked(View view) {

//        FirebaseUser account = FirebaseAuth.getInstance().getCurrentUser();
        String valueNumSeat = mNumSeat.getSelectedItem().toString();
        int numSeat = Integer.parseInt(valueNumSeat);
        String seat_type = mPreferredSeat.getSelectedItem().toString();
        String date = mDateReserve.getText().toString();
        String time = mTimeReserve.getSelectedItem().toString();
        String comment = mComment.getText().toString();

        checkSeat(numSeat, date, time);

        Reservation reservation = new Reservation("TestID", numSeat, seat_type , date, time, comment, new Timestamp(new Date()), false);
        mFirestore.collection("reservations")
                .add(reservation);

        if (mDateReserve.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Please pick a date", Toast.LENGTH_LONG).show();
        }

    }

    private void checkSeat (int numSeat, String date, String time) {

        mFirestore.collection("seats").document(date + "_" +time).get();

        Map<String, Object> seatData = new HashMap<>();
        seatData.put("available_seat", 40);
        seatData.put("update_time", new Timestamp(new Date()));
        mFirestore.collection("seats").document(date + "_" +time)
                .set(seatData);
    }
}
