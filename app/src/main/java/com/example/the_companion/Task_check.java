package com.example.the_companion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.anychart.ui.contextmenu.Item;
import com.google.android.material.snackbar.Snackbar;

public class Task_check extends AppCompatActivity {
    TextView taskDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_check);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String desc = getIntent().getSerializableExtra("TaskDescription").toString();
        taskDescription = findViewById(R.id.task_description);
        taskDescription.setText(desc);
        return_dash();
        return_profile();
    }
    private void return_dash(){
        ImageView home = (ImageView) findViewById(R.id.home_icon);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Task_check.this, AddTaskActivity.class));
            }
        });
    }
    private void return_profile(){
        ImageView user = (ImageView) findViewById(R.id.user_icon);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Task_check.this,Profile_page.class ));
            }
        });
    }

}