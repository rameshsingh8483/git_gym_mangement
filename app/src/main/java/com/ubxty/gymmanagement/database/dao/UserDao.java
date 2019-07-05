package com.ubxty.gymmanagement.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.ubxty.gymmanagement.database.entity.User;

import java.util.List;

@Dao
public interface UserDao {


    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE name LIKE :name LIMIT 5")
    User findByName(String name);


    @Query("SELECT * FROM user WHERE payfees = totalfees")
    List<User> fullpayUser();


    @Query("SELECT * FROM user WHERE payFees_status = 0")
    List<User> pendingPayUser();


    @Query("SELECT * FROM user WHERE join_month LIKE  :month ")
    List<User> thisMonthUser(String month);


    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);
}