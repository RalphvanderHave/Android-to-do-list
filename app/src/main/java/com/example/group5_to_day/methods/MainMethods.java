package com.example.group5_to_day.methods;

import com.example.group5_to_day.models.User;

import java.util.ArrayList;

public class MainMethods {
    public static int getUserIndex(ArrayList<User> arrayListOfUsers, String usernameString) {
        for (int i = 0; i < arrayListOfUsers.size(); i++) {
            if (usernameString.equals(arrayListOfUsers.get(i).getUsername()))
                return i;
        }
        return -1;
    }

    public static boolean validPassword(ArrayList<User> usersList, int userIndex, String password) {
        return password.equals(usersList.get(userIndex).getPassword());
    }

    public static String padTimeToString(int hourOfDay, int minute) {
        String hourOfDayString = "";
        String minuteString = "";
        if (hourOfDay < 10) {
            hourOfDayString += "0";
        }
        if (minute < 10) {
            minuteString += "0";
        }
        hourOfDayString += hourOfDay;
        minuteString += minute;
        return hourOfDayString + ":" + minuteString;
    }

    public static String padDateToString(int dayOfMonth, int monthStartsAtZero, int year) {
        int month = monthStartsAtZero + 1;
        String monthString = "";
        String dayOfMonthString = "";
        if (month < 10) {
            monthString += "0";
        }
        if (dayOfMonth < 10) {
            dayOfMonthString += "0";
        }
        monthString += month;
        dayOfMonthString += dayOfMonth;
        return dayOfMonthString + "/" + monthString + "/" + year;
    }

    public boolean passwordVerificationString(String passwordString, String passwordVerificationString){
        return passwordString.equals(passwordVerificationString);
    }
}
