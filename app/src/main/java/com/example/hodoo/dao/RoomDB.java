package com.example.hodoo.dao;


import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;


public abstract class RoomDB extends RoomDatabase {
    private static  RoomDB instance;
    public abstract  UserDao userDao();
    public static synchronized RoomDB getInstance(Context context) {
        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, "user")
                    .fallbackToDestructiveMigration().allowMainThreadQueries()
                    .build();
        }

        return instance;
    }
}
