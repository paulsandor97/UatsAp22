package com.polos.uatsap22.view;

import android.arch.persistence.room.Database;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.polos.uatsap22.ComponentFactory;
import com.polos.uatsap22.R;
import com.polos.uatsap22.database.MessageDB;
import com.polos.uatsap22.database.MyDatabase;
import com.polos.uatsap22.model.builder.UserBuilder;
import com.polos.uatsap22.model.recyclerViewAdapter.MessagesAdapter;
import com.polos.uatsap22.service.messages.MessageService;

import java.util.ArrayList;
import java.util.List;

public class MessagesScreen extends AppCompatActivity {

    private MyDatabase myDatabase;

    private TextView contactName;
    private EditText messageToSend;
    private Button sendMessageButton;

    private int myId;
    private String phoneNumber;
    private int contactId;
    private String contactNumber;

    private MessageService messageService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_screen);

        myDatabase = MyDatabase.getInstance(this);

        messageService = ComponentFactory.getInstance().getMessageService();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        phoneNumber = extras.getString(ContactsScreen.YOU);
        contactNumber = extras.getString(ContactsScreen.CONTACT);

        contactName = (TextView) findViewById(R.id.contactName);
        contactName.setText(contactNumber);
        messageToSend = (EditText) findViewById(R.id.message);
        sendMessageButton = (Button) findViewById(R.id.send_message);

        myId = messageService.getUserIdByPhoneNumber(phoneNumber);
        contactId = messageService.getUserIdByPhoneNumber(contactNumber);

        getMessages();

        setButtonListeners();

        updateMessageCount();
    }

    private void updateMessageCount() {
        messageService.updateMessageCount();
    }

    private void setButtonListeners() {

        sendMessageButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                messageService.sendMessage(myId, contactId, messageToSend.getText().toString());
                getMessages();

                messageToSend.setText("");
            }
        });
    }

    private void getMessages() {

        final List<MessageDB> messages = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                messages.addAll(myDatabase.messagesDao().getMessages(myId, contactId));
            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RecyclerView rvMessages = (RecyclerView) findViewById(R.id.rvMessages);

        MessagesAdapter adapter = new MessagesAdapter(messages, new UserBuilder()
                                                                    .setUserId(myId)
                                                                    .setPhoneNumber(phoneNumber)
                                                                    .build(),
                                                                new UserBuilder()
                                                                    .setUserId(contactId)
                                                                    .setPhoneNumber(contactNumber)
                                                                    .build());
        // Attach the adapter to the recyclerview to populate items
        rvMessages.setAdapter(adapter);
        // Set layout manager to position the items
        rvMessages.setLayoutManager(new LinearLayoutManager(this));

        rvMessages.scrollToPosition(messages.size() - 1);
        // That's all!
    }
}
