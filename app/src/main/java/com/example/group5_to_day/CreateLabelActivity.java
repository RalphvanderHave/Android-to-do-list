package com.example.group5_to_day;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.group5_to_day.databases.AppDatabase;
import com.example.group5_to_day.models.Label;

import static com.example.group5_to_day.CommonUserInterfaceMethods.shortToast;

import java.util.ArrayList;

public class CreateLabelActivity extends AppCompatActivity {
    private ArrayList<Label> labels;
    private EditText labelDescriptionInputField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_label);
        labelDescriptionInputField = findViewById(R.id.create_label_description);

        AppDatabase.getDbExecutor().execute(()-> {
            labels = new ArrayList<>(
                    AppDatabase
                            .getDatabase(this)
                            .labelDao()
                            .getAll());
        });
    }

    public void createNewLabel(View view) {
        String labelDescription = labelDescriptionInputField.getText().toString();

        if (labelDescription.equals("")) {
            shortToast(getApplicationContext(), "No label given");
        } else {
            Label label = new Label(labelDescription);

            if (labels.contains(label)) {
                shortToast(getApplicationContext(), "This label already exists");
            } else {
                AppDatabase.getDbExecutor().execute(() -> {
                    AppDatabase.getDatabase(getApplicationContext()).labelDao().insertLabel(label);
                });
                shortToast(getApplicationContext(), "Label created");

                Intent intent = new Intent(this, ViewLabelsActivity.class);
                startActivity(intent);
            }
        }
    }
}