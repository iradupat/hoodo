package com.example.hodoo.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.hodoo.R;
import com.example.hodoo.controller.FactoryController;
import com.example.hodoo.controller.StoreUserInterface;
import com.example.hodoo.controller.UserAuthInterface;
import com.example.hodoo.dao.RoomDB;
import com.example.hodoo.model.User;
import com.example.hodoo.service.UserLocationService;
import com.example.hodoo.view.MainActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class HoodoMessageService extends FirebaseMessagingService {
    private StoreUserInterface storeUserInterface = FactoryController.createStoreUserController("ROOM_DB");

    private UserAuthInterface userAuthInterface = FactoryController.registerUserController("FIREBASE_DB");

    private RoomDB db ;

    private User user;



    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        db = RoomDB.getInstance(this);
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        User user=null;
        if(storeUserInterface.checkIfUserExist(db))
        {
            user = storeUserInterface.getCredentials(db);
        }
        String userId = remoteMessage.getData().get("senderId");

        if(user == null || user.getUserId().equals(userId) || !user.isNotificationOn()){
            return;
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "HOODO")
                .setContentTitle(title).setContentTitle(body).setSmallIcon(R.drawable.logo);

        Intent notifIntent = new Intent(this, MainActivity.class);
        notifIntent.putExtra("extraData", "some data");
        PendingIntent pIntent = PendingIntent.getActivity(this, 120, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int id = (int)System.currentTimeMillis();
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("HOODO", "APP", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(id,notificationBuilder.build());

    }

   }
