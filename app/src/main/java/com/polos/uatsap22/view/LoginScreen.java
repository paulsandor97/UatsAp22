package com.polos.uatsap22.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.polos.uatsap22.ComponentFactory;
import com.polos.uatsap22.R;
import com.polos.uatsap22.database.MyDatabase;
import com.polos.uatsap22.database.NotPasswordsDB;
import com.polos.uatsap22.database.UserDB;
import com.polos.uatsap22.model.builder.NotPasswordsBuilder;
import com.polos.uatsap22.model.builder.UserBuilder;
import com.polos.uatsap22.service.login.AuthenticationLogInService;
import com.polos.uatsap22.service.login.AuthenticationLogInServiceImpl;

public class LoginScreen extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final String ID_MESSAGE = "User ID";
    public static final String PHONE_MESSAGE = "User Phone";

    //pN 111    qwerty123

    private MyDatabase database;

    private AuthenticationLogInService authenticationLogInService;

    private EditText phoneNumber;
    private EditText password;
    private Button logInButton;
    private Button signUpButton;
    private Button showDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        database = MyDatabase.getInstance(this);

        authenticationLogInService = ComponentFactory.getInstance().getAuthenticationLogInService();

        phoneNumber = (EditText) findViewById(R.id.phoneinput);
        password = (EditText) findViewById(R.id.passwordinput);
        logInButton = (Button) findViewById(R.id.loginbutton);
        signUpButton = (Button) findViewById(R.id.signinbutton);
        showDataBase = (Button) findViewById(R.id.showdbbutton);

        setButtonListeners();
        updateUserCount();
    }

    private void updateUserCount() {
        authenticationLogInService.updateUserCounter();
    }

    private void setButtonListeners(){

        logInButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                boolean authSuccssful = authenticationLogInService.logIn(
                        new NotPasswordsBuilder()
                                .setId(authenticationLogInService.getUserIdByPhoneNumber(phoneNumber.getText().toString()))
                                .setPassword(password.getText().toString())
                                .build());

                if(authSuccssful){
                    goToContacts(phoneNumber.getText().toString());
                }else{

                    showMessageToast("UNABLE TO CONNECT");
                }

            }
        });

        signUpButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                String signInError = authenticationLogInService.insertNewUser(new UserBuilder()
                                .setAdmin(false)
                                .setPhoneNumber(phoneNumber.getText().toString())
                                .setUserId(UserDB.getUserNo())
                                .buildForDB()
                                ,new NotPasswordsBuilder()
                                .setPassword(password.getText().toString())
                                .setId(UserDB.getUserNo() -1)
                                .build()
                );
                showMessageToast(signInError);
                if(signInError.equals("Sign in successful")) {
                    goToContacts(phoneNumber.getText().toString());
                }
            }
        });

        showDataBase.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                boolean authSuccssful = authenticationLogInService.logIn(
                        new NotPasswordsBuilder()
                                .setId(authenticationLogInService.getUserIdByPhoneNumber(phoneNumber.getText().toString()))
                                .setPassword(password.getText().toString())
                                .build());



                if(authSuccssful) {
                    logInAsAdmin(phoneNumber.getText().toString());
                }else{

                showMessageToast("UNABLE TO CONNECT");
            }

            }
        });
    }

    private void goToContacts(String phoneNumber) {

        int userId = authenticationLogInService.getUserIdByPhoneNumber(phoneNumber);
        Intent intent = new Intent(this, ContactsScreen.class);
        intent.putExtra(PHONE_MESSAGE, phoneNumber);
        intent.putExtra(ID_MESSAGE, userId);
        startActivity(intent);
    }

    private void logInAsAdmin(String phoneNumber) {

        Intent intent = new Intent(this, AdminScreen.class);
        intent.putExtra(PHONE_MESSAGE, phoneNumber);
        startActivity(intent);
    }

    public void showMessageToast(String s){

        Toast toast = Toast.makeText(this, s, Toast.LENGTH_LONG);
        toast.show();
    }
}
