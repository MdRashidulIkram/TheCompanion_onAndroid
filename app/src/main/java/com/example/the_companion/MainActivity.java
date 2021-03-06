package com.example.the_companion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //variables
    Animation topAnim;
    Animation bottomAnim;
    View view;
    ImageView image;
    TextView welcome, slogan;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        configureNextButton();

        //Animations
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        view = findViewById(R.id.view2);
        image = findViewById(R.id.mainlogo);
        welcome = findViewById(R.id.register_text);
        slogan = findViewById(R.id.disclaimer_text);
        start = findViewById(R.id.get_started_button);


        image.setAnimation(topAnim);
        view.setAnimation(bottomAnim);
        welcome.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);
        start.setAnimation(bottomAnim);
    }

    private void configureNextButton() {
        Button nextButton = (Button) findViewById(R.id.get_started_button);
        nextButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, login_screen.class)));
    }
}