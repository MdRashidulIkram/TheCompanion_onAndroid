package com.example.the_companion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

public class registration_screen extends AppCompatActivity {

    //variables
    Animation bottomAnim;
    View view2;
    Button buttonSignUp;
    EditText email, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration_screen);

        //animations
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        view2 = findViewById(R.id.view2);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        email = findViewById(R.id.textBoxEmail);
        password = findViewById(R.id.textBoxPassword);
        confirmPassword = findViewById(R.id.textBoxConfirmPass);

        view2.setAnimation(bottomAnim);
        buttonSignUp.setAnimation(bottomAnim);
        email.setAnimation(bottomAnim);
        password.setAnimation(bottomAnim);
        confirmPassword.setAnimation(bottomAnim);

    }


}