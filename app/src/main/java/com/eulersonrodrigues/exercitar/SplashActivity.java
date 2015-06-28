package com.eulersonrodrigues.exercitar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class SplashActivity extends Activity {
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        doAnim();



        final String PREFS_NAME = "MyPrefsFile";

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

        if (settings.getBoolean("my_first_time", true)) {
            abreTuto();
            Log.d("Comments", "First time");
            settings.edit().putBoolean("my_first_time", false).commit();
        } else abreHome();

    }

    private void abreTuto() {
        Handler h = new Handler();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this,com.eulersonrodrigues.exercitar.TutorialActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }

    private void doAnim() {
        ImageView imageView = (ImageView) findViewById(R.id.logoSplash);
        imageView.setImageResource(R.drawable.logosplash512);
        imageView.clearAnimation();


        Animation scaleAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_animation);
        imageView.startAnimation(scaleAnimation);
    }

    public void abreHome() {
        Handler h = new Handler();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, com.eulersonrodrigues.exercitar.MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);

    }
}