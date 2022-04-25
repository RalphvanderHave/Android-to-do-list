package com.example.group5_to_day;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.group5_to_day.databases.AppDatabase;
import com.example.group5_to_day.methods.MainMethods;
import com.example.group5_to_day.models.Label;
import com.example.group5_to_day.models.Task;

import java.util.ArrayList;

public class EditTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Task task;
    private TextView taskTitle;
    private TextView taskDescription;
    private TextView taskDeadlineDate;
    private TextView taskDeadlineTime;
    private ArrayList<Label> labels = new ArrayList<>();
    private ArrayAdapter<Label> adapter;
    private Spinner taskSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        Button deleteButton = findViewById(R.id.task_edit_deadline_deleteTaskButton);
        deleteButton.setBackgroundColor(Color.RED);

        taskTitle = findViewById(R.id.task_edit_taskTitleField);
        taskDescription = findViewById(R.id.task_edit_taskDescriptionField);
        taskDeadlineDate = findViewById(R.id.task_edit_deadline_dateText);
        taskDeadlineTime = findViewById(R.id.task_edit_deadline_timeText);
        taskSpinner = findViewById(R.id.spinner);

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("TASK");

        taskTitle.setText(task.getTitle());
        taskDescription.setText(task.getDescription());
        taskDeadlineDate.setText(task.getDeadline().split(" ")[0]);
        taskDeadlineTime.setText(task.getDeadline().split(" ")[1]);

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
            runOnUiThread(() -> taskSpinner.setAdapter(adapter));
        });

        for (int i = 0; i < labels.size(); i++) {
            if (labels.get(i).getDescription().equals(task.getLabel())) {
                taskSpinner.setSelection(i);
            }
        }

        findViewById(R.id.task_edit_pickDateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        findViewById(R.id.task_edit_pickTimeButton).setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        }));
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

    public void saveChanges(View view) {
        task.setTitle(this.taskTitle.getText().toString());
        task.setDescription(this.taskDescription.getText().toString());
        task.setDeadline(this.taskDeadlineDate.getText().toString() + " " + taskDeadlineTime.getText().toString());
        if (taskSpinner.getSelectedItem().toString().equals("No Label")) {
            task.setLabel(null);
        } else {
            task.setLabel(taskSpinner.getSelectedItem().toString());
        }
        AppDatabase.getDbExecutor().execute(()->{
            AppDatabase.getDatabase(getApplicationContext()).taskDao().updateTask(task);
        });
        Intent intent = new Intent(this, ViewTasksActivity.class);
        startActivity(intent);
    }

    public void deleteTask(View view) {
        AppDatabase.getDbExecutor().execute(()->{
            AppDatabase.getDatabase(getApplicationContext()).taskDao().deleteTask(task);
        });
        Intent intent = new Intent(this, ViewTasksActivity.class);
        startActivity(intent);
    }

    public void goToManageLabel(View view) {
        Intent intent = new Intent(this, ViewLabelsActivity.class);
        startActivity(intent);
    }
}