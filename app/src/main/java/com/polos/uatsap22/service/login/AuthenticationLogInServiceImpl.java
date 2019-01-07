package com.polos.uatsap22.service.login;

import android.content.Context;
import android.content.Intent;

import com.polos.uatsap22.database.MyDatabase;
import com.polos.uatsap22.database.NotPasswordsDB;
import com.polos.uatsap22.database.UserDB;
import com.polos.uatsap22.model.builder.UserBuilder;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationLogInServiceImpl implements AuthenticationLogInService {

    private AuthenticationLogInService authenticationLogInService;

    private final MyDatabase myDatabase;


    public AuthenticationLogInServiceImpl() {
        myDatabase = MyDatabase.getInstance();
    }

    @Override
    public boolean logIn(final NotPasswordsDB notPasswordsDB) {

        final List<String> passwords = new ArrayList<>();
        final List<String> phoneNumber = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                int userId = notPasswordsDB.getId();
                String phone = myDatabase.loginDao().getPhoneNumberById(userId);
                phoneNumber.add(phone);
                String databasePassword = myDatabase.notPasswordsDao().getUserPassword(userId);
                passwords.add(databasePassword);
            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String phone = phoneNumber.get(0);
        String insertedPassword = new String();

        if(phone.equals("0742072669") || phone.equals("123") || phone.equals("234")){

            insertedPassword = notPasswordsDB.getPassword();
        }else {

            insertedPassword = encodePassword(notPasswordsDB.getPassword());
        }

        String databasePassword = passwords.get(0);

        return insertedPassword.equals(databasePassword);
    }

    @Override
    public int getUserIdByPhoneNumber(final String phoneNumber) {

        final List<Integer> ids = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                Integer userId = myDatabase.loginDao().getUserIdByPhone(phoneNumber);
                ids.add(userId);
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
    public String insertNewUser(final UserDB newUser, final NotPasswordsDB notPassword) {

        final List<String> errors = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                String newUserPhone = newUser.getPhoneNumber();
                List<UserDB> allUsers = myDatabase.loginDao().getAllUsers();

                boolean isPhoneUsed = false;
                for (UserDB userDB : allUsers) {
                    if (userDB.getPhoneNumber().equals(newUserPhone)) {
                        isPhoneUsed = true;
                        break;
                    }
                }

                if (isPhoneUsed) {
                    errors.add("User already exists");
                } else {

                    String passwordCheck = checkPassword(notPassword.getPassword());

                    if(passwordCheck== null){

                        String encodedPassword = encodePassword(notPassword.getPassword());
                        notPassword.setPassword(encodedPassword);
                        myDatabase.loginDao().insertUser(newUser);
                        myDatabase.notPasswordsDao().insertPassword(notPassword);
                    }
                    else {

                        errors.add(passwordCheck);
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

        if (errors.size() > 0){

            String returnErrors = new String();

            for(String s : errors){
                returnErrors += s;
            }
            return returnErrors;
        }

        return "Sign in successful";
    }

    @Override
    public void updateUserCounter() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<UserDB> userDBList = new ArrayList<>();
                userDBList.addAll(myDatabase.loginDao().getAllUsers());
                UserDB.setUserCount(userDBList.get(userDBList.size() - 1).getUserId() + 1);
            }
        }).start();
    }

    @Override
    public boolean deleteAsAdmin(final NotPasswordsDB admin, final String phoneNumberToDelete) {

        final List<Object> hasBeenDeleted = new ArrayList<>();

        if (logIn(admin)) {

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    UserDB deleteUser = myDatabase.loginDao().getUserById(Integer.parseInt(phoneNumberToDelete));

                    if (myDatabase.loginDao().isAdmin(admin.getId())) {

                        myDatabase.loginDao().deleteUser(deleteUser.getUserId());
                        hasBeenDeleted.add(true);
                    } else {
                        hasBeenDeleted.add(false);
                    }
                }
            });

            t.start();

            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return (boolean) hasBeenDeleted.get(0);
        }

        return false;
    }


    private String checkPassword(String password) {

        String invalidations = new String();

        if (password.length() < 5) {
            invalidations += "\nPassword too short";
        }
        if (!(password.matches(".*[a-z].*") && password.matches(".*[0-9].*"))) {
            invalidations += "\nMust contain a letter and a letter and a digit";
        }

        if (invalidations.length() == 0){
            return null;
        }

        return invalidations;
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
