package com.assignment.restaurantreservation.models;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

@IgnoreExtraProperties
public class Reservation {

    private String account_id;
    private @ServerTimestamp Timestamp created_time;
    private int no_seat;
    private String seat_type;
    private String reserve_date;
    private String reserve_time;
    private String comment;
    private boolean removed;

    public Reservation() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Reservation(FirebaseUser account, int no_seat, String seat_type, String reserve_date, String reserve_time, String comment, Timestamp created_time, boolean removed) {
        this.account_id = account.getUid();
        this.no_seat = no_seat;
        this.seat_type = seat_type;
        this.reserve_date = reserve_date;
        this.reserve_time = reserve_time;
        this.comment = comment;
        this.created_time = created_time;
        this.removed = removed;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

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

    public String getReserve_date() { return reserve_date;}

    public void setReserve_date(String reserve_date) { this.reserve_date = reserve_date;}

    public String getReserve_time() { return reserve_time;}

    public void setReserve_time(String reserve_time) { this.reserve_time = reserve_time;}

    public String getComment() { return comment;}

    public void setComment(String comment) { this.comment = comment;}

    public boolean getRemoved() { return removed;}

    public void serRemoved(boolean removed) { this.removed = removed;}

}

