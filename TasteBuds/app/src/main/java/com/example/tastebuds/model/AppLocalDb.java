package com.example.tastebuds.model;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tastebuds.MyApplication;

// ROOM - 1.Database - DB holder, serves as the main access point to your app's persisted data
@Database(entities = {Post.class}, version = 2)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao postDao();
}

// MonoState
public class AppLocalDb{
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb(){}
}