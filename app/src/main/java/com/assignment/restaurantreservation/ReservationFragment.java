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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;


public class ReservationFragment extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText datepick, timepick;
    int day, month, year, hour, minute;
    int day_x, month_x, year_x, hour_x, minute_x;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_reservation, null);

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

        return view;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        year_x = year;
        month_x = month + 1;
        day_x = dayOfMonth;
        datepick.setText(day_x + "/" + month_x + "/" + year_x);

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hour_x = hourOfDay;
        minute_x = minute;
        timepick.setText(hourOfDay + ":" + minute_x);

    }

}

