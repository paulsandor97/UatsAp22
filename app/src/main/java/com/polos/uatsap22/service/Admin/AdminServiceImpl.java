package com.polos.uatsap22.service.Admin;

import com.polos.uatsap22.database.MyDatabase;
import com.polos.uatsap22.database.NotPasswordsDB;
import com.polos.uatsap22.database.UserDB;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class AdminServiceImpl implements AdminService {

    private AdminService adminService;

    private final MyDatabase myDatabase;

    public AdminServiceImpl(){
        myDatabase = MyDatabase.getInstance();
    }

    @Override
    public List<UserDB> showDatabase() {

        final List<UserDB> userDBList = new ArrayList<>();

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
               userDBList.addAll(myDatabase.adminDao().getAllUsers());
            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return userDBList;
    }

    @Override
    public void deleteUser(final String phone) {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                myDatabase.loginDao().deleteUser(myDatabase.loginDao().getUserIdByPhone(phone));
            }
        });

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String insertNewUser(final UserDB newUser, final NotPasswordsDB notPasswordsDB) {

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

                    String passwordCheck = checkPassword(notPasswordsDB.getPassword());

                    if(passwordCheck== null){

                        String encodedPassword = encodePassword(notPasswordsDB.getPassword());
                        notPasswordsDB.setPassword(encodedPassword);
                        myDatabase.loginDao().insertUser(newUser);
                        myDatabase.notPasswordsDao().insertPassword(notPasswordsDB);
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
