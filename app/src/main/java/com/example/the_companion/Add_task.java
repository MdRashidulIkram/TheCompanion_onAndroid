package com.example.the_companion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Add_task extends AppCompatActivity {
    ImageView camlogo;
    Button take;
    Button doneButton;
    EditText taskDescription;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String input = taskDescription.getText().toString();

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_task);
        taskDescription = findViewById(R.id.taskinput);
        doneButton = findViewById(R.id.done);
        db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);

        useCamera();

        configureNextAddtask();

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    HashMap<String, Object> data = new HashMap<>();
                    String description = taskDescription.getText().toString();
                    String taskId = String.valueOf(Timestamp.now().hashCode());
                    data.put("task_description", description);
                    data.put("task_id", taskId);

                    db.collection("Tasks").document(taskId)
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Snackbar.make(v, "Successfully added to firebase", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Snackbar.make(v, "Failed to add to firebase", Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                }
                            });


                    startActivity(new Intent(Add_task.this, AddTaskActivity.class));
                }

        });
    }
    private void useCamera(){
        camlogo = findViewById(R.id.camera);
        take = findViewById(R.id.picbutton);

        if(ContextCompat.checkSelfPermission(Add_task.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Add_task.this, new String[]{Manifest.permission.CAMERA},100);
        }
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==100){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            camlogo.setImageBitmap(bitmap);
        }
    }
    private void configureNextAddtask(){
        ImageView nextAddtask = (ImageView) findViewById(R.id.imageView5);
        nextAddtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Add_task.this, Profile_page.class));
            }
        });
    }
}