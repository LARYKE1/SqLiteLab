package com.example.sqlitelab;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities ={User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME="lab.db";
    private static AppDatabase appDatabase;

    //Singleton Pattern
    // I used synchronized in case this method gonna use more Threads, only one thread at a time will execute this
    public static synchronized AppDatabase getInstance(Context context){
        if(appDatabase==null){
            appDatabase= Room.databaseBuilder(context, AppDatabase.class,DATABASE_NAME)
                    .build();
        }

        return appDatabase;
    }
    public abstract UserDao userDao();
}
