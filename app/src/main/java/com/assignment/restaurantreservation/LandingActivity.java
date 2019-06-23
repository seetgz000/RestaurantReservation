package com.assignment.restaurantreservation;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class LandingActivity extends AppCompatActivity {


    private TextView signuptext, customerPage;
    EditText _email, _password;
    Button LoginButton, RegisterButton, AnonymousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        _email = findViewById(R.id._email);
        _password = findViewById(R.id._password);
        LoginButton = findViewById(R.id.LoginButton1);
        RegisterButton = findViewById(R.id.RegisterButton);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogin();
            }
        });
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignup();
            }
        });

    }

    public void openSignup() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void openLogin() {
//        Intent intent = new Intent(this, LoginActivity.class);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void AnonymousOpen() {
        Intent intent = new Intent(this, MakeReservationActivity.class);
        startActivity(intent);
    }
}
