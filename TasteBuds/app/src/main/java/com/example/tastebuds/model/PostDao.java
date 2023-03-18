package com.example.tastebuds.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

// Room 3.Dao - Contains the methods used for accessing the database
@Dao
public interface PostDao {
    @Query("select * from Post ORDER BY lastUpdated DESC")
    LiveData<List<Post>> getAll();

    @Query("select * from Post where userName = :userName ORDER BY lastUpdated DESC")
    LiveData<List<Post>> getPostsByUser(String userName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);
}
