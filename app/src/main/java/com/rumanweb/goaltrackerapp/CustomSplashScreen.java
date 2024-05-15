package com.rumanweb.goaltrackerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

public class CustomSplashScreen extends AppCompatActivity {

    ProgressBar progressBarSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // To set statusbarcolor to the color of the layout
        Window window = this.getWindow();
        window.setStatusBarColor(Color.parseColor("#FFFFFF"));

        progressBarSplash = findViewById(R.id.progressBarSplash);

        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();
    }

    private void doWork() {
        for (int progress=0; progress<3; progress++) {
            try {
                Thread.sleep(1000);
                progressBarSplash.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startApp() {
        Intent intent = new Intent(CustomSplashScreen.this, MainActivity.class);
        startActivity(intent);
    }
}