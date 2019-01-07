package com.polos.uatsap22.model.builder;

import com.polos.uatsap22.database.UserDB;

public class UserBuilder {

    private UserDB userDB;

    public UserBuilder(){
        userDB = new UserDB();
    }

    public UserBuilder setPhoneNumber(String username) {
        userDB.setPhoneNumber(username);
        return this;
    }

    public UserBuilder setUserId(int id){
        userDB.setUserId(id);
        return this;
    }

    public UserBuilder setAdmin(boolean admin) {
        userDB.setAdmin(false);
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
