package com.example.hodoo.view;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.example.hodoo.R;
import com.example.hodoo.util.Notification;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

    public class FirebaseMessageReceiver
            extends FirebaseMessagingService {
        @Override
        public void onNewToken(String s) {
            super.onNewToken(s);
            Log.e("newToken", s);
            getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", s).apply();
        }

        @Override
        public void
        onMessageReceived(RemoteMessage remoteMessage) {
            super.onMessageReceived(remoteMessage);
            if (remoteMessage.getNotification() != null) {
                new Notification(this);
                Notification.showNotification(
                        this,
                        remoteMessage.getNotification().getTitle(),
                        remoteMessage.getNotification().getBody()
                );
            }
        }

        public static String getToken(Context context) {
            return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "empty");
        }


}
