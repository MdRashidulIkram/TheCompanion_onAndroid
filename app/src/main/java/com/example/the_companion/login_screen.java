package com.example.the_companion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class login_screen extends AppCompatActivity {

    //variables
    Animation bottomAnim;
    View view2;
    Button button3, button4;
    TextInputLayout textInputLayout, textInputLayout3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_screen);

        configureRegButton();

        //animations
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        view2 = findViewById(R.id.view2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        textInputLayout = findViewById(R.id.textInputLayout);
        textInputLayout3 = findViewById(R.id.textInputLayout3);

        view2.setAnimation(bottomAnim);
        button3.setAnimation(bottomAnim);
        button4.setAnimation(bottomAnim);
        textInputLayout.setAnimation(bottomAnim);
        textInputLayout3.setAnimation(bottomAnim);
    }

    private void configureRegButton(){
        Button regButton = (Button) findViewById(R.id.button4);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_screen.this, registration_screen.class));
            }
        });
    }
}