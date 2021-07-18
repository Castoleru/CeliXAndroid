package com.example.celixandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUp extends AppCompatActivity {
    private EditText fullName, email, password, confirmPassword;
    private Button buttonSignUp;
    private TextView back2Login;
    private FirebaseAuth firebaseAuth;
    private CheckBox checkBoxYes, checkBoxNo;
    private Boolean isDoctor;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);

        fullName = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextSUEmail);
        password = findViewById(R.id.editTextSUPassword);
        confirmPassword = findViewById(R.id.editTextSURePassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        back2Login = findViewById(R.id.textSUToLoginPage);
        checkBoxNo = findViewById(R.id.checkboxNo);
        checkBoxYes = findViewById(R.id.checkboxYes);
        progressBar = findViewById(R.id.progressBarSU);

        firebaseAuth = FirebaseAuth.getInstance();
        back2Login.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),MainActivity.class)));
        // Sign Up process
        buttonSignUp.setOnClickListener(v -> {
            String sEmail = email.getText().toString().trim();
            String sPassword = password.getText().toString().trim();
            String sConfirmPassword = confirmPassword.getText().toString().trim();
            String sName = fullName.getText().toString().trim();
            isDoctor = false;

            if(TextUtils.isEmpty(sName)){
                fullName.setError("Your name is required");
                email.requestFocus();
                return;
            }

            if(TextUtils.isEmpty(sEmail)){
                email.setError("Email is required");
                return;
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(sEmail).matches()){
                email.setError("Email is invalid");
                email.requestFocus();
                return;
            }

            if(TextUtils.isEmpty(sPassword)){
                password.setError("Password is required");
                password.requestFocus();
                return;
            }

            if(sPassword.length()<6) {
                password.setError("Password must have more than 5 characters");
                password.requestFocus();
                return;
            }

            if(!sConfirmPassword.equals(sPassword)) {
                confirmPassword.setError("Password doesn't match");
                confirmPassword.requestFocus();
                return;
            }

            if(!checkBoxNo.isChecked() && !checkBoxYes.isChecked()) {
                Toast.makeText(SignUp.this, "You need to check one checkbox!",
                        Toast.LENGTH_LONG).show();
                return;
            }else if (checkBoxYes.isChecked()){
                isDoctor = true;
            }
            progressBar.setVisibility(View.VISIBLE);

            // Register
            firebaseAuth.createUserWithEmailAndPassword(sEmail,sPassword).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    // Passing information to the account
                    User user = new User(sName,sEmail,isDoctor);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(user)
                            .addOnCompleteListener(task1 -> {
                                if(task1.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "User created",
                                            Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                    // Redirect to Login
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }else{
                                    Toast.makeText(SignUp.this, task1.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            });
                }else{
                    Toast.makeText(SignUp.this, task.getException().getMessage(),
                            Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        });

    }
}