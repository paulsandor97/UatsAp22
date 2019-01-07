package com.polos.uatsap22.service.login;

import com.polos.uatsap22.database.NotPasswordsDB;
import com.polos.uatsap22.database.UserDB;

public interface AuthenticationLogInService {

    boolean logIn(NotPasswordsDB notPasswordsDB);

    int getUserIdByPhoneNumber(String phoneNumber);

    String insertNewUser(UserDB newUser, NotPasswordsDB notPassword);

    boolean deleteAsAdmin(NotPasswordsDB admin, String phoneNumberToDelete);

    void updateUserCounter();
}
