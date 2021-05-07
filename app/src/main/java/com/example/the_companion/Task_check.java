package com.example.the_companion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.anychart.ui.contextmenu.Item;
import com.google.android.material.snackbar.Snackbar;

public class Task_check extends AppCompatActivity {
    TextView taskDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_check);
        String desc = getIntent().getSerializableExtra("TaskDescription").toString();
        taskDescription = findViewById(R.id.task_description);
        taskDescription.setText(desc);
    }
}