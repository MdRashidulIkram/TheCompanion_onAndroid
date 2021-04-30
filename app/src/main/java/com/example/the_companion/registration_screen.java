package com.example.the_companion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class registration_screen extends AppCompatActivity {

    //variables
    Animation bottomAnim;
    View view2;
    Button button4;
    TextInputLayout textInputLayout, textInputLayout3, textInputLayout4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration_screen);

        //animations
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        view2 = findViewById(R.id.view2);
        button4 = findViewById(R.id.button4);
        textInputLayout = findViewById(R.id.textInputLayout);
        textInputLayout3 = findViewById(R.id.textInputLayout3);
        textInputLayout4 = findViewById(R.id.textInputLayout4);

        view2.setAnimation(bottomAnim);
        button4.setAnimation(bottomAnim);
        textInputLayout.setAnimation(bottomAnim);
        textInputLayout3.setAnimation(bottomAnim);
        textInputLayout4.setAnimation(bottomAnim);

    }
}