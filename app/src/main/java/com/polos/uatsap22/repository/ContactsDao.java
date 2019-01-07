package com.polos.uatsap22.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.polos.uatsap22.database.Constants;
import com.polos.uatsap22.database.ContactDB;
import com.polos.uatsap22.database.UserDB;

import java.util.List;

@Dao
public interface ContactsDao {

    @Insert
    void insertNewContact(ContactDB contactDB);

    @Query("SELECT * FROM ContactDB WHERE userId = :userId")
    List<ContactDB> getUserContacts(int userId);

    @Query("SELECT * FROM ContactDB")
    List<ContactDB> getAllContactList();

    @Query("DELETE FROM ContactDB WHERE contactId IS :userId")
    void deleteContact(int userId);

    @Query("DELETE FROM ContactDB WHERE contactId IS :contactId AND userId IS :userId")
    void deleteContact(int userId, int contactId);
}
