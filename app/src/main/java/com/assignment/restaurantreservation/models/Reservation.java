package com.assignment.restaurantreservation.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties
public class Reservation {

    private int account_id;
    private @ServerTimestamp Timestamp created_time;
    private int no_seat;
    private String seat_type;
//    private String comment;

    public Reservation() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Reservation(int no_seat, String seat_type, Timestamp create_time) {
//        add FirebaseUser user?
//        this.account_id = account_id.getUid();
        this.no_seat = no_seat;
        this.account_id = 111;
        this.seat_type = seat_type;
        this.created_time = create_time;
//        this.comment = comment;
    }

//    public int getAccount_id() {
//        return account_id;
//    }
//
//    public void setAccount_id(int account_id) {
//        this.account_id = account_id;
//    }

    public int getNo_seat() {
        return no_seat;
    }

    public void setNo_seat(int no_seat) {
        this.no_seat = no_seat;
    }

    public String getSeat_type() {
        return seat_type;
    }

    public void setSeat_type(String seat_type) {
        this.seat_type = seat_type;
    }

    public Timestamp getCreated_time() {
        return created_time;
    }

    public void setCreated_time(Timestamp created_time) {
        this.created_time = created_time;
    }
}

