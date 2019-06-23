package com.assignment.restaurantreservation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AccountFragment extends Fragment{

    private static final String TAG = "AccountFrangment";

    private TextView EditProfileButton, ViewCustomerName;
    private FirebaseAuth auth;
    private FirebaseFirestore mFirestore;
    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_account, null);

        ViewCustomerName = rootView.findViewById(R.id.customerName_View);
        EditProfileButton = rootView.findViewById(R.id.btnEditProfile);

        EditProfileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment_container, new EditProfile());
                fr.addToBackStack(null);
                fr.commit();

            }
        });

        initFirestore();

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
                                    ViewCustomerName.setText(document.getString("customer_name"));
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

        return rootView;
    }

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();
    }
}
