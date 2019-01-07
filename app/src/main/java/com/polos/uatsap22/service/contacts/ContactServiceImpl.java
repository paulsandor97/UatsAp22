package com.polos.uatsap22.service.contacts;

import com.polos.uatsap22.database.ContactDB;
import com.polos.uatsap22.database.MyDatabase;
import com.polos.uatsap22.database.UserDB;
import com.polos.uatsap22.model.builder.ContactBuilder;
import com.polos.uatsap22.model.builder.UserBuilder;

import java.util.ArrayList;
import java.util.List;

public class ContactServiceImpl implements ContactService{

    private ContactService contactService;

    private final MyDatabase myDatabase;

    @Override
    public List<ContactDB> getUserContacts(final int userId) {

        final List<ContactDB> contactDBS = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
               contactDBS.addAll(myDatabase.contactsDao().getUserContacts(userId));
            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return contactDBS;
    }

    @Override
    public void insertNewContact(final int userId, final String contactPhoneNumber) {

        final List<Integer> id = new ArrayList<>();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                int contactId = myDatabase.loginDao().getUserIdByPhone(contactPhoneNumber);
                id.add(contactId);
            }
        });

        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                if(alreadyContacts(userId, id.get(0).intValue())){

                    myDatabase.contactsDao().insertNewContact(new ContactBuilder()
                            .setUserId(userId)
                            .setContactId(id.get(0).intValue())
                            .buildForDB());

                    myDatabase.contactsDao().insertNewContact(new ContactBuilder()
                            .setContactId(userId)
                            .setUserId(id.get(0).intValue())
                            .buildForDB());
                }
            }
        });

        t2.start();

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteContact(final int userId, final String contactPhoneNumber) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                int contactId = myDatabase.loginDao().getUserIdByPhone(contactPhoneNumber);
                myDatabase.contactsDao().deleteContact(userId, contactId);
                myDatabase.contactsDao().deleteContact(contactId, userId);
            }
        }).start();
    }

    @Override
    public UserDB getUserById(final int userId) {

        final List<UserDB> userDBS = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                userDBS.add(new UserBuilder()
                        .setPhoneNumber(myDatabase.loginDao().getPhoneNumberById(userId))
                        .setUserId(userId)
                        .setAdmin(false)
                        .build());
            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return userDBS.get(0);
    }

    @Override
    public void updateContactCount() {


        new Thread(new Runnable() {
            @Override
            public void run() {

                List<ContactDB> contactDBList = myDatabase.contactsDao().getAllContactList();
                if(contactDBList.size() > 0) {
                    ContactDB.setListIdCount(contactDBList.get(contactDBList.size() - 1).getListId() + 1);
                }
            }
        }).start();

    }

    public ContactServiceImpl() {
        myDatabase = MyDatabase.getInstance();
    }

    @Override
    public boolean alreadyContacts(final int id1, final int id2) {

        final List<Object> objectList = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                List<ContactDB> contactDBS1 = myDatabase.contactsDao().getUserContacts(id1);
                List<ContactDB> contactDBS2 = myDatabase.contactsDao().getUserContacts(id2);

                for(ContactDB contactDB : contactDBS1){

                    if(contactDB.getContactId() == id2){

                        for(ContactDB contactDb : contactDBS2){

                            if(contactDb.getContactId() == id1){

                                objectList.add(false);
                                break;
                            }
                        }

                        break;
                    }
                }
            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return (objectList.size() == 0);
    }
}
