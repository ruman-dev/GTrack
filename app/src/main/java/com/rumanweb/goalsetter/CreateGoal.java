package com.rumanweb.goalsetter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class CreateGoal extends AppCompatActivity {

    TextView taskTitleName, addNewPhase, notesText;
    EditText taskTitleEd, dueDatePicker;
    Button createBtn;
    ImageView arrowBackBtn;
    String steric = "<font color='#CC0000'> * </font>";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.statusBarColor));

        taskTitleName = findViewById(R.id.taskTitle);
        taskTitleEd = findViewById(R.id.taskTitleEd);
        dueDatePicker = findViewById(R.id.dueDatePicker);
        addNewPhase = findViewById(R.id.addNewPhases);
        notesText = findViewById(R.id.notesEdText);
        createBtn = findViewById(R.id.createBtn);
        arrowBackBtn = findViewById(R.id.arrowBackBtn);

        taskTitleName.append(Html.fromHtml(steric));

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
        }

        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                String myFormat = "dd/MM/yy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                dueDatePicker.setText(dateFormat.format(myCalendar.getTime()));

            }
        };
        dueDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call date picker dialog
                new DatePickerDialog(CreateGoal.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


            createBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String taskTitle = taskTitleEd.getText().toString();
                    String dueDate = dueDatePicker.getText().toString();
                    String notes = notesText.getText().toString();
                    if (taskTitle.isEmpty()) {
                        taskTitleEd.setError("Please enter task title");
                        taskTitleEd.requestFocus();
                    } else if (dueDate.isEmpty()) {
                        dueDatePicker.setError("Please enter due date");
                        dueDatePicker.requestFocus();
                    } else if (notes.isEmpty()) {
                        notesText.setError("Please enter notes");
                        notesText.requestFocus();
                    } else {
                    /* I want to add this taskTitle, duedate, notes in firestore database based on each logged in user
                    and also want to restore data from firestore to show this data in the recyclerview in the main activity
                        */
                        DocumentReference dbRefer = db.collection("users").document(userId);
                        Map<String, Object> user = new HashMap<>();
                        user.put("taskTitle", taskTitle);
                        user.put("dueDate", dueDate);
                        user.put("notes", notes);
                        dbRefer.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(CreateGoal.this, "Goal created successfully", Toast.LENGTH_SHORT).show();
                                Intent myIntent = new Intent(getApplicationContext(), HomeFragment.class);
                                myIntent.putExtra("taskTitle",taskTitle);
                                myIntent.putExtra("dueDate",dueDate);
                                myIntent.putExtra("notes",notes);
                                startActivity(myIntent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreateGoal.this, "Failed to create goal", Toast.LENGTH_SHORT).show();
                                Log.e("failed", e.toString());
                            }
                        });
                    }
                }
            });


        addNewPhase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog initialDialog = new Dialog(CreateGoal.this);
                initialDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                initialDialog.setContentView(R.layout.add_task_bottom_sheet_dialog);


                EditText taskTitleEd = initialDialog.findViewById(R.id.taskTitleEd);
                TextView setDateText = initialDialog.findViewById(R.id.setDateTxt);
                TextView saveBtnBSD = initialDialog.findViewById(R.id.saveBtnBSD);
                LinearLayout setDate = initialDialog.findViewById(R.id.setDateLayout);
                LinearLayout setReminder = initialDialog.findViewById(R.id.setReminderLayout);

                setReminder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(CreateGoal.this, "It will ne our next task, till then Please wait", Toast.LENGTH_LONG).show();
                    }
                });

                setDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar myCalendar = Calendar.getInstance();
                        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, month);
                                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                                String myFormat = "MMM dd, yyyy";
                                SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
                                dueDatePicker.setText(dateFormat.format(myCalendar.getTime()));

                                String dueDateStr = dueDatePicker.getText().toString();
                                setDateText.setText(dueDateStr);
                            }
                        };
                        // Call date picker dialog
                        new DatePickerDialog(CreateGoal.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                initialDialog.show();
                Objects.requireNonNull(initialDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                initialDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                initialDialog.getWindow().setGravity(Gravity.BOTTOM);

                saveBtnBSD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String taskTitle = taskTitleEd.getText().toString();
                        String dueDate = setDateText.getText().toString();
                        if (taskTitle.isEmpty()) {
                            taskTitleEd.setError("Please enter task title");
                            taskTitleEd.requestFocus();
                        } else {
                            // Call API to create goal

                        }

                        initialDialog.dismiss();
                    }
                });
            }
        });


        arrowBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateGoal.super.onBackPressed();
                finish();
            }
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}