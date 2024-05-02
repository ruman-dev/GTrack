package com.rumanweb.goalsetter;

import android.app.Dialog;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabBtnMainLayout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.MainLayoutStatusBarColor));

        fabBtnMainLayout = findViewById(R.id.fabBtnMainLayout);
        bottomNavigationView = findViewById(R.id.bottomNavMainLayout);


        fabBtnMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog myDialog = new Dialog(MainActivity.this);
                myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                myDialog.setContentView(R.layout.bottom_sheet_dialog_dup);


                Button chooseFromTemplatesBtn = myDialog.findViewById(R.id.chooseFromTemplatesBtn);
                Button createCustomGoalBtn = myDialog.findViewById(R.id.createCustomGoalBtn);

                chooseFromTemplatesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "It will ne our next task, till then Please wait", Toast.LENGTH_LONG).show();
                    }
                });

                createCustomGoalBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myIntent = new Intent(getApplicationContext(), CreateGoal.class);
                        startActivity(myIntent);
                    }
                });

                myDialog.show();
                Objects.requireNonNull(myDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                //myDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                myDialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
        defaultFragment(R.id.frameLayout, new HomeFragment());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.homeNavBtn) {

                    //--   Get the intent from CreateGoal.java --------//

                    Intent intent = getIntent();
                    String taskTitleMain = intent.getStringExtra("taskTitle");
                    String dueDate = intent.getStringExtra("dueDate");
                    String notes = intent.getStringExtra("notes");

                    HomeFragment homeFragment = new HomeFragment();
                    Bundle args = new Bundle();
                    args.putString("taskTitleMain", taskTitleMain);
                    args.putString("dueDate", dueDate);
                    args.putString("notes", notes);
                    homeFragment.setArguments(args);

                    defaultFragment(R.id.frameLayout, new HomeFragment());
                }
                else if(item.getItemId() == R.id.insightsNavBtn) {
                    defaultFragment(R.id.frameLayout, new InsightsFragment());
                }
                else if(item.getItemId() == R.id.settingsNavBtn) {
                    defaultFragment(R.id.frameLayout, new SettingsFragment());
                }
                return true;
            }
        });

    }
    // Inside your activity class
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }
    public void defaultFragment(int containerViewId, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
    }
}