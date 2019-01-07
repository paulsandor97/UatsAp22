package com.polos.uatsap22.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.polos.uatsap22.database.Constants;
import com.polos.uatsap22.database.UserDB;

import java.util.List;

@Dao
public interface AdminDao {

    @Query("SELECT * FROM UserDB WHERE admin IS NOT 1")
    List<UserDB> getAllUsers();
}
