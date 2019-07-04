package com.ubxty.gymmanagement.modal;

import com.ubxty.gymmanagement.database.entity.User;

import java.util.ArrayList;
import java.util.List;

public class AllUser {



    List<User> userslist = new ArrayList<>() ;

    public AllUser(List<User> userslist) {
        this.userslist = userslist;
    }

    public List<User> getUserslist() {
        return userslist;
    }

    public void setUserslist(List<User> userslist) {
        this.userslist = userslist;
    }
}
