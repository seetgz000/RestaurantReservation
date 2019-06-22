package com.assignment.restaurantreservation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.assignment.restaurantreservation.models.Reservation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    private CardView LoginButton;
    private EditText username,password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        LoginButton = findViewById(R.id.LoginButton2);
        username = findViewById(R.id._username);
        password = findViewById(R.id._password);

        //if user already logged in
        authListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                if(firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                }
            }
        };

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthorizedOpen();
            }
        });
    }// end onCreate

    public void AuthorizedOpen() {
        super.onStart();
        // auth.addAuthStateListener(authListener);

        String username_input = username.getText().toString();
        String password_input = password.getText().toString();
        Log.d("EMail",username_input);
        Log.d("Passpword:",password_input);

        if (username_input.isEmpty()) {
            username.requestFocus();
            username.setError("Name cannot be empty");
            return;
        }
        else if (password_input.isEmpty()){
            password.requestFocus();
            password.setError("Password cannot be empty");
            return;
        }
        else {
            auth.signInWithEmailAndPassword(username_input, password_input).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Sign in problem", Toast.LENGTH_LONG).show();
                    }
                    else{
                        //go to main reservation page
                        Intent intent = new Intent(LoginActivity.this, MakeReservationActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

        //empty both text view after user log in
        password.setText("");
        username.setText("");

    }//end authorizedOpen



}
