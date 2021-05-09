package com.example.the_companion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class login_screen extends AppCompatActivity {

    private static final String TAG = "LOGIN" ;
    //variables
    Animation bottomAnim;
    View view2;
    Button btnSignIn, btnSignUp;
    EditText email, password;
    private FirebaseAuth mAuth;
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_screen);

        nextPrimaryDashboard();
        goforgotPassword();

        mAuth = FirebaseAuth.getInstance();
        //animations
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        view2 = findViewById(R.id.view2);
        btnSignIn = findViewById(R.id.sign_in_button);
        btnSignUp = findViewById(R.id.sign_up_button);
        email = findViewById(R.id.textBoxEmail);
        password = findViewById(R.id.textBoxPassword);
        forgotPassword = findViewById(R.id.forgotpassword);

        view2.setAnimation(bottomAnim);
        btnSignIn.setAnimation(bottomAnim);
        btnSignUp.setAnimation(bottomAnim);
        email.setAnimation(bottomAnim);
        password.setAnimation(bottomAnim);
        forgotPassword.setAnimation(bottomAnim);

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Intent i = new Intent(login_screen.this, PrimaryDashboard.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
        btnSignIn.setOnClickListener(v -> {

            loginUser();
        });

    }

    private void loginUser(){
        String emailText = email.getText().toString();
        String passText = password.getText().toString();


        if (!emailText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            if (!passText.isEmpty()){
                mAuth.signInWithEmailAndPassword(emailText , passText)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(login_screen.this, "Login Successfully !!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(login_screen.this, PrimaryDashboard.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login_screen.this, "Login Failed !!", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                password.setError("Empty Fields Are not Allowed");
            }
        }else if(emailText.isEmpty()){
            email.setError("Empty Fields Are not Allowed");
        }else{
            email.setError("Pleas Enter Correct Email");
        }
    }

    private void nextPrimaryDashboard(){
        Button nextbtn = findViewById(R.id.sign_up_button);
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_screen.this, registration_screen.class));
            }
        });
    }

    private void goforgotPassword(){
        forgotPassword = findViewById(R.id.forgotpassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login_screen.this, forgotPassword.class));
            }
        });
    }

}