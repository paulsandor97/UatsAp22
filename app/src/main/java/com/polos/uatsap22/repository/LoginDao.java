package com.polos.uatsap22.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.polos.uatsap22.database.Constants;
import com.polos.uatsap22.database.UserDB;

import java.util.List;

@Dao
public interface LoginDao {

    @Insert
    void insertUser(UserDB user);

    @Query(Constants.GET_USERID_BY_PHONE_NUMBER)
    int getUserIdByPhone(String phoneNumber);

    @Query(Constants.GET_PHONE_NUMBER_BY_ID)
    String getPhoneNumberById(int id);

    @Query(Constants.GET_USER_BY_PHONE_NUMBER)
    UserDB getUserByPhoneNumber(String phoneNumber);

    @Query(Constants.GET_USER_BY_ID)
    UserDB getUserById(int userId);

    @Query(Constants.IS_ADMIN)
    boolean isAdmin(int userId);

    @Query(Constants.GET_ALL_USERS)
    List<UserDB> getAllUsers();

    @Query(Constants.DELETE_USER)
    void deleteUser(int userId);
}
