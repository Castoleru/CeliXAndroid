package com.example.celixandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private TextView toSignUp, toForgotPassword;
    private EditText email, password;
    private Button buttonLogin;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        toSignUp = findViewById(R.id.textView2SignUp);
        toForgotPassword = findViewById(R.id.textView2ForgotPass);
        progressBar = findViewById(R.id.progressBarLI);
        email = findViewById(R.id.editTextLIemail);
        password = findViewById(R.id.editTextLIPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        firebaseAuth = FirebaseAuth.getInstance();

        // Auto Login
        if (firebaseAuth.getCurrentUser() != null) {
            // User is signed in (getCurrentUser() will be null if not signed in)
            startActivity(new Intent(getApplicationContext(),MainBottomNavigation.class));
            finish();
        }

        // To Forgot Password page
        toForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(getApplicationContext(),ForgetPassword.class));
                progressBar.setVisibility(View.INVISIBLE);

            }
        });

        // To Sign Up page
        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                startActivity(new Intent(getApplicationContext(),SignUp.class));
                progressBar.setVisibility(View.INVISIBLE);

            }

        });

        // Login implementation
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEmail = email.getText().toString().trim();
                String sPassword = password.getText().toString().trim();

                if(TextUtils.isEmpty(sEmail)){
                    email.setError("Email is required!");
                    email.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()) {
                    email.setError("Please enter a valid email!");
                    email.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(sPassword)){
                    password.setError("Password is required!");
                    password.requestFocus();
                    return;
                }

                if(sPassword.length()< 6){
                    password.setError("Min Password length is 6 characters!");
                    password.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(sEmail,sPassword).addOnCompleteListener(task ->  {
                    if(task.isSuccessful())
                    {
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        if(firebaseUser.isEmailVerified()){
                            Toast.makeText(MainActivity.this, "Login Successful",
                                    Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainBottomNavigation.class));

                        }else{
                            firebaseUser.sendEmailVerification();
                            Toast.makeText(MainActivity.this, "Check your email",
                                    Toast.LENGTH_LONG).show();
                        }

                        progressBar.setVisibility(View.INVISIBLE);
                    }else {
                        Toast.makeText(MainActivity.this, task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    }

                });
            }
        });


    }
}