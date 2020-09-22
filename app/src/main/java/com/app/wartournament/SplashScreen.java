package com.app.wartournament;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread th = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    SharedPreferences sharedPreferences = getSharedPreferences("logininfo",MODE_PRIVATE);
                    if(sharedPreferences.getBoolean("logged",false)){
                        Intent i = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else {
                        Intent i = new Intent(SplashScreen.this,LoginAcitivity.class);
                        startActivity(i);
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
     th.start();
    }
}
