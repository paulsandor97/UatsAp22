package com.polos.uatsap22.service.Admin;

import com.polos.uatsap22.database.NotPasswordsDB;
import com.polos.uatsap22.database.UserDB;

import java.util.List;

public interface AdminService {

    List<UserDB> showDatabase();

    void deleteUser(String phone);

    String insertNewUser(UserDB userDB, NotPasswordsDB notPasswordsDB);
}
