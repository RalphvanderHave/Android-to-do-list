package com.example.group5_to_day.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.group5_to_day.models.Label;

import java.util.List;

@Dao
public interface LabelDao {
    @Query("SELECT * FROM Label")
    List<Label> getAll();

    @Insert
    void insertLabel(Label label);

    @Delete
    void deleteLabel(Label label);
}