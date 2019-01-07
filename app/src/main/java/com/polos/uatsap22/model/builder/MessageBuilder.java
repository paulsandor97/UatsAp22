package com.polos.uatsap22.model.builder;

import com.polos.uatsap22.database.MessageDB;

public class MessageBuilder {

    private MessageDB messageDB;

    public MessageBuilder(){
        messageDB = new MessageDB();
    }


    public MessageBuilder setFromId(int fromId) {
        messageDB.setFromId(fromId);
        return this;
    }

    public MessageBuilder setToId(int toId) {
        messageDB.setToId(toId);
        return this; }

    public MessageBuilder setMessage(String message) {
        messageDB.setMessage(message);
        return this;
    }

    public MessageDB build(){
        return messageDB;
    }

    public MessageDB buildForDB(){
        messageDB.setMessageId(MessageDB.getMessageCount());
        MessageDB.incrementMessageCount();
        return messageDB;
    }
}
