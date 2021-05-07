package com.example.the_companion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AddTaskActivity extends AppCompatActivity {
    Button addTask;
    ListView list;
    ArrayList<Task> tasksList = new ArrayList<>();
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_list);
        list = (ListView)findViewById(R.id.listView);

        db = FirebaseFirestore.getInstance();

        final CollectionReference itemCollectionReference = db.collection("Tasks");


        tasksList.add(new Task("Get Pizza"));
        tasksList.add(new Task("Lock the door"));


        ArrayAdapter taskAdapter= new CustomArrayAdapter(this,tasksList);
        list.setAdapter(taskAdapter);

        itemCollectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                tasksList.clear();
                if(error != null){
                    Log.d("Sample","Error"+error.getMessage());
                }
                else{
                    for(QueryDocumentSnapshot doc: queryDocumentSnapshots){
                        Log.d("Sample",String.valueOf(doc.getData().get("task_id")));
                        String taskId = (String) doc.getData().get("task_id");
                        String description = (String) doc.getData().get("task_description");

                        Task task = new Task(taskId, description);
                        tasksList.add(task);
                    }
                }
                taskAdapter.notifyDataSetChanged();
            }
        });
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