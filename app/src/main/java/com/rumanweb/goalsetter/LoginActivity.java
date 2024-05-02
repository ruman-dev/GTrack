package com.rumanweb.goalsetter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button signInBtn;
    TextView forgotPassword, signUp;
    ProgressBar progressBarLogin;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.layoutBackground));

        email = findViewById(R.id.emailEdText);
        password = findViewById(R.id.passwordEdText);
        forgotPassword = findViewById(R.id.forgotPassword);
        signUp = findViewById(R.id.signUp);
        signInBtn = findViewById(R.id.signInBtn);
        progressBarLogin = findViewById(R.id.progressBarLogin);
        mAuth = FirebaseAuth.getInstance();

        //progressBarLogin.setVisibility(View.GONE);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBarLogin.setVisibility(View.VISIBLE);

                String emailStr, passwordStr;

                emailStr = email.getText().toString();
                passwordStr = password.getText().toString();

                if (TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(passwordStr)) {
                    Toast.makeText(getApplicationContext(), "Please enter correct info!!", Toast.LENGTH_LONG).show();
                    progressBarLogin.setVisibility(View.GONE);
                }
                mAuth.signInWithEmailAndPassword(emailStr, passwordStr)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(
                                    @NonNull Task<AuthResult> task) {
                                progressBarLogin.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login successful!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                } else {

                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(), "Login failed!! Provide correct info!!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}