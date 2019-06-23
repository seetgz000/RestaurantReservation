package com.assignment.restaurantreservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private FirebaseAuth auth;

    private EditText email;
    private Button sendPassBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_layout);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id._email);
        sendPassBtn = findViewById(R.id._resetPasswordBtn);

        sendPassBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                sendPass();
            }
        });

    }//end onCreate

    //send email to reset password
    private void sendPass(){

        String email_input = email.getText().toString();

        if (email_input.isEmpty() || email_input.contains(" ") || ! isValidEmail(email_input)) {
            email.requestFocus();

            if (email_input.isEmpty()) {email.setError("Email field cannot be empty");}
            else if (email_input.contains(" ")){email.setError("Spaces are not allowed.");}
            else if ( ! isValidEmail(email_input)){email.setError("Invalid email");}
        }
        else{
            auth.sendPasswordResetEmail(email_input)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForgotPassword.this,"Email sent successfully.", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ForgotPassword.this, LandingActivity.class));
                            }
                            else{
                                Toast.makeText(ForgotPassword.this,"Failed to send email.", Toast.LENGTH_LONG).show();
                                email.requestFocus();
                                email.setError("Either invalid email or typo.");
                            }
                        }
                    });
        }
    }//end sendPass

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }//end isValidEmailAddress



}
