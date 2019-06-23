package com.assignment.restaurantreservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.assignment.restaurantreservation.models.Account;
import com.assignment.restaurantreservation.models.Reservation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseFirestore mFirestore;

    private EditText fullName,email,password,confirmPassword,mobileNo;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        registerBtn = findViewById(R.id.CreateAccount);
        email = findViewById(R.id._email);
        password = findViewById(R.id._password);
        confirmPassword = findViewById(R.id._confirmPassword);
        fullName = findViewById(R.id._fullName);
        mobileNo = findViewById(R.id._phoneNumber);

        auth = FirebaseAuth.getInstance();

        //if user already logged in
        authListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                //signed in
                if(firebaseAuth.getCurrentUser() == null){
                    Toast.makeText(RegisterActivity.this, "Internet connection unavailable.", Toast.LENGTH_LONG).show();
                }
            }
        };

        initFirestore();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });

    }//end onCreate

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();
    }

    public void registerAccount(){
        super.onStart();
        //auth.addAuthStateListener(authListener);


        String email_input = email.getText().toString();
        String password_input = password.getText().toString();
        String confirm_pass_input = confirmPassword.getText().toString();

        if (email_input.isEmpty() || email_input.contains(" ") || ! isValidEmail(email_input)) {
            email.requestFocus();

            if (email_input.isEmpty()) {email.setError("Email field cannot be empty");}
            else if (email_input.contains(" ")){email.setError("Spaces are not allowed.");}
            else if ( ! isValidEmail(email_input)){email.setError("Invalid email");}
        }

        else if (password_input.isEmpty() || password_input.contains(" ")
                || password_input.length() < 6){
            password.requestFocus();

            if (password_input.isEmpty()){password.setError("Password field cannot be empty");}
            else if (password_input.contains(" ")){password.setError("Spaces are not allowed");}
            else if (password_input.length()<6){password.setError("Password should be more than 6 characters");}
        }

        else if( confirm_pass_input.isEmpty() || ! confirm_pass_input.equals(password_input)  ){
            confirmPassword.requestFocus();
            confirmPassword.setError("Password entered is not the same.");
        }
        else {
            auth.createUserWithEmailAndPassword(email_input, password_input).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String fullName_input = fullName.getText().toString();
                        String email_input = email.getText().toString();
                        String mobileNo_input = mobileNo.getText().toString();

                        Account account = new Account(user, fullName_input, email_input , mobileNo_input, new Timestamp(new Date()), false);
                        mFirestore.collection("accounts")
                                .add(account);
                        //go to main reservation page
                        startActivity(new Intent(RegisterActivity.this, MakeReservationActivity.class));
                        //empty both text view after user log in
                        password.setText("");
                        email.setText("");
                    } else {
                        Toast.makeText(RegisterActivity.this, "Invalid account, please try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        }
    }//end registerAccount


    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }//end isValidEmailAddress


}//end class

