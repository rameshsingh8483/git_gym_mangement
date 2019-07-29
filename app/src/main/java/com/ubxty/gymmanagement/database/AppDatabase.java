package com.ubxty.gymmanagement.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;


import com.ubxty.gymmanagement.database.dao.UserDao;
import com.ubxty.gymmanagement.database.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase = null;

    /**
     * from developers android, made my own singleton
     *
     * @param context
     * @return
     */
    public static AppDatabase getInstance(Context context) {


        if (appDatabase == null) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(),
            AppDatabase.class, "userTable").build();
        }


        return appDatabase;
    }

    public abstract UserDao userDao() ;
}