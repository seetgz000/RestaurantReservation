package com.example.restaurantreservation;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.JobIntentService;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;

public class AppNotification extends JobIntentService {
    public static final String channel1= "channel1";
    public static final int id = 1;

    //public AppNotification(){
        //super("AppNotification");
    //}

    @Override
    public void onHandleWork(Intent intent){
        Intent notifyIntent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,2,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuild = new NotificationCompat.Builder(this, channel1)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Ang Ang restaurant reservation")
                .setContentText("Your reservation is 30 mins away!")
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
        mNotificationManager.notify(id,notificationBuild.build() );
    }

    private void createNotificationChannel(){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String name = "Ang Ang restaurant";
                String description = "From your favourite restaurant";
                int importance = NotificationManager.IMPORTANCE_HIGH;

                NotificationChannel channel = new NotificationChannel(channel1, name, importance);
                channel.setDescription("Reminder on time");
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                channel.setLightColor(Color.TRANSPARENT);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
                }
                }

        public void sendNotification() {
                NotificationCompat.Builder notificationBuild = new NotificationCompat.Builder(this, channel1)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Ang Ang restaurant reservation")
                .setContentText("Your reservation is 30 mins away!")
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                createNotificationChannel();
                mNotificationManager.notify(id,notificationBuild.build() );
                }

        }