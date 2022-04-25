package com.example.group5_to_day;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.group5_to_day.databases.AppDatabase;
import com.example.group5_to_day.models.Task;

import static com.example.group5_to_day.CommonUserInterfaceMethods.shortToast;

import java.util.ArrayList;

public class ViewTasksToEditActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Task> tasks;
    private ArrayAdapter<Task> adapter;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tasks_to_edit);

        listView = findViewById(R.id.edit_tasks_listview);
        AppDatabase.getDbExecutor().execute(()-> {
            tasks = new ArrayList<>(
                    AppDatabase
                            .getDatabase(this)
                            .taskDao()
                            .getAllTasksFromUser(MainActivity.loggedInUser.getUsername()));
            adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    tasks);
            runOnUiThread(() -> this.listView.setAdapter(this.adapter));
        });

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                shortToast(getApplicationContext(), "To: " + tasks.get(position).getTitle());
                task = tasks.get(position);
                goToEditTask(task);
            }
        });
    }

    private void goToEditTask(Task task) {
        Intent intent = new Intent(this, EditTaskActivity.class);
        intent.putExtra("TASK", task);
        startActivity(intent);
    }
}