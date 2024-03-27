package com.rumanweb.goalsetter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    EditText userName, userEmail, userPwd, userConfirmPwd, userDOB;
    String emailStr, pwdStr, nameStr, dobStr, confirmPwdStr, genderStr;
    RadioButton radioBtnSelected;
    RadioGroup radioGrpGender;
    Button signUpBtn;
    TextView signInBtn;
    ProgressBar progressBar;
    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.layoutBackground));


        userName = findViewById(R.id.nameEdText);
        userEmail = findViewById(R.id.emailEdText);
        userPwd = findViewById(R.id.pwdEdText);
        userConfirmPwd = findViewById(R.id.confirmPwdEdText);
        userDOB = findViewById(R.id.dobEdText);

        radioGrpGender = findViewById(R.id.radioGrpGender);
        radioGrpGender.clearCheck();

        signInBtn = findViewById(R.id.signIn);
        signUpBtn = findViewById(R.id.signUpBtn);
        progressBar = findViewById(R.id.progressBarSignUp);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };
        userDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(RegisterActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedGenderId = radioGrpGender.getCheckedRadioButtonId();
                radioBtnSelected = findViewById(selectedGenderId);

                emailStr = userEmail.getText().toString();
                nameStr = userName.getText().toString();
                pwdStr = userPwd.getText().toString();
                confirmPwdStr = userConfirmPwd.getText().toString();
                dobStr = userDOB.getText().toString();


                if (TextUtils.isEmpty(nameStr)) {
                    Toast.makeText(getApplicationContext(), "Please enter your name first!", Toast.LENGTH_LONG).show();
                    userName.setError("Full name required!");
                    userName.requestFocus();
                } else if (TextUtils.isEmpty(emailStr)) {
                    Toast.makeText(getApplicationContext(), "Please enter your email!", Toast.LENGTH_LONG).show();
                    userEmail.setError("Email required!");
                    userEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid email!", Toast.LENGTH_LONG).show();
                    userEmail.setError("Valid email required!");
                    userEmail.requestFocus();
                } else if (TextUtils.isEmpty(dobStr)) {
                    Toast.makeText(getApplicationContext(), "Please enter your date of birth!", Toast.LENGTH_LONG).show();
                    userDOB.setError("Date of birth required!");
                    userDOB.requestFocus();
                } else if (radioGrpGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please select your Gender!", Toast.LENGTH_LONG).show();

                } else if (TextUtils.isEmpty(pwdStr)) {
                    Toast.makeText(getApplicationContext(), "Please enter your password!", Toast.LENGTH_LONG).show();
                    userPwd.setError("Password required!");
                    userPwd.requestFocus();
                } else if (pwdStr.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password should be at least 6 digits!", Toast.LENGTH_LONG).show();
                    userPwd.setError("Password should be at least 6 digits!");
                    userPwd.requestFocus();
                } else if (TextUtils.isEmpty(confirmPwdStr)) {
                    Toast.makeText(getApplicationContext(), "Please confirm your password!", Toast.LENGTH_LONG).show();
                    userConfirmPwd.setError("Password required!");
                    userConfirmPwd.requestFocus();
                } else if (!pwdStr.equals(confirmPwdStr)) {
                    Toast.makeText(getApplicationContext(), "Password does not match!", Toast.LENGTH_LONG).show();
                    userPwd.setError("Password does not match!");
                    userPwd.requestFocus();

                    //clear the edittext fields
                    userPwd.clearComposingText();
                    userConfirmPwd.clearComposingText();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    genderStr = radioBtnSelected.getText().toString();
                    registerUser(nameStr, emailStr,dobStr, genderStr, pwdStr );
                }

            }
        });


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }


    private void registerUser(String nameStr, String emailStr, String dobStr, String genderStr, String pwdStr) {

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(emailStr, pwdStr)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();

                        assert firebaseUser != null;
                        firebaseUser.sendEmailVerification();


                        Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);



                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Registration successful!!", Toast.LENGTH_LONG).show();
                        startActivity(myIntent);
                        finish();


                    } else {
                        // registration failed
                        Toast.makeText(getApplicationContext(), "Registration failed!! Provide correct info or wait sometime!!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        userDOB.setText(dateFormat.format(myCalendar.getTime()));
    }
}