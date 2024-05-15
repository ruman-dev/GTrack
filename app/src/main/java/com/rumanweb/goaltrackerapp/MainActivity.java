package com.rumanweb.goaltrackerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;
    FloatingActionButton fabBtnMain;
    ListView listViewMain;
    private ArrayList<TaskModal> taskModalArrayList;
    public static boolean delData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To set statusbarcolor to the color of the layout
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.more_green, getTheme()));


        progressBar = findViewById(R.id.progressBarMain);
        fabBtnMain = findViewById(R.id.fabBtnMain);
        listViewMain = findViewById(R.id.listViewMain);
        // To set the progress of the progressbar
//        progressBar.setProgress(50);

        fabBtnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), CreateGoalActivity.class);
                startActivity(myIntent);
            }
        });
        DBHandler dbHandler = new DBHandler(getApplicationContext());
        taskModalArrayList = dbHandler.readTasks();

        // To set the adapter to the listview
        MyAdapter myAdapter = new MyAdapter();
        listViewMain.setAdapter(myAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHandler dbHandler = new DBHandler(getApplicationContext());
        taskModalArrayList = dbHandler.readTasks();
        MyAdapter myAdapter = new MyAdapter();
        listViewMain.setAdapter(myAdapter);
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return taskModalArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return taskModalArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View myView = layoutInflater.inflate(R.layout.item_layout, viewGroup, false);

            TaskModal task = taskModalArrayList.get(position);

            RelativeLayout taskItemLayout = myView.findViewById(R.id.taskItemLayout);
            ImageView taskImage = myView.findViewById(R.id.taskImage);
            TextView item_taskTitle = myView.findViewById(R.id.item_taskTitle);
            TextView item_taskDueDate = myView.findViewById(R.id.item_dueDate);
            ProgressBar linearProgressIndMain = myView.findViewById(R.id.linearProgressIndMain);
            TextView tvProgressPercentage = myView.findViewById(R.id.progressPercentage);

            int progress = task.getProgress();
            int maxProgress = Integer.parseInt(task.getStepsCount());

            int progressPercentage = (int) (progress * 100) / maxProgress;

            item_taskTitle.setText(task.getTaskTitle());
            item_taskDueDate.setText(task.getDueDate());
            taskImage.setImageResource(R.drawable.icon_task);
            linearProgressIndMain.setMax(maxProgress);
            linearProgressIndMain.setProgress(progress);
            tvProgressPercentage.setText(progressPercentage + "%");

            taskItemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detailIntent = new Intent(getApplicationContext(), TaskDetail.class);
                    TaskDetail.taskTitleStrTD = task.getTaskTitle();
                    TaskDetail.dueDateStrTD = task.getDueDate();
                    TaskDetail.stepsCountTD = task.getStepsCount();
                    TaskDetail.notesStrTD = task.getNotes();
                    TaskDetail.creationDateStrTD = task.getCreationDate();
                    TaskDetail.stepsTextTD = task.getStepsText();

                    startActivity(detailIntent);
                }
            });

            return myView;
        }
    }
}