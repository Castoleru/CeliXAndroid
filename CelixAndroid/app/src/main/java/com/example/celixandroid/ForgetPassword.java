package com.example.celixandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private EditText forgotEmail;
    private Button buttonDone;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private TextView toLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        forgotEmail = findViewById(R.id.editTextFPemail);
        buttonDone = findViewById(R.id.buttonFPDone);
        toLogin = findViewById(R.id.textView2Login);
        progressBar = findViewById(R.id.progressBarFP);
        firebaseAuth = FirebaseAuth.getInstance();

        toLogin.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            progressBar.setVisibility(View.GONE);
        });

        buttonDone.setOnClickListener(v -> {
            resetPassword();
        });

    }

    private void resetPassword() {
        String sEmail = forgotEmail.getText().toString().trim();
        if(sEmail.isEmpty()){
            forgotEmail.setError("Email is required!");
            forgotEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches())
        {
            forgotEmail.setError("Please enter a valid email!");
            forgotEmail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.sendPasswordResetEmail(sEmail).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ForgetPassword.this,"Check your email to reset the password!",Toast.LENGTH_LONG)
                        .show();
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }else{
                Toast.makeText(ForgetPassword.this,"Try again!",Toast.LENGTH_LONG)
                        .show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}