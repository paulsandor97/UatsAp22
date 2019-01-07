package com.polos.uatsap22.service.messages;

import com.polos.uatsap22.database.MessageDB;

import java.util.List;

public interface MessageService {

    int getUserIdByPhoneNumber(String phoneNumber);

    List<MessageDB> getConversation(int myId, int contactId);

    void sendMessage(int userId, int contactId, String message);

    void updateMessageCount();
}
