package com.assignment.restaurantreservation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.restaurantreservation.models.Reservation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    private CardView loginButton;
    private EditText email,password;
    private TextView forgotPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        loginButton = findViewById(R.id.LoginButton2);
        email = findViewById(R.id._email);
        password = findViewById(R.id._password);
        forgotPass = findViewById(R.id._forgotPassword);

        auth = FirebaseAuth.getInstance();

        //if user already logged in
        authListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                //signed in
                if(firebaseAuth.getCurrentUser() == null){
                    Toast.makeText(LoginActivity.this, "Internet connection unavailable.", Toast.LENGTH_LONG).show();
                }
            }
        };

        // login feature
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthorizedOpen();
            }
        });

        //if user forgot password
        forgotPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });

    }// end onCreate


    private void AuthorizedOpen() {
        super.onStart();
        //auth.addAuthStateListener(authListener);

        String email_input = email.getText().toString();
        String password_input = password.getText().toString();

        if (email_input.isEmpty() || email_input.contains(" ") || ! isValidEmail(email_input)) {
            email.requestFocus();

            if (email_input.isEmpty()) {email.setError("Email field cannot be empty");}
            else if (email_input.contains(" ")){email.setError("Spaces are not allowed.");}
            else if ( ! isValidEmail(email_input)){email.setError("Invalid email");}
        }
        else if (password_input.isEmpty() || password_input.contains(" ")){
            password.requestFocus();

            if (password_input.isEmpty()){password.setError("Password field cannot be empty");}
            else if (password_input.contains(" ")){password.setError("Spaces are not allowed");}
        }
        else {
            auth.signInWithEmailAndPassword(email_input, password_input).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Invalid account, please try again.", Toast.LENGTH_LONG).show();
                    }
                    else{
                        //go to main reservation page
                        startActivity(new Intent(LoginActivity.this, MakeReservationActivity.class));
                        //empty both text view after user log in
                        password.setText("");
                        email.setText("");
                    }
                }
            });
        }

    }//end authorizedOpen

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }//end isValidEmailAddress



}
