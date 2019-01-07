package com.polos.uatsap22.database;

public class Constants {

    public static final String DATABASE_NAME = "uatsap_db";

    //UserDB Queries
    public static final String GET_USERID_BY_PHONE_NUMBER = "SELECT userId FROM UserDB WHERE phoneNumber = :phoneNumber LIMIT 1";
    public static final String GET_USER_BY_PHONE_NUMBER = "SELECT * FROM UserDB WHERE phoneNumber = :phoneNumber LIMIT 1";
    public static final String GET_PHONE_NUMBER_BY_ID = "SELECT phoneNumber FROM UserDB WHERE userId = :id";
    public static final String GET_ALL_USERS = "SELECT * FROM UserDB";
    public static final String DELETE_USER = "DELETE FROM UserDB WHERE userId is :userId";
    public static final String IS_ADMIN = "SELECT admin FROM UserDB WHERE userId IS :userId";
    public static final String GET_USER_BY_ID = "SELECT * FROM UserDB WHERE userId = :userId LIMIT 1";
}
