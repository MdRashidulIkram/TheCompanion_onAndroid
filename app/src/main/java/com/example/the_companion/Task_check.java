package com.example.the_companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Task_check extends AppCompatActivity {
    TextView taskDescription, taskDate;
    String compulsion,taskid;
    TextView warning;
    Task task;
    private FirebaseFirestore db;
    ImageView taskImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_check);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.db = FirebaseFirestore.getInstance();
        final CollectionReference collectionReference = db.collection("Tasks");

        taskImageView = findViewById(R.id.task_image);
        warning = findViewById(R.id.congratz_text);
        String desc = getIntent().getSerializableExtra("TaskDescription").toString();
        taskid = getIntent().getSerializableExtra("TaskId").toString();
        this.compulsion = getIntent().getSerializableExtra("Compulsion").toString();
        String timestamp = getIntent().getSerializableExtra("TaskTime").toString();
        String filepath = getIntent().getSerializableExtra("TaskImage").toString();
        Snackbar.make(findViewById(R.id.task_image), filepath, Snackbar.LENGTH_LONG);

        task = new Task(taskid,desc);

        task.setCompulsioncheck(this.compulsion);
        if(Integer.parseInt(this.compulsion) > 3){
            warning.setText("Relax, you have checked this task " + this.compulsion + " times!" + " Have a coffee!");
        }
        taskDescription = findViewById(R.id.task_description);
        taskDate = findViewById(R.id.time_stamp_text);
        taskDescription.setText(desc);

        //Toast.makeText(Task_check.this, filepath, Toast.LENGTH_LONG).show();
        StorageReference imgStorageReference = FirebaseStorage.getInstance().getReference().child("images/");

            imgStorageReference.getFile(new File(filepath))
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(Task_check.this, "Image received", Toast.LENGTH_LONG).show();
                            Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                            taskImageView.setImageBitmap(bitmap);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener(){
                        @Override
                        public void onFailure(@NonNull Exception e){
                            Toast.makeText(Task_check.this, "Image failed", Toast.LENGTH_LONG).show();

                        }
                    });


        if (!timestamp.equals(null)){
            taskDate.setText(timestamp);
        }
        return_dash(collectionReference);
        return_profile();
    }
    private void return_dash(CollectionReference collectionReference){
        ImageView home = (ImageView) findViewById(R.id.home_icon);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputTask(task,v,collectionReference);
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
    public void InputTask(Task task, View view, CollectionReference collectionReference) {

        HashMap<String, Object> data = new HashMap<>();
        data.put("task_compulsion",compulsion);

        collectionReference
                .document(taskid)
                .update(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Hello","Data addition successful");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Hello", "Data addition failed" + e.toString());
                    }
                });

    }
}