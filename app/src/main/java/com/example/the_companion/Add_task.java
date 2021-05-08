package com.example.the_companion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class Add_task extends AppCompatActivity {
    ImageView camlogo;
    Button take;
    Button doneButton;
    EditText taskDescription;
    StorageReference storageReference;
    FirebaseStorage storage;
    private Uri filePath;

    FirebaseFirestore db;
    private Bitmap bitmap;
    private boolean picTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_task);
        taskDescription = findViewById(R.id.taskinput);
        doneButton = findViewById(R.id.done);
        db = FirebaseFirestore.getInstance();
        FirebaseFirestore.setLoggingEnabled(true);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        useCamera();

        configureNextAddtask();

        return_dash();


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(taskDescription.getText().toString().equals("")){
                    taskDescription.setError("Task Description required");
                    return;
                }

                if (!picTaken){
                    Snackbar.make(v, "No Image added", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
              
                HashMap<String, Object> data = new HashMap<>();
                String description = taskDescription.getText().toString();
                String taskId = String.valueOf(Timestamp.now().hashCode());
                data.put("task_compulsion",0);
                data.put("task_description", description);
                data.put("task_id", taskId);

                db.collection("Tasks").document(taskId)
                        .set(data)
                        .addOnSuccessListener(aVoid -> Snackbar.make(v, "Successfully added to firebase", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show())
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(v, "Failed to add to firebase", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        });


                startActivity(new Intent(Add_task.this,AddTaskActivity.class));
            }
        });
    }
    private void useCamera(){
        camlogo = findViewById(R.id.camera_icon);
        take = findViewById(R.id.picbutton);

        if(ContextCompat.checkSelfPermission(Add_task.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Add_task.this, new String[]{Manifest.permission.CAMERA},100);
        }

        take.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            this.bitmap = (Bitmap) data.getExtras().get("data");
            this.picTaken = true;
            camlogo.setImageBitmap(bitmap);
            if (filePath != null) {
                ProgressDialog progressDialog
                        = new ProgressDialog(this);
                progressDialog.setTitle("Uploading...");
                progressDialog.show();
                filePath = data.getData();
                // Defining the child of storageReference
                StorageReference ref
                        = storageReference.child(
                        "images/"
                                + UUID.randomUUID().toString());

                // adding listeners on upload
                // or failure of image
                ref.putFile(filePath)
                        .addOnSuccessListener(
                                new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                    @Override
                                    public void onSuccess(
                                            UploadTask.TaskSnapshot taskSnapshot) {

                                        // Image uploaded successfully
                                        // Dismiss dialog
                                        progressDialog.dismiss();
                                        Toast.makeText(Add_task.this, "Image Uploaded!!", Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                // Error, Image not uploaded
                                progressDialog.dismiss();
                                Toast
                                        .makeText(Add_task.this,
                                                "Failed " + e.getMessage(),
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .addOnProgressListener(
                                new OnProgressListener<UploadTask.TaskSnapshot>() {

                                    // Progress Listener for loading
                                    // percentage on the dialog box
                                    @Override
                                    public void onProgress(
                                            UploadTask.TaskSnapshot taskSnapshot) {
                                        double progress
                                                = (100.0
                                                * taskSnapshot.getBytesTransferred()
                                                / taskSnapshot.getTotalByteCount());
                                        progressDialog.setMessage(
                                                "Uploaded "
                                                        + (int) progress + "%");
                                    }
                                });
            }
        }
    }
    private void configureNextAddtask(){
        ImageView nextAddtask = (ImageView) findViewById(R.id.user_icon);
        nextAddtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Add_task.this, Profile_page.class));
            }
        });
    }
    private void return_dash(){
        ImageView home = (ImageView) findViewById(R.id.home_icon);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}