package com.polos.uatsap22.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {

        @ForeignKey(entity = UserDB.class,
                parentColumns = "userId",
                childColumns = "fromId",
                onDelete = CASCADE),

        @ForeignKey(entity = UserDB.class,
                parentColumns = "userId",
                childColumns = "toId",
                onDelete = CASCADE)

})
public class MessageDB {

    @Ignore
    private static int messageCount = 0;

    @PrimaryKey
    private int messageId;
    private int fromId;
    private int toId;
    private String message;

    public MessageDB() { }

    public static int getMessageCount() { return messageCount; }

    public static void incrementMessageCount() { MessageDB.messageCount++; }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public int getMessageId() {
        return messageId;
    }

    public static void setMessageCount(int messageCount) {
        MessageDB.messageCount = messageCount;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void setToId(int toId) { this.toId = toId; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
