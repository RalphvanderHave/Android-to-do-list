package com.example.group5_to_day.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
public class Task implements Serializable {
    @PrimaryKey (autoGenerate = true) private int uid;
    @ColumnInfo @NonNull private String owner;
    @ColumnInfo @NonNull private String title;
    @ColumnInfo @NonNull private String deadline;
    @ColumnInfo @NonNull private String description;
    @ColumnInfo private String checkedDate;
    @ColumnInfo private String label;

    public Task(String title, String description, String deadline, String owner, String label) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.owner = owner;
        this.label = label;
    }

    @Ignore
    public Task(String title, String description, String deadline, String owner) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.owner = owner;
    }

    @Override
    public String toString() {
        if (this.label == null) {
            return this.title + "\nDeadline:" + this.deadline;
        }
        return this.title + "  " + this.label + "\nDeadline:" + this.deadline;
    }

    public static String convertEpochToTime(long epoch) {
        Date date = new Date(epoch);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.forLanguageTag("nl-BE"));
        return format.format(date);
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCheckedDate() {
        return checkedDate;
    }

    public void setCheckedDate(String checkedDate) {
        this.checkedDate = checkedDate;
    }

    public void setCheckedDate(long epochTime) {
        this.checkedDate = convertEpochToTime(epochTime);
    }

    public String getOwner() {
        return owner;
    }
}
