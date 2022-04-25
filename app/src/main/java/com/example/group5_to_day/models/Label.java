package com.example.group5_to_day.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Label {
    @PrimaryKey(autoGenerate = true) private int uid;
    @ColumnInfo @NonNull private String description;

    @Override
    public String toString() {
        return description;
    }

    public Label(String description) {
        this.description = description;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }
}