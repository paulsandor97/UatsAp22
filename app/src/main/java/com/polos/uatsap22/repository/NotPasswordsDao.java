package com.polos.uatsap22.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.polos.uatsap22.database.NotPasswordsDB;

import java.util.List;

@Dao
public interface NotPasswordsDao {

    @Insert
    void insertPassword(NotPasswordsDB passwordsDb);

    @Query("SELECT NotPasswordsDB.password FROM UserDB INNER JOIN NotPasswordsDB ON UserDB.userId = NotPasswordsDB.id and NotPasswordsDB.id = :userId")
    String getUserPassword(int userId);

    @Query("SELECT * FROM NotPasswordsDB")
    List<NotPasswordsDB> getAllPasswords();
}
