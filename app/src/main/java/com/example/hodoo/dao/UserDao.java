package com.example.hodoo.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.hodoo.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("UPDATE user SET language = :lang WHERE userId = :userId")
    void update(String userId, String lang);
}
