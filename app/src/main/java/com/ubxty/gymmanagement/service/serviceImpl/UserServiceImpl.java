package com.ubxty.gymmanagement.service.serviceImpl;

import android.content.Context;


import com.ubxty.gymmanagement.database.AppDatabase;
import com.ubxty.gymmanagement.database.dao.UserDao;
import com.ubxty.gymmanagement.database.entity.User;
import com.ubxty.gymmanagement.service.UserService;

import java.util.List;

/**
 * Created by dinakar.maurya on 25-01-2018.
 */

/**
 * writing my own impls
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(Context context) {
        userDao = AppDatabase.getInstance(context).userDao();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public void insertAll(User... users) {
        userDao.insertAll(users) ;

    }

    @Override
    public void deleteUser(User user) {
        userDao.delete(user) ;
    }

    @Override
    public List<User> fullPayUser() {

        return  userDao.fullpayUser() ;

        //return null;
    }

    @Override
    public List<User> pendingPayUser() {
        return  userDao.pendingPayUser() ;
    }

    @Override
    public List<User> thisMonthUser(String month) {

        return  userDao.thisMonthUser(month) ;

    }

    @Override
    public void updateUsers(User user) {

          userDao.updateUsers(user) ;


    }


}
