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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditProfile extends Fragment {

    private FirebaseAuth auth;
    private EditText email,password,confirmPassword;
    private Button editProfileBtn;

    int updated_email = 0;
    int updated_password = 0;


    Boolean same_password = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_edit_profile, null);

        email = rootView.findViewById(R.id._email);
        password = rootView.findViewById(R.id._password);
        confirmPassword = rootView.findViewById(R.id._confirmPassword);
        editProfileBtn = rootView.findViewById(R.id.btn_EditProfileSave);

        auth = FirebaseAuth.getInstance();

        editProfileBtn.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });

        return rootView;
    }



    private void editProfile(){
        updated_email = 0;
        updated_password = 0;

        final String email_input = email.getText().toString();
        final String password_input = password.getText().toString();
        String confirm_pass_input = confirmPassword.getText().toString();


        FirebaseUser user = auth.getCurrentUser();

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


}//end class
