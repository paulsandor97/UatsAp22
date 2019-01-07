package com.polos.uatsap22.view;

import android.content.Intent;
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
import com.polos.uatsap22.model.recyclerViewAdapter.UsersAdapter;
import com.polos.uatsap22.service.Admin.AdminService;

import java.util.ArrayList;
import java.util.List;

public class AdminScreen extends AppCompatActivity {

    private Button insertNewUser;
    private TextView adminPhoneTV;
    private EditText phone;
    private EditText password;
    private String adminPhone;

    AdminService adminService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        Intent intent = getIntent();
        adminPhone = intent.getStringExtra(LoginScreen.PHONE_MESSAGE);

        adminService = ComponentFactory.getInstance().getAdminService();

        adminPhoneTV = (TextView) findViewById(R.id.adminPhone);
        adminPhoneTV.setText(adminPhone);
        phone = (EditText) findViewById(R.id.phone_number);
        password = (EditText) findViewById(R.id.password) ;

        insertNewUser = (Button) findViewById(R.id.insert_new_user_button);

        showUsers();

        setButtonListeners();

    }

    private void showUsers() {

        final List<UserDB> userDBList = adminService.showDatabase();


        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvUsers);

        UsersAdapter adapter = new UsersAdapter(userDBList);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
        // That's all!

        final List<UserDB> m = new ArrayList<>();
        m.addAll(userDBList);

        adapter.setOnItemClickListener(new UsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String userPhone = m.get(position).getPhoneNumber();
                //showMessageToast(userPhone);
                adminService.deleteUser(userPhone);
                showUsers();
            }
        });
    }

    private void setButtonListeners() {

        insertNewUser.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String signInError = adminService.insertNewUser(new UserBuilder()
                                .setAdmin(false)
                                .setPhoneNumber(phone.getText().toString())
                                .setUserId(UserDB.getUserNo())
                                .buildForDB()
                        ,new NotPasswordsBuilder()
                                .setPassword(password.getText().toString())
                                .setId(UserDB.getUserNo() -1)
                                .build()
                );
                showMessageToast(signInError);
                if(signInError.equals("Sign in successful")) {
                    showUsers();
                    phone.setText("");
                    password.setText("");
                }
            }
        });

    }


    public void showMessageToast(String s){

        Toast toast = Toast.makeText(this, s, Toast.LENGTH_LONG);
        toast.show();
    }
}
