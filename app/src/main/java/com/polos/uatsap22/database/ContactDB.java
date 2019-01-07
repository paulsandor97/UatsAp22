package com.polos.uatsap22.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {

        @ForeignKey(entity = UserDB.class,
                parentColumns = "userId",
                childColumns = "userId",
                onDelete = CASCADE),

        @ForeignKey(entity = UserDB.class,
                parentColumns = "userId",
                childColumns = "contactId",
                onDelete = CASCADE)

})
public class ContactDB {

    @Ignore
    private static int listIdCount = 0;

    @PrimaryKey
    private int listId;
    private int userId;
    private int contactId;

    public ContactDB() { }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getListId(){return listId; }

    public static int getListIdCount() { return listIdCount; }

    public static void incrementListIdCount() { ContactDB.listIdCount++; }

    public static void setListIdCount(int listIdCount) {
        ContactDB.listIdCount = listIdCount;
    }
}
