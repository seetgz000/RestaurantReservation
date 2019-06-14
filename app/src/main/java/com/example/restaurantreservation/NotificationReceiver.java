package com.example.restaurantreservation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
        Intent noteIntent = new Intent(context,AppNotification.class);
        context.startService(noteIntent);
    }
}
