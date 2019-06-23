package com.assignment.restaurantreservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class EditProfile extends Fragment {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    private EditText fullName, email, password, confirmPassword, phoneNumber;
    private Button btn_save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_edit_profile, null);


        btn_save = view.findViewById(R.id.btn_EditProfileSave);
        fullName = view.findViewById(R.id._fullnameEP);
        email = view.findViewById(R.id._emailEP);
        password = view.findViewById(R.id._passwordEP);
        confirmPassword = view.findViewById(R.id._confirmPasswordEP);
        phoneNumber = view.findViewById(R.id._phoneNumberEP);


        auth = FirebaseAuth.getInstance();

        //if user already logged in
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //signed in
                if (firebaseAuth.getCurrentUser() == null) {
                    Toast.makeText(getContext(), "Internet connection unavailable.", Toast.LENGTH_LONG).show();
                }
            }
        };

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });

        return view;
    }


        public void registerAccount(){
            super.onStart();
            //auth.addAuthStateListener(authListener);

            String full_name_input = fullName.getText().toString();
            String email_input = email.getText().toString();
            String password_input = password.getText().toString();
            String confirm_pass_input = confirmPassword.getText().toString();
            String phone_number_input = phoneNumber.getText().toString();


            if (full_name_input.isEmpty() || !isValidName(full_name_input)) {
                fullName.requestFocus();
                if (full_name_input.isEmpty()) {fullName.setError("Name cannot be empty");}
                else if (  ! isValidName(full_name_input)){fullName.setError("Invalid name, only alphabets are allowed.");}
            }


            else if (email_input.isEmpty() || email_input.contains(" ") || ! isValidEmail(email_input)) {
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

            else if (phone_number_input.isEmpty() || phone_number_input.contains(" ") || ! isValidNumber(email_input)) {
                phoneNumber.requestFocus();

                if (phone_number_input.isEmpty()) {phoneNumber.setError("Phone number cannot be empty");}
                else if (phone_number_input.contains(" ")){phoneNumber.setError("Spaces are not allowed.");}
                else if ( ! isValidNumber(phone_number_input)){phoneNumber.setError("Invalid phone number");}
            }
            else {
                auth.createUserWithEmailAndPassword(email_input, password_input).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //go to main reservation page
                            startActivity(new Intent(getContext(), MakeReservationActivity.class));
                            //empty both text view after user log in
                            password.setText("");
                            email.setText("");
                        } else {
                            Toast.makeText(getContext(), "Invalid account, please try again.", Toast.LENGTH_LONG).show();
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

        private boolean isValidName(String fullname) {
            String regex = "[A-Za-z ]+";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
            java.util.regex.Matcher m = p.matcher(fullname);
            return m.matches();
        }//end isValidName

        private boolean isValidNumber(String fullname) {
            String regex = "[0-9]+";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
            java.util.regex.Matcher m = p.matcher(fullname);
            return m.matches();
        }//end isValidNumber


    }//end class

