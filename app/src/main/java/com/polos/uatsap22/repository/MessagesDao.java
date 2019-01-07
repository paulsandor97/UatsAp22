package com.polos.uatsap22.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.polos.uatsap22.database.MessageDB;

import java.util.List;

@Dao
public interface MessagesDao {

    @Insert
    void insertNewMessage(MessageDB messages);

    @Query("SELECT * FROM MessageDB WHERE (fromId IS :myId AND toId IS :contactId) OR (fromId IS :contactId and toId is :myId)")
    List<MessageDB> getMessages(int myId, int contactId);

    @Query("SELECT * FROM MessageDB")
    List<MessageDB> getAllMessages();
}
