package com.example.the_companion;



import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class registration_screen extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "REGISTER";
    //variables
    Animation bottomAnim;
    View view2;
    Button btnSignUp;
    EditText email, password, confirmPassword;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration_screen);
        configureNextDashboard();

        mAuth = FirebaseAuth.getInstance();
        configureNextDashboard();


        //animations
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        //Hooks
        view2 = findViewById(R.id.view2);
        btnSignUp = findViewById(R.id.sign_up_button);
        email = findViewById(R.id.textBoxEmail);
        password = findViewById(R.id.textBoxPassword);
        confirmPassword = findViewById(R.id.textBoxConfirmPass);

        view2.setAnimation(bottomAnim);
        email.setAnimation(bottomAnim);
        password.setAnimation(bottomAnim);
        confirmPassword.setAnimation(bottomAnim);

        btnSignUp.setOnClickListener(v -> registerUser());

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void reload() {
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

        createAccount(emailText, passText);

        mAuth.createUserWithEmailAndPassword(emailText, passText)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        User user = new User(emailText,passText);

                        FirebaseDatabase.getInstance().getReference("User")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()){
                                        Snackbar.make(findViewById(R.id.view), "Successful", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();                                  }
                                    else{
                                        Snackbar.make(findViewById(R.id.view), "Failed", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }
                                });
                    }

                    else{
                        Toast.makeText(registration_screen.this, "Failed to register user", Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Snackbar.make(findViewById(R.id.view), "Successfully registered", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //FirebaseUser user = mAuth.getCurrentUser();
                        Intent intent = new Intent(registration_screen.this, PrimaryDashboard.class);
                        startActivity(intent);
                    }

                    else {
                        // If sign in fails, display a message to the user.
                        Snackbar.make(findViewById(R.id.view), email + password, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                    }
                });
        // [END create_user_with_email]
    }

    private void configureNextDashboard() {

        Button nextDashboard = findViewById(R.id.sign_up_button);


        nextDashboard.setOnClickListener(v -> startActivity(new Intent(registration_screen.this, AddTaskActivity.class)));
    }

    @Override
    public void onClick(View view) {

    }
}