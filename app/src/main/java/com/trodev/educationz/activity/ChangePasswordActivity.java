package com.trodev.educationz.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.trodev.educationz.R;
import com.trodev.educationz.fragments.HomeFragment;
import com.trodev.educationz.fragments.ProfileFragment;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText emailEt;
    private MaterialButton submitBtn;
    private ImageButton backBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        firebaseAuth= FirebaseAuth.getInstance();

        emailEt= findViewById(R.id.emailEt);
        submitBtn= findViewById(R.id.submitBtn);
        backBtn= findViewById(R.id.backBtn);

        //Init/setup progress dialog
        progressDialog= new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        //handle click, go back
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //handle click, begin recovery password
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }

    private String email= "";
    private void validateData() {
        email = emailEt.getText().toString().trim();

        //validate data shouldn't empty and should be valid format
        if(email.isEmpty()){
            Toast.makeText(this, "Enter Email...", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format...", Toast.LENGTH_SHORT).show();
        }
        else {
            recoveryPassword();
        }
    }

    private void recoveryPassword() {

        //show progress
        progressDialog.setMessage("Sending password recovery instruction to"+email);
        progressDialog.show();

        //begin sending recovery
        firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        //sent
                        progressDialog.dismiss();
                        Toast.makeText(ChangePasswordActivity.this, "Instruction to reset password sent to"+email, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ChangePasswordActivity.this, ProfileFragment.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        //failed to send
                        progressDialog.dismiss();
                        Toast.makeText(ChangePasswordActivity.this, "Failed to send due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}