package com.example.group5_to_day;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import static com.example.group5_to_day.CommonUserInterfaceMethods.shortToast;

import java.util.ArrayList;
import com.example.group5_to_day.databases.AppDatabase;
import com.example.group5_to_day.methods.MainMethods;
import com.example.group5_to_day.models.Label;
import com.example.group5_to_day.models.Task;

public class CreateTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private TextView taskDeadlineDate;
    private TextView taskDeadlineTime;
    private ArrayList<Label> labels = new ArrayList<>();
    private ArrayAdapter<Label> adapter;
    private Spinner taskLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        taskDeadlineDate = findViewById(R.id.create_task_date_text_view);
        taskDeadlineTime = findViewById(R.id.create_task_time_text_view);
        taskLabel = findViewById(R.id.create_task_spinner);

        AppDatabase.getDbExecutor().execute(()-> {
            labels = new ArrayList<>(
                    AppDatabase
                            .getDatabase(this)
                            .labelDao()
                            .getAll());
            labels.add(0, new Label("No Label"));
            adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_item,
                    labels);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            runOnUiThread(() -> taskLabel.setAdapter(adapter));
        });

        findViewById(R.id.create_task_date_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });

        findViewById(R.id.create_task_time_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                showTimePickerDialog();
            }
        });
    }

    public void createThisTask(View view) {
        EditText titleTaskTextField = findViewById(R.id.create_task_task_title_textfield);
        EditText descriptionTaskField = findViewById(R.id.create_task_task_description_textfield);

        String titleTask = titleTaskTextField.getText().toString();
        String descriptionTask = descriptionTaskField.getText().toString();
        String time = taskDeadlineTime.getText().toString();
        String date = taskDeadlineDate.getText().toString();
        String deadlineTask = taskDeadlineDate.getText().toString() + " " + taskDeadlineTime.getText().toString();
        String label = taskLabel.getSelectedItem().toString();

        if (titleTask.equals("")) {
            shortToast(getApplicationContext(), "Missing title");
        } else if (descriptionTask.equals("")) {
            shortToast(getApplicationContext(), "Missing description");
        } else if (date.equals("")) {
            shortToast(getApplicationContext(), "Missing date");
        } else if (time.equals("")) {
            shortToast(getApplicationContext(), "Missing time");
        } else {
            Task task;
            if (label.equals("No Label")) {
                task = new Task(titleTask, descriptionTask, deadlineTask , MainActivity.loggedInUser.getUsername());
            } else {
                task = new Task(titleTask, descriptionTask, deadlineTask , MainActivity.loggedInUser.getUsername(),label);
            }
            AppDatabase.getDbExecutor().execute(() -> {
                AppDatabase.getDatabase(getApplicationContext()).taskDao().insertTask(task);
            });
            shortToast(getApplicationContext(), "Task created");
            Intent intent = new Intent(this, ViewTasksActivity.class);
            startActivity(intent);
        }
    }

    public void goToManageLabel(View view) {
        Intent intent = new Intent(this, ViewLabelsActivity.class);
        startActivity(intent);
    }

    private void showTimePickerDialog() {
        CommonUserInterfaceMethods.showTimePickerDialog(this, this);
    }

    private void showDatePickerDialog() {
        CommonUserInterfaceMethods.showDatePickerDialog(this, this);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = MainMethods.padDateToString(dayOfMonth, month, year);
        taskDeadlineDate.setText(date);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = MainMethods.padTimeToString(hourOfDay, minute);
        taskDeadlineTime.setText(time);
    }
}