package com.example.the_companion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Profile_page extends AppCompatActivity {
    TextView textview;
    TextView textview1;
    TextView textview2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile_page);

        textview = findViewById(R.id.textView3);
        textview1 = findViewById(R.id.textView5);
        textview2 = findViewById(R.id.textView6);

        YoYo.with(Techniques.Shake).duration(2500).repeat(100).playOn(textview);
        YoYo.with(Techniques.Pulse).duration(2500).repeat(100).playOn(textview1);
        YoYo.with(Techniques.Pulse).duration(2500).repeat(100).playOn(textview2);
    }
}