package com.example.the_companion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.firebase.auth.FirebaseAuth;

public class Profile_page extends AppCompatActivity {
    TextView textview;
    TextView textview1;
    TextView textview2;
    ImageView chart;
    ImageView contact;
    ImageView donation;
    private Button LogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile_page);


        textview = findViewById(R.id.weekly_report_text);
        textview1 = findViewById(R.id.contact_us_text);
        textview2 = findViewById(R.id.donation_text);
        chart = findViewById(R.id.weekly_report_icon);
        contact = findViewById(R.id.contact_us_icon);
        donation = findViewById(R.id.donation_icon);

        return_dash();

        weekly_report();

        contact_us();

        log_out();

        YoYo.with(Techniques.Bounce).duration(2000).repeat(100).playOn(textview);
        YoYo.with(Techniques.Pulse).duration(2500).repeat(100).playOn(textview1);
        YoYo.with(Techniques.Pulse).duration(2500).repeat(100).playOn(textview2);
        YoYo.with(Techniques.Bounce).duration(2500).repeat(100).playOn(chart);
        YoYo.with(Techniques.Pulse).duration(2500).repeat(100).playOn(contact);
        YoYo.with(Techniques.Pulse).duration(2500).repeat(100).playOn(donation);
    }

    private void log_out() {
        LogOut = (Button) findViewById(R.id.LogOut);
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile_page.this, login_screen.class));
                finish();
            }
        });
    }

    private void return_dash(){
        ImageView home = (ImageView) findViewById(R.id.home_icon);
        home.setOnClickListener(v -> startActivity(new Intent(Profile_page.this, AddTaskActivity.class)));

    }
    private void weekly_report(){
        textview.setOnClickListener(v -> startActivity(new Intent(Profile_page.this,Chart.class)));


    }

    private void contact_us(){
        TextView contact = findViewById(R.id.contact_us_text);
        contact.setOnClickListener(v -> startActivity(new Intent(Profile_page.this, about_us.class)));
    }


}