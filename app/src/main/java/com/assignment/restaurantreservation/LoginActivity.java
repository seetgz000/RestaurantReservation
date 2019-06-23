package com.assignment.restaurantreservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment.restaurantreservation.models.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseFirestore mFirestore;
    private FirebaseUser user;

    private Button loginButton;
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

        initFirestore();

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

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();
    }

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
                        user = auth.getInstance().getCurrentUser();
                        String userID = user.getUid();

                        mFirestore.collection("accounts").whereEqualTo("account_id", userID)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            mFirestore.collection("accounts").document(document.getId())
                                                    .update("last_login_time", new Timestamp(new Date()));
                                            Log.d(TAG, document.getId() + " => " + document.getData());
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });
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
