package com.polos.uatsap22.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class UserDB {

    @Ignore
    private static int userCount = 0;
    @Ignore
    private static boolean adminAdded = false;

    @PrimaryKey
    private int userId;
    private String phoneNumber;
    private boolean admin;

    public UserDB() { }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPhoneNumber(String username) {
        this.phoneNumber = username;
    }

    public static int getUserNo() { return userCount; }

    public static void incrementUserNo() { UserDB.userCount++; }

    public boolean isAdmin() { return admin; }

    public void setAdmin(boolean admin) { this.admin = admin; }

    public int getUserId() {
        return userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public static void setUserCount(int userCount) {
        UserDB.userCount = userCount;
    }

    public static UserDB defaultAdmin(){

        if(!adminAdded){

            UserDB userDB = new UserDB();
            userDB.setAdmin(true);
            userDB.setPhoneNumber("0742072669");
            userDB.setUserId(userCount);
            incrementUserNo();

            adminAdded = true;

            return userDB;
        }
        return null;
    }
}
