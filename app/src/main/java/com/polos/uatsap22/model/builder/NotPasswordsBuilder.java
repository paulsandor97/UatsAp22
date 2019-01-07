package com.polos.uatsap22.model.builder;

import com.polos.uatsap22.database.NotPasswordsDB;

public class NotPasswordsBuilder {

    private NotPasswordsDB notPasswordsDB;

    public NotPasswordsBuilder(){
        notPasswordsDB = new NotPasswordsDB();
    }

    public NotPasswordsBuilder setId(int id) {
        notPasswordsDB.setId(id);
        return this;
    }

    public NotPasswordsBuilder setPassword(String password) {
        notPasswordsDB.setPassword(password);
        return this;
    }

    public NotPasswordsDB build(){
        return notPasswordsDB;
    }
}
