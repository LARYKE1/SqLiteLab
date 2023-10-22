package com.example.sqlitelab;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT *FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid = :userId")
    User getById(int userId);

    @Insert
    void insertData (User... users);

    @Query("DELETE FROM user WHERE uid = :userId")
    void delete(int userId);

    @Update
    void update(User... user);
}
