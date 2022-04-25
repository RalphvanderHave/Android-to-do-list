package com.example.group5_to_day;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.example.group5_to_day.databases.AppDatabase;
import com.example.group5_to_day.models.Label;

import static com.example.group5_to_day.CommonUserInterfaceMethods.shortToast;

import java.util.ArrayList;

public class ViewLabelsActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<Label> labels;
    private ArrayList<Label> checkedLabels = new ArrayList<>();
    private ArrayAdapter<Label> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_labels);

        Button deleteButton = findViewById(R.id.view_labels_delete_button);
        deleteButton.setBackgroundColor(Color.RED);

        listView = findViewById(R.id.view_labels_listview);
        AppDatabase.getDbExecutor().execute(()-> {
            labels = new ArrayList<>(
                    AppDatabase
                            .getDatabase(this)
                            .labelDao()
                            .getAll());
            adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_checked,
                    labels);
            runOnUiThread(() -> listView.setAdapter(adapter));
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView v = (CheckedTextView) view;
                v.toggle();

                if (v.isChecked()) {
                    checkedLabels.add(labels.get(position));
                } else {
                    checkedLabels.remove(labels.get(position));
                }
            }
        });
    }

    public void deleteSelectedLabels(View view) {
        if (checkedLabels.size() == 0) {
            shortToast(getApplicationContext(), "No labels selected");
        } else {
            for (int i = 0; i < checkedLabels.size(); i++) {
                Label label = checkedLabels.get(i);
                labels.remove(label);
                AppDatabase.getDbExecutor().execute(()->{
                    AppDatabase.getDatabase(getApplicationContext()).labelDao().deleteLabel(label);
                });
            }
            shortToast(getApplicationContext(), "Selected Labels Deleted");
            adapter.notifyDataSetChanged();
        }
    }

    public void goToCreateLabelActivity(View view) {
        Intent intent = new Intent(this, CreateLabelActivity.class);
        startActivity(intent);
    }
}