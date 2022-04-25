package com.example.group5_to_day;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.group5_to_day.databases.AppDatabase;
import com.example.group5_to_day.models.User;

import java.util.ArrayList;

import static com.example.group5_to_day.CommonUserInterfaceMethods.shortToast;

public class RegisterUserActivity extends AppCompatActivity {
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        AppDatabase.getDbExecutor().execute(()-> {
            users = new ArrayList<>(
                    AppDatabase
                            .getDatabase(this)
                            .userDao()
                            .getAll());
        });
    }

    public void createUser(View view) {
        EditText usernameInputField = findViewById(R.id.register_user_usernameInputField);
        EditText passwordInputField = findViewById(R.id.register_user_passwordInputField);
        EditText passwordVerificationInputField = findViewById(R.id.register_user_passwordVerificationInputField);
        String userNameString = usernameInputField.getText().toString();
        String passwordString = passwordInputField.getText().toString();
        String passwordVerificationString = passwordVerificationInputField.getText().toString();

        ArrayList<String> listOfUsernames = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            listOfUsernames.add(users.get(i).getUsername());
        }

        if (userNameString.equals("")) {
            shortToast(getApplicationContext(), "Error: Missing Username.");
        } else if (passwordString.equals("") || passwordVerificationString.equals("")) {
            shortToast(getApplicationContext(), "Error: Missing Password.");
        } else if (!passwordString.equals(passwordVerificationString)) {
            shortToast(getApplicationContext(), "Error: Passwords do not match.");
        } else if (listOfUsernames.contains(userNameString)) {
            shortToast(getApplicationContext(), "Error: Username is taken.");
        } else {
            User user = new User(userNameString, passwordString);
            AppDatabase.getDbExecutor().execute(()->{
                AppDatabase.getDatabase(getApplicationContext()).userDao().insertUser(user);
            });

            shortToast(getApplicationContext(), "User " + user.getUsername() + " created!");

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}