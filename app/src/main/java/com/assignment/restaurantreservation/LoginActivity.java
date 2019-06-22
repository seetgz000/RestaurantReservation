package com.assignment.restaurantreservation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    CardView LoginButton;
    EditText username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        LoginButton = findViewById(R.id.LoginButton2);
        username = findViewById(R.id._username);
        password = findViewById(R.id._password);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthorizedOpen();
            }
        });
    }

    public void AuthorizedOpen() {

        String username_input = username.getText().toString();
        String password_input = password.getText().toString();

        if (is_empty(username_input)) {
            username.requestFocus();
            username.setError("Name cannot be empty");
            return;
        }
        if (is_empty(password_input)){
            password.requestFocus();
            password.setError("Password cannot be empty");
            return;
        }

        //empty both text view after user log in
        password.setText("");
        username.setText("");

        //go to main reservation page
        Intent intent = new Intent(this, MakeReservationActivity.class);
        startActivity(intent);
    }

    private boolean is_empty(String string){
        boolean return_status =  false;
        if (string.length() == 0 ){
            return_status = true;
        }
        return return_status;
    }

}
