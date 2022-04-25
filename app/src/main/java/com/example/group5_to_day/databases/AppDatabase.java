package com.example.group5_to_day.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.group5_to_day.dao.LabelDao;
import com.example.group5_to_day.dao.TaskDao;
import com.example.group5_to_day.dao.UserDao;
import com.example.group5_to_day.models.Label;
import com.example.group5_to_day.models.Task;
import com.example.group5_to_day.models.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Label.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService dbExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ExecutorService getDbExecutor() {
        return dbExecutor;
    }

    public abstract TaskDao taskDao();

    public abstract LabelDao labelDao();

    public abstract UserDao userDao();

    public static final AppDatabase getDatabase(final Context context) {
        AppDatabase appDatabase;

        synchronized (AppDatabase.class) {
            appDatabase = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "database")
                    .build();
        }
        return appDatabase;
    }
}
