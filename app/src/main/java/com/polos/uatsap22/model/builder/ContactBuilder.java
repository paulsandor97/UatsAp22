package com.polos.uatsap22.model.builder;

import com.polos.uatsap22.database.ContactDB;

public class ContactBuilder {

    private ContactDB contactDB;

    public ContactBuilder(){
        contactDB = new ContactDB();
    }

    public ContactBuilder setUserId(int userId) {
        contactDB.setUserId(userId);
        return this;
    }

    public ContactBuilder setContactId(int contactId) {
        contactDB.setContactId(contactId);
        return this;
    }

    public ContactDB build(){
        return contactDB;
    }

    public ContactDB buildForDB(){
        contactDB.setListId(ContactDB.getListIdCount());
        ContactDB.incrementListIdCount();
        return contactDB;
    }

}
