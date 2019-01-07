package com.polos.uatsap22.model.builder;

import com.polos.uatsap22.database.UserDB;

public class AdminBuilder {

    private UserDB userDB;

    public AdminBuilder(){
        userDB = new UserDB();
    }

    public AdminBuilder setPhoneNumber(String username) {
        userDB.setPhoneNumber(username);
        return this;
    }

    public AdminBuilder setAdmin(boolean admin) {
        userDB.setAdmin(true);
        return this;
    }

    public UserDB build(){
        return userDB;
    }

    public UserDB buildForDB(){
        userDB.setUserId(UserDB.getUserNo());
        UserDB.incrementUserNo();
        return userDB;
    }
}
