package com.example.android.testnotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nex3z.notificationbadge.NotificationBadge;

import java.util.Random;

import me.leolin.shortcutbadger.ShortcutBadger;

public class MainActivity extends AppCompatActivity {
    NotificationCompat.Builder notification;
    NotificationBadge mBadge;
    int counts = 0;
    Data dt = new Data();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBadge = findViewById(R.id.notification_badge);
        if (Data.listAll(Data.class).size()> 0) {
            mBadge.setNumber(Data.listAll(Data.class).get(0).getNotificationCount());

        }
        if (Data.listAll(Data.class).size()==0) {
            ShortcutBadger.applyCount(getApplicationContext(), 0);
        }
        else{
            ShortcutBadger.applyCount(getApplicationContext(),Data.listAll(Data.class).get(0).getNotificationCount());
        }
        notification = new NotificationCompat.Builder(this);
        notification.setAutoCancel(true);


    }

    public void notifyClicked(View view) {
         Random r = new Random();
        int random = r.nextInt(100);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notification.setSmallIcon(R.drawable.ic_stat_name);
        notification.setTicker("this is the ticker");
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("The Title");
        notification.setContentText("Notification Body");
        notification.setSound(alarmSound);

        Intent intent = new Intent(this, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(random, notification.build());

        mBadge.setNumber(++counts);
        dt.notificationCount=counts;
        dt.save();
        if (counts > 100) {
            mBadge.setNumber(99);
        }

    }

    public void clearAction(View view) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        mBadge.setNumber(0);
        if (Data.listAll(Data.class).size() > 0) {
            Data.deleteAll(Data.class);
            Intent intent = new Intent(this,SecondActivity.class);
            startActivity(intent);

        }
        ShortcutBadger.removeCount(getApplicationContext());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Data.listAll(Data.class).size() > 0) {
            dt.setNotificationCount(counts);
            dt.save();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        ShortcutBadger.applyCount(getApplicationContext(),Data.listAll(Data.class).get(0).getNotificationCount());
    }
}
