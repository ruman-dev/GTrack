package com.rumanweb.goaltrackerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

public class    EditGoal extends AppCompatActivity {

    Toolbar toolbarEditGoal;
    EditText edTaskTitleEdit,edDueDateEdit, edStepsCountEdit, edStepsTextEdit, edNotesEdit;
    Button btnEdit;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goal);

        // To set statusbarcolor to the color of the layout
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.more_green, getTheme()));

        toolbarEditGoal = findViewById(R.id.toolbarEditGoal);
        edTaskTitleEdit = findViewById(R.id.edTaskTitleEdit);
        edDueDateEdit = findViewById(R.id.edDueDateEdit);
        edStepsCountEdit = findViewById(R.id.edStepsCountEdit);
        edStepsTextEdit = findViewById(R.id.edStepsTextEdit);
        edNotesEdit = findViewById(R.id.edNotesEdit);
        btnEdit = findViewById(R.id.btnEdit);

        dbHandler = new DBHandler(EditGoal.this);

        String taskTitleEd = getIntent().getStringExtra("taskTitle");
        String dueDateEd = getIntent().getStringExtra("dueDate");
        String stepsCountEd = getIntent().getStringExtra("stepsCount");
        String stepsTextEd = getIntent().getStringExtra("stepsText");
        String notesEd = getIntent().getStringExtra("notes");
        String creationDateEd = getIntent().getStringExtra("creationDate");

        edTaskTitleEdit.setText(taskTitleEd);
        edDueDateEdit.setText(dueDateEd);
        edStepsCountEdit.setText(stepsCountEd);
        edStepsTextEdit.setText(stepsTextEd);
        edNotesEdit.setText(notesEd);

        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a Date");
        final MaterialDatePicker<Long> materialDatePicker = builder.build();

        edDueDateEdit.setOnClickListener(new View.OnClickListener() {
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
                        edDueDateEdit.setText(materialDatePicker.getHeaderText());
                    }
                });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskTitle = edTaskTitleEdit.getText().toString();
                String dueDate = edDueDateEdit.getText().toString();
                String stepsCount = edStepsCountEdit.getText().toString();
                String stepsText = edStepsTextEdit.getText().toString();
                String notes = edNotesEdit.getText().toString();
                dbHandler.updateTask(taskTitleEd, taskTitle, dueDate, stepsCount, stepsText, notes, creationDateEd);
                Toast.makeText(EditGoal.this, "Task Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        toolbarEditGoal.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}