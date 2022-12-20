package com.example.final_project_1_todolist_lisma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView addBtn;
    Button completeBtn;
    AlertDialog.Builder builder;
    RecyclerView recyclerView;
    Database db;
    ArrayList<String> task_id, task_title;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = findViewById(R.id.addBtn);
        completeBtn = findViewById(R.id.completeBtn);
        recyclerView = findViewById(R.id.recyclerView);
        db = new Database(MainActivity.this);
        task_id = new ArrayList<>();
        task_title = new ArrayList<>();
        builder = new AlertDialog.Builder(this);
        storeDataInArrays();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText taskEditText = new EditText(MainActivity.this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                taskEditText.setLayoutParams(layoutParams);
                builder.setTitle("Add a new Task");
                builder.setMessage("What do you want to do next?");
                builder.setCancelable(true);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Database db = new Database(MainActivity.this);
                        db.addTask(taskEditText.getText().toString().trim());
                        task_id.clear();
                        task_title.clear();
                        storeDataInArrays();
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).setView(taskEditText).show();
            }
        });

        adapter = new Adapter(MainActivity.this, task_id, task_title);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }

    void storeDataInArrays() {
        Cursor cursor = db.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0));
                task_title.add(cursor.getString(1));
            }
        }
    }
}