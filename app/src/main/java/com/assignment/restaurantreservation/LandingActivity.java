package com.assignment.restaurantreservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LandingActivity extends AppCompatActivity {


    private TextView signuptext, customerPage;
    EditText _username, _password;
    Button LoginButton, RegisterButton, AnonymousButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        _username = findViewById(R.id._username);
        _password = findViewById(R.id._password);
        LoginButton = findViewById(R.id.LoginButton1);
        RegisterButton = findViewById(R.id.RegisterButton);
        AnonymousButton = findViewById(R.id.AnonymousButton);

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

        AnonymousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnonymousOpen();
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
