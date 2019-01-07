package com.polos.uatsap22.service.messages;

import com.polos.uatsap22.database.MessageDB;
import com.polos.uatsap22.database.MyDatabase;
import com.polos.uatsap22.model.builder.MessageBuilder;

import java.util.ArrayList;
import java.util.List;

public class MessageServiceImpl implements MessageService{

    private MessageService messageService;

    MyDatabase myDatabase;

    public MessageServiceImpl() {
        myDatabase = MyDatabase.getInstance();
    }

    @Override
    public void updateMessageCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<MessageDB> messageDBS = new ArrayList<>();
                messageDBS.addAll(myDatabase.messagesDao().getAllMessages());
                MessageDB.setMessageCount(messageDBS.get(messageDBS.size() - 1).getMessageId() + 1);
            }
        }).start();
    }

    @Override
    public void sendMessage(final int userId, final int contactId, final String message) {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

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
                myDatabase.messagesDao().insertNewMessage(new MessageBuilder()
                                                            .setFromId(userId)
                                                            .setToId(contactId)
                                                            .setMessage(message)
                                                            .buildForDB());
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
    public int getUserIdByPhoneNumber(final String phoneNumber) {

        final List<Integer> ids = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                ids.add(myDatabase.loginDao().getUserIdByPhone(phoneNumber));
            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ids.get(0).intValue();
    }

    @Override
    public List<MessageDB> getConversation(final int myId, final int contactId) {

        final List<MessageDB> messageDBS = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                messageDBS.addAll(myDatabase.messagesDao().getMessages(myId, contactId));
            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return messageDBS;
    }
}
