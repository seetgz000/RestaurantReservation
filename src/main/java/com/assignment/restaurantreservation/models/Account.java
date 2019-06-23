package com.assignment.restaurantreservation.models;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

@IgnoreExtraProperties
public class Account {

    private String account_id;
    private String customer_name;
    private String email;
    private String mobile_number;
    private @ServerTimestamp Timestamp last_login_time;
    private int role_id;
    private boolean removed;

    public Account() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Account(FirebaseUser account, String customer_name, String email, String mobile_number, Timestamp last_login_time, int role_id, boolean removed) {
        this.account_id = account.getUid();
        this.customer_name = customer_name;
        this.email = email;
        this.mobile_number = mobile_number;
        this.last_login_time = last_login_time;
        this.role_id = role_id;
        this.removed = removed;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getCustomer_name() { return customer_name;}

    public void setCustomer_name(String customer_name) { this.customer_name = customer_name;}

    public String getEmail() { return email;}

    public void setEmail(String email) { this.email = email;}

    public String getMobile_number() { return mobile_number;}

    public void setMobile_number(String mobile_number) { this.mobile_number = mobile_number;}

    public Timestamp getLast_login_time() {

        return last_login_time;
    }

    public void setLast_login_time(Timestamp last_login_time) {
        this.last_login_time = last_login_time;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public boolean getRemoved() { return removed;}

    public void serRemoved(boolean removed) { this.removed = removed;}

}

