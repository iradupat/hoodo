package com.example.hodoo.dao;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.hodoo.model.User;

@Database(entities = {User.class}, version = 3, exportSchema = false)
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
