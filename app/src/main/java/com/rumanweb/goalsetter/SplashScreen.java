package com.rumanweb.goalsetter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBarSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        progressBarSplash = findViewById(R.id.progressBarSplash);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.layoutBackground));

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
        Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
        startActivity(intent);
    }

}
