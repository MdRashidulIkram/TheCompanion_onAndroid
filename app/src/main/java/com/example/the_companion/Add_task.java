package com.example.the_companion;

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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class Add_task extends AppCompatActivity {
    ImageView camlogo;
    Button take;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_add_task);
        useCamera();

        configureNextAddtask();

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