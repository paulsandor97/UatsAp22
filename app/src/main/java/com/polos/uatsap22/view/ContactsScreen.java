package com.polos.uatsap22.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.polos.uatsap22.ComponentFactory;
import com.polos.uatsap22.R;
import com.polos.uatsap22.database.ContactDB;
import com.polos.uatsap22.database.MyDatabase;
import com.polos.uatsap22.database.UserDB;
import com.polos.uatsap22.model.builder.NotPasswordsBuilder;
import com.polos.uatsap22.model.builder.UserBuilder;
import com.polos.uatsap22.model.recyclerViewAdapter.ContactsAdapter;
import com.polos.uatsap22.service.contacts.ContactService;
import com.polos.uatsap22.service.contacts.ContactServiceImpl;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class ContactsScreen extends AppCompatActivity {

    private static final String TAG = "RV";
    public static final String CONTACT = "Your Phone Number";
    public static final String YOU = "Contact Phone Number";

    private MyDatabase myDatabase;

    private Handler handler;

    private ContactService contactService;

    private int yourId;
    private String yourPhoneNumber;

    private TextView contactName;
    private EditText newContact;
    private Button addNewContact;
    private Button deleteContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_screen);

        Intent intent = getIntent();
        yourPhoneNumber = intent.getStringExtra(LoginScreen.PHONE_MESSAGE);
        yourId = intent.getIntExtra(LoginScreen.ID_MESSAGE, -1);

        myDatabase = MyDatabase.getInstance(this);

        contactService = ComponentFactory.getInstance().getContactService();

        newContact = (EditText) findViewById(R.id.newContact);
        contactName = (TextView) findViewById(R.id.contactName);
        addNewContact = (Button) findViewById(R.id.addNewContact);
        deleteContact = (Button) findViewById(R.id.deleteContact);

        contactName.setText(yourPhoneNumber);

        setButtonListeners();

        getContacts(yourId);

        updateContactCount();
    }

    private void updateContactCount() {

        contactService.updateContactCount();
    }

    private void setButtonListeners() {

        addNewContact.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                contactService.insertNewContact(yourId, newContact.getText().toString());
                getContacts(yourId);
            }
        });

        deleteContact.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                contactService.deleteContact(yourId, newContact.getText().toString());
                getContacts(yourId);
            }
        });

    }

    private void getContacts(final int yourId) {

        final List<ContactDB> contactList = new ArrayList<>();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {

                contactList.addAll(myDatabase.contactsDao().getUserContacts(yourId));
            }
        });

        t1.start();

        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        final List<UserDB> contactsName = new ArrayList<>();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {

                for(ContactDB contactDB : contactList){

                    contactsName.add(contactService.getUserById(contactDB.getContactId()));
                }
            }
        });

        t2.start();

        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

        ContactsAdapter adapter = new ContactsAdapter(contactsName);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

        final List<UserDB> m = new ArrayList<>();
        m.addAll(contactsName);

        adapter.setOnItemClickListener(new ContactsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String contactPhone = m.get(position).getPhoneNumber();
                goToMessages(contactPhone);
            }
        });
    }

    public void goToMessages(String contactPhoneNumber){

        Intent intent = new Intent(getApplicationContext(), MessagesScreen.class);
        Bundle extras = new Bundle();
        extras.putString(YOU, yourPhoneNumber);
        extras.putString(CONTACT, contactPhoneNumber);
        intent.putExtras(extras);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), contactPhoneNumber + " was clicked from " + yourPhoneNumber, Toast.LENGTH_LONG).show();
    }
}
