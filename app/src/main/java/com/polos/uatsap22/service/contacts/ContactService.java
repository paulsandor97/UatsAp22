package com.polos.uatsap22.service.contacts;

import com.polos.uatsap22.database.ContactDB;
import com.polos.uatsap22.database.UserDB;

import java.util.List;

public interface ContactService {

    List<ContactDB> getUserContacts(int userId);

    UserDB getUserById(int userId);

    void insertNewContact(int userId, String contactPhoneNumber);

    void deleteContact(int userId, String contactPhoneNumber);

    void updateContactCount();

    boolean alreadyContacts(int id1, int id2);

}
