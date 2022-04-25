package com.example.group5_to_day;

import com.example.group5_to_day.methods.MainMethods;
import com.example.group5_to_day.models.User;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class MainMethodsTest {
    @Test
    public void userindex_returns_correct_index_of_username_of_arraylist_of_users() {
        //ARRANGE
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("user1", "password1"));
        String userName = "user1";
        //ACT
        int result = MainMethods.getUserIndex(users, userName);
        //ASSERT
        assertEquals(result, 0);
    }

    @Test
    public void userindex_returns_minus_1_when_user_not_in_arraylist_of_users() {
        //ARRANGE
        ArrayList<User> users = new ArrayList<>();
        User user = new User("user1", "password1");
        users.add(user);
        //ACT
        int result = MainMethods.getUserIndex(users, "user2");
        //ASSERT
        assertEquals(result, -1);
    }

    @Test
    public void validpassword_returns_true_when_password_matches() {
        //ARRANGE
        ArrayList<User> users = new ArrayList<>();
        User user = new User("user", "password");
        users.add(user);
        int index = 0;
        //ACT
        boolean result = MainMethods.validPassword(users, index, user.getPassword());
        //ASSERT
        assertTrue(result);
    }

    @Test
    public void validpassword_returns_false_when_password_does_not_match() {
        //ARRANGE
        ArrayList<User> users = new ArrayList<>();
        User user = new User("user", "password");
        users.add(user);
        int index = 0;
        //ACT
        boolean result = MainMethods.validPassword(users, index, "Wrong password");
        //ASSERT
        assertFalse(result);
    }

    @Test
    public void padtimetostring_pads_time_values_below_10() {
        //ARRANGE
        int hourOfDay = 1;
        int minute = 1;
        //ACT
        String result = MainMethods.padTimeToString(hourOfDay, minute);
        //ASSERT
        assertEquals(result, "01:01");
    }

    @Test
    public void padtimetostring_does_not_pad_time_values_of_10_or_above() {
        //ARRANGE
        int hourOfDay = 10;
        int minute = 10;
        //ACT
        String result = MainMethods.padTimeToString(hourOfDay, minute);
        //ASSERT
        assertEquals(result, "10:10");
    }

    @Test
    public void paddatetostring_does_not_pad_date_values_of_10_or_above() {
        //ARRANGE
        int dayOfMonth = 10;
        int monthStartsAtZero = 10;
        int year = 2021;
        //ACT
        String result = MainMethods.padDateToString(dayOfMonth, monthStartsAtZero, year);
        //ASSERT
        assertEquals(result, "10/11/2021");
    }

    @Test
    public void passwordVerificationString_returns_true_when_passwords_match() {
        //ARRANGE
        MainMethods mainMethods = new MainMethods();
        //ACT
        boolean result = mainMethods.passwordVerificationString("Hey", "Hey");
        //ASSERT
        assertTrue(result);
    }

    @Test
    public void passwordVerificationString_returns_false_when_passwords_dont_match() {
        //ARRANGE
        MainMethods mainMethods = new MainMethods();
        //ACT
        boolean result = mainMethods.passwordVerificationString("Hey", "Hallo");
        //ASSERT
        assertFalse(result);
    }
}