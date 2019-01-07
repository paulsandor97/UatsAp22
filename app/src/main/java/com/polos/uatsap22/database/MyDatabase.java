package com.polos.uatsap22.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.polos.uatsap22.repository.AdminDao;
import com.polos.uatsap22.repository.ContactsDao;
import com.polos.uatsap22.repository.LoginDao;
import com.polos.uatsap22.repository.MessagesDao;
import com.polos.uatsap22.repository.NotPasswordsDao;

import java.util.concurrent.Executors;

@Database(entities = {UserDB.class, NotPasswordsDB.class, ContactDB.class, MessageDB.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase INSTANCE;

    public abstract LoginDao loginDao();
    public abstract ContactsDao contactsDao();
    public abstract MessagesDao messagesDao();
    public abstract NotPasswordsDao notPasswordsDao();
    public abstract AdminDao adminDao();

    public synchronized static MyDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    public synchronized static MyDatabase getInstance() {
        return INSTANCE;
    }

    private static MyDatabase buildDatabase(final Context context) {
        return (MyDatabase) Room.databaseBuilder(context,
                MyDatabase.class,
                Constants.DATABASE_NAME).
                addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).loginDao().insertUser(UserDB.defaultAdmin());
                            }
                        });
                    }
                })
                //.fallbackToDestructiveMigration()
                .build();
    }


}
