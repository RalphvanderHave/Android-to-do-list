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

public class ViewCheckedTasksActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Task> checkedTasks;
    private ArrayList<Task> uncheckedTasks = new ArrayList<>();
    private ArrayAdapter<Task> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_checked_tasks);

        listView = (ListView) findViewById(R.id.ViewAllTasksListView);
        AppDatabase.getDbExecutor().execute(()-> {
            checkedTasks = new ArrayList<>(
                    AppDatabase
                            .getDatabase(this)
                            .taskDao()
                            .getCheckedTasksFromUser(MainActivity.loggedInUser.getUsername()));
            adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_checked,
                    this.checkedTasks);
            runOnUiThread(() -> listView.setAdapter(adapter));
        });

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView v = (CheckedTextView) view;
                v.toggle();

                if (v.isChecked()) {
                    uncheckedTasks.add(checkedTasks.get(position));
                } else {
                    uncheckedTasks.remove(checkedTasks.get(position));
                }
            }
        });

        this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                shortToast(getApplicationContext(), "Details of " + checkedTasks.get(position).getTitle());
                goToTaskDetailsActivity(checkedTasks.get(position));
                return false;
            }
        });
    }

    public void uncheckSelectedTasks(View view) {
        for (int i = 0; i < uncheckedTasks.size(); i++) {
            Task task = uncheckedTasks.get(i);
            task.setCheckedDate(null);
            AppDatabase.getDbExecutor().execute(()->{
                AppDatabase.getDatabase(getApplicationContext()).taskDao().updateTask(task);
            });
        }
        Intent intent = new Intent(this, ViewTasksActivity.class);
        startActivity(intent);
    }

    public void goToTaskDetailsActivity(Task task) {
        Intent intent = new Intent(this, ViewTaskDetails.class);
        intent.putExtra("TASK", task);
        startActivity(intent);
    }

}