package com.example.the_companion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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

        textview = findViewById(R.id.weekly_report_text);
        textview1 = findViewById(R.id.contact_us_text);
        textview2 = findViewById(R.id.donation_text);

        return_dash();
        weekly_report();
        contact_us();

        YoYo.with(Techniques.Bounce).duration(2000).repeat(100).playOn(textview);
        YoYo.with(Techniques.Pulse).duration(2500).repeat(100).playOn(textview1);
        YoYo.with(Techniques.Pulse).duration(2500).repeat(100).playOn(textview2);
    }
    private void return_dash(){
        ImageView home = (ImageView) findViewById(R.id.home_icon);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile_page.this, Dashboard.class));
            }
        });

    }
    private void weekly_report(){
        textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile_page.this,Chart.class));
            }
        });


    }

    private void contact_us(){
        TextView contact = (TextView) findViewById(R.id.contact_us_text);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile_page.this, about_us.class));
            }
        });
    }
}