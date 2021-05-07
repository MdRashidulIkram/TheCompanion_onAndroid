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
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
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