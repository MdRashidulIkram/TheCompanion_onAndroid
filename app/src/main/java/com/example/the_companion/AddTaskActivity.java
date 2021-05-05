package com.example.the_companion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class AddTaskActivity extends AppCompatActivity {
Button addTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_list);
        nextTaskPage();
    }

    private void nextTaskPage(){
        Button addTask = (Button) findViewById(R.id.addtaskbutton);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddTaskActivity.this,Add_task.class));
            }
        });
    }
}