package com.example.the_companion;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class registration_screen extends AppCompatActivity implements View.OnClickListener{

    //variables
    Animation bottomAnim;
    View view2;
    Button buttonSignUp, backButton;
    EditText email, password, confirmPassword;


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration_screen);
        mAuth = FirebaseAuth.getInstance();
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

        buttonSignUp.setOnClickListener(v -> {
            Toast.makeText(registration_screen.this, "Button pressed", Toast.LENGTH_LONG);

            registerUser();
        });
    }


    @Override
    public void onClick(View view) {


    }

    private void registerUser() {
        String emailText = email.getText().toString();
        String passText = password.getText().toString();
        String confPassText = confirmPassword.getText().toString();

        if (emailText.isEmpty()){
            email.setError("username/email required");
            return;
        }

        if (passText.isEmpty() || confPassText.isEmpty()){
            password.setError("password field cannot be empty");
            confirmPassword.setError("password field cannot be empty");
            return;
        }



        mAuth.createUserWithEmailAndPassword(emailText, passText)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(emailText,passText);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>(){

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(registration_screen.this, "User has been registered successfully!", Toast.LENGTH_LONG);
                                    }
                                    else{
                                        Toast.makeText(registration_screen.this, "Failed to register user", Toast.LENGTH_LONG);

                                    }
                                }
                            });
                        }

                        else{
                            Toast.makeText(registration_screen.this, "Failed to register user", Toast.LENGTH_LONG);
                        }
                    }
                });

    }
}