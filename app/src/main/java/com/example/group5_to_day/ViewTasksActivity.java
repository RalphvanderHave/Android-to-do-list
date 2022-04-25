package com.example.group5_to_day;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.example.group5_to_day.databases.AppDatabase;
import com.example.group5_to_day.models.Task;

import static com.example.group5_to_day.CommonUserInterfaceMethods.shortToast;

import java.util.ArrayList;

public class ViewTasksActivity extends AppCompatActivity {
    private ArrayList<Task> tasks;
    private ArrayList<Task> tasksToBeChecked = new ArrayList<>();
    private ArrayAdapter<Task> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks);

        this.listView = (ListView) findViewById(R.id.view_tasks_ListView);
        AppDatabase.getDbExecutor().execute(()-> {
            tasks = new ArrayList<>(
                    AppDatabase
                            .getDatabase(this)
                            .taskDao()
                            .getUncheckedTasksFromUser(MainActivity.loggedInUser.getUsername()));
            adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_checked,
                    tasks);
            runOnUiThread(() -> this.listView.setAdapter(this.adapter));
        });

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView v = (CheckedTextView) view;
                v.toggle();

                if (v.isChecked()) {
                    tasksToBeChecked.add(tasks.get(position));
                } else {
                    tasksToBeChecked.remove(tasks.get(position));
                }
            }
        });

        this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                shortToast(getApplicationContext(), "Details of " + tasks.get(position).getTitle());
                goToTaskDetailsActivity(tasks.get(position));
                return false;
            }
        });
    }

    public void checkSelectedTasks(View view) {
        if (tasksToBeChecked.size() == 0) {
            shortToast(getApplicationContext(), "No tasks selected");
        } else {
            for (int i = 0; i < tasksToBeChecked.size(); i++) {
                Task task = tasksToBeChecked.get(i);
                task.setCheckedDate(System.currentTimeMillis());
                AppDatabase.getDbExecutor().execute(()->{
                    AppDatabase.getDatabase(getApplicationContext()).taskDao().updateTask(task);
                });
            }
            Intent intent = new Intent(this, ViewTasksActivity.class);
            startActivity(intent);
        }
    }

    public void goToCreateTask(View view) {
        Intent intent = new Intent(this, CreateTaskActivity.class);
        startActivity(intent);
    }

    public void goToTaskDetailsActivity(Task task) {
        Intent intent = new Intent(this, ViewTaskDetails.class);
        intent.putExtra("TASK", task);
        startActivity(intent);
    }

    public void goToViewAllTasksActivity(View view) {
        Intent intent = new Intent(this, ViewCheckedTasksActivity.class);
        startActivity(intent);
    }

    public void goToEditTasksActivity(View view) {
        Intent intent = new Intent(this, ViewTasksToEditActivity.class);
        startActivity(intent);
    }
}