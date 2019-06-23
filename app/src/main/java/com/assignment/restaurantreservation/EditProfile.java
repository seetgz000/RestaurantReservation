package com.assignment.restaurantreservation;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.assignment.restaurantreservation.models.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import org.w3c.dom.Document;

import java.util.Date;

public class EditProfile extends Fragment {

    private static final String TAG = "EditProfile";

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseFirestore mFirestore;
    private FirebaseUser user;
    private EditText email,password,confirmPassword, fullName, phoneNumber;
    private Button editProfileBtn;

    int updated_email = 0;
    int updated_password = 0;


    Boolean same_password = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_edit_profile, null);


        editProfileBtn = view.findViewById(R.id.btn_EditProfileSave);
        fullName = view.findViewById(R.id._fullnameEP);
        email = view.findViewById(R.id._emailEP);
        password = view.findViewById(R.id._passwordEP);
        confirmPassword = view.findViewById(R.id._confirmPasswordEP);
        phoneNumber = view.findViewById(R.id._phoneNumberEP);


        auth = FirebaseAuth.getInstance();
        initFirestore();

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

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });


        user = auth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            String userID = user.getUid();

            mFirestore.collection("accounts").whereEqualTo("account_id", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                fullName.setText(document.getString("customer_name"));
                                email.setText(document.getString("email"));
                                phoneNumber.setText(document.getString("mobile_number"));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        } else {
            // No user is signed in
            Log.d(TAG, "Please Sign In to keep your information");
        }



        return view;
    }

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();
    }

    private void editProfile(){
        super.onStart();
        //auth.addAuthStateListener(authListener);

        updated_email = 0;
        updated_password = 0;

        final String full_name = fullName.getText().toString();
        final String phone_number = phoneNumber.getText().toString();
        final String email_input = email.getText().toString();
        final String password_input = password.getText().toString();
        String confirm_pass_input = confirmPassword.getText().toString();

        FirebaseUser user = auth.getCurrentUser();

        if (full_name.isEmpty() || !isValidName(full_name)) {
            fullName.requestFocus();
            if (full_name.isEmpty()) {fullName.setError("Name cannot be empty");}
            else if (  ! isValidName(full_name)){fullName.setError("Invalid name, only alphabets are allowed.");}
        }else if (phone_number.isEmpty() || phone_number.contains(" ") || ! isValidNumber(email_input)) {
            phoneNumber.requestFocus();

            if (phone_number.isEmpty()) {phoneNumber.setError("Phone number cannot be empty");}
            else if (phone_number.contains(" ")){phoneNumber.setError("Spaces are not allowed.");}
            else if ( ! isValidNumber(phone_number)){phoneNumber.setError("Invalid phone number");}
        }
        //email validation
        if (! email_input.isEmpty()) {
            if (!isValidEmail(email_input)) {
                email.requestFocus();
                email.setError("Invalid email");
            } else if (email_input.contains(" ")) {
                email.requestFocus();
                email.setError("Spaces are not allowed.");
            } else {
                updated_email =1;
                user.updateEmail(email_input)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getActivity(),"Profile updated",Toast.LENGTH_LONG).show();

                                if(updated_password == 0){
                                    Toast.makeText(getActivity(),"Email updated",Toast.LENGTH_LONG).show();
                                }
                                if(password_input.isEmpty() || updated_password == 1){
                                    goHome();
                                }
                            }
                            else{
                                email.requestFocus();
                                email.setError("Invalid email.");
                            }
                        }
                    });
            }
        }

        if (!password_input.isEmpty()) {
            //password validation
            if (password_input.length() < 6) {
                password.requestFocus();
                password.setError("Password must be  more than 5 characters");
            } else if (password_input.contains(" ")) {
                password.requestFocus();
                password.setError("Spaces are not allowed");
            } else if (confirm_pass_input.isEmpty() || !confirm_pass_input.equals(password_input)
            ) {
                confirmPassword.requestFocus();
                confirmPassword.setError("Confirmation password does not match.");
            } else {
                updated_password = 1;
                user.updatePassword(password_input)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(),"Profile updated",Toast.LENGTH_LONG).show();

                                if(updated_email == 0 ){
                                    Toast.makeText(getActivity(),"Password updated",Toast.LENGTH_LONG).show();
                                }
                                if (email_input.isEmpty() || updated_email == 1){
                                    goHome();
                                }
                            }
                            else{
                                password.requestFocus();
                                password.setError("Invalid password.");
                            }
                        }
                    });
            }
        }

        // Get a new write batch
        final WriteBatch batch = mFirestore.batch();

        if (user != null) {
            // User is signed in
            String userID = user.getUid();

            mFirestore.collection("accounts").whereEqualTo("account_id", userID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference accountDoc = mFirestore.collection("accounts").document(document.getId());
                                batch.update(accountDoc,"customer_name", full_name);
                                batch.update(accountDoc,"mobile_number", phone_number);
                                batch.update(accountDoc,"email", email_input);
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            batch.commit();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        } else {
            // No user is signed in
            Log.d(TAG, "Please Sign In to keep your information");
        }
//        goHome();


    }//end editProfile

    private boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }//end isValidEmailAddress

    private void goHome(){
        FragmentTransaction fr = getFragmentManager().beginTransaction();
        fr.replace(R.id.fragment_container, new HomeFragment());
        fr.addToBackStack(null);
        fr.commit();
    }

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
