package com.polos.uatsap22.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = UserDB.class,
        parentColumns = "userId",
        childColumns = "id",
        onDelete = CASCADE))
public class NotPasswordsDB {


    @Ignore
    private static boolean adminAdded = false;

    @PrimaryKey
    private int id;
    private  String password;

    public NotPasswordsDB() { }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public static NotPasswordsDB defaultAdmin(){

        if(!adminAdded){

            NotPasswordsDB notPasswordsDB = new NotPasswordsDB();
            notPasswordsDB.setId(0);
            notPasswordsDB.setPassword("123");

            adminAdded = true;

            return notPasswordsDB;
        }
        return null;
    }

}
