package com.trodev.educationz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.trodev.educationz.MainActivity;
import com.trodev.educationz.R;
import com.trodev.educationz.User;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView signin;
    private MaterialButton signup;
    private EditText username, emailET, ageET, instituteEt, passwordET;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

//        /*hide action bar*/
//        getSupportActionBar().hide();

        signup = findViewById(R.id.signup);
        signup.setOnClickListener(this);
        signin = findViewById(R.id.signin);
        signin.setOnClickListener(this);

        /*auth profile from firebase*/
        firebaseAuth = FirebaseAuth.getInstance();

        /*init widget views*/
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(this);
        username = findViewById(R.id.username);
        emailET = findViewById(R.id.emailEt);
        ageET = findViewById(R.id.ageEt);
        instituteEt = findViewById(R.id.instituteEt);
        passwordET = findViewById(R.id.passwordEt);
        progressBar = findViewById(R.id.progressBar);
    }

    // go to SignIn form with clicking
    @Override
    public void onClick(View v) {
        int itemId = v.getId();
        if (itemId == R.id.signin) {
            startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            Toast.makeText(SignUpActivity.this, "SignIn First", Toast.LENGTH_SHORT).show();
            finish();
        } else if (itemId == R.id.signup) {
            registarUser();

        }

    }

    private void registarUser() {
        String usersname, email, age, institute, password;
        usersname = username.getText().toString().trim();
        email = emailET.getText().toString().trim();
        age = ageET.getText().toString().trim();
        institute = instituteEt.getText().toString().trim();
        password = passwordET.getText().toString().trim();

        if (usersname.isEmpty()) {
            username.setError("Name is required");
            username.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            emailET.setError("E-mail is required");
            emailET.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("Please provide valid email!");
            emailET.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            ageET.setError("Age is required");
            ageET.requestFocus();
            return;
        }

        if (institute.isEmpty()) {
            instituteEt.setError("Institute name is required");
            instituteEt.requestFocus();
            return;
        }

        if (password.length() <= 8) {
            passwordET.setError("Minimum password length should be 8 character");
            passwordET.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //Firebase Database

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(usersname, email, age, institute, password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                                finish();

                                            } else {

                                                progressBar.setVisibility(View.VISIBLE);
                                                Toast.makeText(SignUpActivity.this, "রRegistration Unsuccessful", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });

                        }
                    }
                });

    }
}