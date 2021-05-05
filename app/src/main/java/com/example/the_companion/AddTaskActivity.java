package com.example.the_companion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class AddTaskActivity extends AppCompatActivity {
Button addTask;
ListView list;
ArrayList<String> tasksList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_list);
        list = (ListView)findViewById(R.id.listView);

        tasksList.add("Get Pizza");
        tasksList.add("Lock the door");
        tasksList.add("Cheese");
        tasksList.add("Turn off the faucet");
        tasksList.add("go to sleep");
        tasksList.add("Cheese");
        tasksList.add("Turn off the faucet");
        tasksList.add("go to sleep");

        ArrayAdapter taskAdapter= new ArrayAdapter(this, android.R.layout.simple_list_item_1,tasksList);
        list.setAdapter(taskAdapter);

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