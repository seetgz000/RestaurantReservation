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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;


import java.util.Calendar;


public class ReservationFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    String Noofseat[] = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
    String Prefseatdropdown[] = {" ","Corner","Near bathroom","View","Sofa"};
    String hehe = "hest";
    ArrayAdapter<String>myadapter;
    Button datepick, timepick;
    EditText datepickshow, timepickshow;
    int day, month, year, hour, minute;
    int day_x, month_x, year_x, hour_x, minute_x;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_reservation, null);

        datepickshow = view.findViewById(R.id.DatePickShow);
        timepickshow = view.findViewById(R.id.TimePickShow);


        datepick = view.findViewById(R.id.DatePick);
        datepick.setOnClickListener(new View.OnClickListener() {
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

        timepick = view.findViewById(R.id.TimePick);
        timepick.setOnClickListener(new View.OnClickListener() {
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
        Spinner spinner1  = view.findViewById(R.id.Spinner1);
        myadapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,Noofseat);
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(myadapter);


        //prefered seat drop down
        Spinner spinner2  = view.findViewById(R.id.Spinner2);
        myadapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,Prefseatdropdown);
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(myadapter);

        return view;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        year_x = year;
        month_x = month + 1;
        day_x = dayOfMonth;
        datepickshow.setText(day_x + "/" + month_x + "/" + year_x);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hour_x = hourOfDay;
        minute_x = minute;
        timepickshow.setText(hourOfDay + ":" + minute_x);

    }

}

