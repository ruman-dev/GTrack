package com.rumanweb.goaltrackerapp;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;

public class TaskDetail extends AppCompatActivity {

    Toolbar toolbarDetail;
    TextView taskTitleDetail, dueDateDetail,taskStepsCountDetail,tvNotesDetail,tvStepsDetail,tvCreationDateDetail;
    Button saveBtnDetail;
    ImageView decrementIconDetail,incrementIconDetail, deleteIconDetail, editIconDetail;
    LinearProgressIndicator linearProgressIndDetail;
    private int progress = 0;
    public static String taskTitleStrTD = "";
    public static String dueDateStrTD = "";
    public static String stepsCountTD = "";
    public static String notesStrTD = "";
    public static String creationDateStrTD = "";
    public static String stepsTextTD = "";
    //public int savedProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // To set statusbarcolor to the color of the layout
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.more_green, getTheme()));

        toolbarDetail = findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbarDetail);
        taskTitleDetail = findViewById(R.id.taskTitleDetail);
        dueDateDetail = findViewById(R.id.dueDateDetail);
        taskStepsCountDetail = findViewById(R.id.taskStepsCountDetail);
        decrementIconDetail = findViewById(R.id.decrementIconDetail);
        incrementIconDetail = findViewById(R.id.incrementIconDetail);
        saveBtnDetail = findViewById(R.id.saveBtnDetail);
        linearProgressIndDetail = findViewById(R.id.linearProgressIndDetail);
        tvStepsDetail = findViewById(R.id.tvStepsDetail);
        tvNotesDetail = findViewById(R.id.tvNotesDetail);
        tvCreationDateDetail = findViewById(R.id.tvCreationDateDetail);
        deleteIconDetail = findViewById(R.id.deleteIconDetail);
        editIconDetail = findViewById(R.id.editIconDetail);

        DBHandler dbHandler = new DBHandler(TaskDetail.this);
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        String query = "SELECT * FROM tasklist WHERE tasktitle = ?";
        String[] selectionArgs = {taskTitleStrTD};
        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            int progressColumnIndex = cursor.getColumnIndex("progress");

            if (progressColumnIndex != -1) { // Check if the column exists
                progress = cursor.getInt(progressColumnIndex);
            } else {
                Toast.makeText(getApplicationContext(), "progress column not found in query result", Toast.LENGTH_SHORT).show();
            }
        }
//        updateProgress();
        cursor.close();
        db.close();

        taskTitleDetail.setText(taskTitleStrTD);
        dueDateDetail.setText(dueDateStrTD);
        taskStepsCountDetail.setText("0 / "+stepsCountTD);
        tvNotesDetail.setText(notesStrTD);
        tvCreationDateDetail.setText(creationDateStrTD);
        tvStepsDetail.setText(stepsTextTD);

        linearProgressIndDetail.setMax(Integer.parseInt(stepsCountTD));
        updateProgress();
        incrementIconDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = linearProgressIndDetail.getProgress();
                if (progress < Integer.parseInt(stepsCountTD)) {
                    linearProgressIndDetail.setProgress(progress + 1);
                    taskStepsCountDetail.setText(String.valueOf(progress + 1) +" / "+stepsCountTD);
                }
            }
        });

        decrementIconDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = linearProgressIndDetail.getProgress();
                if (progress > 0) {
                    linearProgressIndDetail.setProgress(progress - 1);
                    taskStepsCountDetail.setText(String.valueOf(progress - 1) +" / "+stepsCountTD);
                }
            }
        });

        deleteIconDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskDetail.this);
                builder.setTitle("Delete Task")
                        .setMessage("Are you sure you want to delete this task?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dbHandler.deleteTask(taskTitleStrTD);
                                Toast.makeText(TaskDetail.this, "Task deleted", Toast.LENGTH_SHORT).show();
                                finish(); // Close the TaskDetail activity
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        editIconDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskDetail.this);
                builder.setTitle("Edit Task")
                        .setMessage("Are you sure you want to edit this task?")
                        .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Open the EditGoalActivity to edit the task

                                Intent editGoalIntent = new Intent(getApplicationContext(), EditGoal.class);
                                editGoalIntent.putExtra("taskTitle", taskTitleStrTD);
                                editGoalIntent.putExtra("dueDate", dueDateStrTD);
                                editGoalIntent.putExtra("stepsCount", stepsCountTD);
                                editGoalIntent.putExtra("notes", notesStrTD);
                                editGoalIntent.putExtra("creationDate", creationDateStrTD);
                                editGoalIntent.putExtra("stepsText", stepsTextTD);

                                startActivity(editGoalIntent);
                                finish(); // Close the TaskDetail activity
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });


        saveBtnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progress = linearProgressIndDetail.getProgress();
                dbHandler.updateProgress(taskTitleStrTD, progress);
//                updateProgress();
                finish();

            }
        });
        toolbarDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void updateProgress()
    {
        linearProgressIndDetail.setProgress(progress);
        taskStepsCountDetail.setText(progress + " / " + stepsCountTD);
    }
}