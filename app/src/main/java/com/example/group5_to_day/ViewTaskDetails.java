package com.example.group5_to_day;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.group5_to_day.models.Task;

public class ViewTaskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_details);

        TextView taskTitle = findViewById(R.id.task_detail_taskTitleField);
        TextView taskDescription = findViewById(R.id.task_detail_taskDescriptionField);
        TextView taskDeadlineDate = findViewById(R.id.task_detail_deadline_dateText);
        TextView taskDeadlineTime = findViewById(R.id.task_detail_deadline_timeText);
        TextView taskCheckedDate = findViewById(R.id.task_detail_lastchecked_text);
        TextView taskLabel = findViewById(R.id.task_detail_label_text);

        Intent intent = getIntent();
        Task task = (Task) intent.getSerializableExtra("TASK");

        taskTitle.setText(task.getTitle());
        taskDescription.setText(task.getDescription());
        taskDeadlineDate.setText(task.getDeadline().split(" ")[0]);
        taskDeadlineTime.setText(task.getDeadline().split(" ")[1]);
        taskCheckedDate.setText(task.getCheckedDate());
        taskLabel.setText(task.getLabel());
    }
}