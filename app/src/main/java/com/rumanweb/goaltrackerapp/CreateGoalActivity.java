package com.rumanweb.goaltrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateGoalActivity extends AppCompatActivity {

    Button btnCreate;
    EditText edNotes, edDueDate, edTaskTitle, edStepsCount;
    String edTaskTitleStr, edNotesStr, edDueDateStr, edStepsCountStr, stepsText, creationDate;
    TextInputEditText edStepsText;
    Toolbar toolbar;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goal);

        // To set statusbarcolor to the color of the layout
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.more_green, getTheme()));

        // To initialize the views
        edNotes = findViewById(R.id.edNotes);
        edDueDate = findViewById(R.id.edDueDate);
        edTaskTitle = findViewById(R.id.edTaskTitle);
        edStepsCount = findViewById(R.id.edStepsCount);
        btnCreate = findViewById(R.id.btnCreate);
        edStepsText = findViewById(R.id.edStepsText);
        toolbar = findViewById(R.id.toolbarCreateGoal);
        setSupportActionBar(toolbar);

        dbHandler = new DBHandler(CreateGoalActivity.this);

        //To get DatePicker
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a Date");
        final MaterialDatePicker<Long> materialDatePicker = builder.build();

        edDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        edDueDate.setText(materialDatePicker.getHeaderText());
                    }
                });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edTaskTitleStr = edTaskTitle.getText().toString();
                edNotesStr = edNotes.getText().toString();
                edDueDateStr = edDueDate.getText().toString();
                edStepsCountStr = edStepsCount.getText().toString();
                stepsText = edStepsText.getText().toString();

                //To check if the fields are empty
                if(edTaskTitleStr.isEmpty()){
                    edTaskTitle.setError("Enter a title");
                }
                else if (edDueDateStr.isEmpty()){
                    edDueDate.setError("Enter a due date");
                }
                else if (edStepsCount.getText().toString().isEmpty()) {
                    edStepsCount.setError("Enter the steps count");
                }
                else if (stepsText.isEmpty()) {
                    edStepsText.setError("Enter the steps");
                }
                else if (edNotesStr.isEmpty()) {
                    edNotesStr = "No notes found";
                }
                else {
                    Intent myIntent = new Intent();
                    setResult(RESULT_OK, myIntent);
                    creationDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(new Date());
                    //creationDate = "2021-09-01";
                    dbHandler.addNewTask(edTaskTitleStr, edDueDateStr, edStepsCountStr,stepsText, edNotesStr, creationDate);
                    Toast.makeText(CreateGoalActivity.this, "Task Has Been Added", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

        // Set navigation icon click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle navigation icon click here
                finish();    // Example: go back when the navigation icon is clicked
            }
        });

    }
}