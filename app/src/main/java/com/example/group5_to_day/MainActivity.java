package com.example.group5_to_day;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.group5_to_day.CommonUserInterfaceMethods.shortToast;

import com.example.group5_to_day.databases.AppDatabase;
import com.example.group5_to_day.methods.MainMethods;
import com.example.group5_to_day.models.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static User loggedInUser;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase.getDbExecutor().execute(()-> {
            users = new ArrayList<>(
                    AppDatabase
                            .getDatabase(this)
                            .userDao()
                            .getAll());
        });
    }

    public void goToViewTasksActivity(View view) {
        EditText usernameInputField = findViewById(R.id.main_usernameInputField);
        EditText passwordInputField = findViewById(R.id.main_passwordInputField);
        String usernameString = usernameInputField.getText().toString();
        String passwordString = passwordInputField.getText().toString();

        if (usernameString.equals("")) {
            shortToast(getApplicationContext(), "Error: Missing Username.");
        } else if (passwordString.equals("")) {
            shortToast(getApplicationContext(), "Error: Missing Password.");
        } else {
            int indexOfUser = MainMethods.getUserIndex(users, usernameString);

            if (indexOfUser == -1) {
                shortToast(getApplicationContext(), "Error: User not found, please register first.");
            } else if (!MainMethods.validPassword(users, indexOfUser, passwordString)) {
                shortToast(getApplicationContext(), "Error: Incorrect password");
            } else {
                loggedInUser = users.get(indexOfUser);
                shortToast(getApplicationContext(), "Welcome, " + loggedInUser.getUsername());

                Intent intent = new Intent(this, ViewTasksActivity.class);
                startActivity(intent);
            }
        }
    }

    public void goToRegisterUser(View view) {
        Intent intent = new Intent(this, RegisterUserActivity.class);
        startActivity(intent);
    }
}