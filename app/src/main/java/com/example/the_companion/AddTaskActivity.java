package com.example.the_companion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class AddTaskActivity extends AppCompatActivity {
    ListView list;
    ArrayList<Task> tasksList = new ArrayList<>();
    FirebaseFirestore db;
    public static Task task = new Task();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_list);
        list = (ListView)findViewById(R.id.listView);
        db = FirebaseFirestore.getInstance();

        final CollectionReference itemCollectionReference = db.collection("Tasks");


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
                        String compulsioncounter = (String) doc.getData().get("task_compulsion");
                        String taskDate = (String)doc.getData().get("task_time");
                        String filepath = (String)doc.getData().get("image_path");
                        Task task = new Task(taskId, description);
                        task.setTaskImage(filepath);
                        task.setDateAndTime(taskDate);
                        task.setCompulsioncheck(compulsioncounter);
                        tasksList.add(task);
                    }
                }
                //Collections.reverse(tasksList);
                taskAdapter.notifyDataSetChanged();
            }
        });

        list.setOnItemLongClickListener((parent, view, position, id) -> {
            view.setSelected(true);
            final int selectedItem = position;
            final Task task = tasksList.get(selectedItem);

            new AlertDialog.Builder(AddTaskActivity.this)
                    .setIcon(android.R.drawable.ic_delete)
                    .setTitle("Are you sure")
                    .setMessage("Would you like to delete this item?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db.collection("Tasks").document(task.getTaskId()).delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("Okay", "DocumentSnapshot sucessfully deleted");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w("Error", "Error deleting song");
                                        }
                                    });
                            tasksList.remove(selectedItem);
                            taskAdapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return false;
        });

        list.setOnItemClickListener((parent, view, position, id) -> {
            this.task = (Task) list.getItemAtPosition(position);

            Intent intent = new Intent(AddTaskActivity.this, Task_check.class);
            intent.putExtra("TaskDescription", this.task.getTaskDescription());
            intent.putExtra("Compulsion",String.valueOf(Integer.parseInt(this.task.getCompulsioncheck())+1));
            intent.putExtra("TaskId",this.task.getTaskId());
            intent.putExtra("TaskTime", this.task.getDateAndTime());
            intent.putExtra("TaskImage", this.task.getTaskImage());
            startActivity(intent);
        });

        nextTaskPage();
        goProfile();
    }

    private void goProfile(){
        ImageView goProfile = (ImageView) findViewById(R.id.user_icon);
        goProfile.setOnClickListener(v -> startActivity(new Intent(AddTaskActivity.this, Profile_page.class)));
    }

    private void nextTaskPage(){
        Button addTask = (Button) findViewById(R.id.addtaskbutton);
        addTask.setOnClickListener(v -> startActivity(new Intent(AddTaskActivity.this,Add_task.class)));
    }


}