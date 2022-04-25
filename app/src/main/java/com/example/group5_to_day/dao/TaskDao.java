package com.example.group5_to_day.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.group5_to_day.models.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task WHERE owner = :userName")
    List<Task> getAllTasksFromUser(String userName);

    @Query("SELECT * FROM Task WHERE checkedDate is null AND owner = :userName ORDER BY deadline")
    List<Task> getUncheckedTasksFromUser(String userName);

    @Query("SELECT * FROM Task WHERE checkedDate is not null AND owner = :userName ORDER BY deadline")
    List<Task> getCheckedTasksFromUser(String userName);

    @Update
    void updateTask(Task task);

    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);
    }
