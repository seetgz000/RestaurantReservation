package com.assignment.restaurantreservation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class HomePageActivity extends AppCompatActivity {

    CardView MakeReservationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        MakeReservationButton = findViewById(R.id.MakeReservationButton);

        MakeReservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakeReservationOpen();
            }
        });

    }
    public void MakeReservationOpen() {
        Intent intent = new Intent(this, MakeReservationActivity.class);
        startActivity(intent);
    }
}
