package com.bdwater.waterservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bdwater.waterservice.model.User;

import java.util.Date;

public class SplashActivity extends AppCompatActivity {

    Date start;
    Long waitingSeconds = 3l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        start = new Date();
        User.instance.read(this);
        if(User.instance.isLogon)
            startLogin();
        else
            loadAndStartMain();
    }

    void loadAndStartMain() {
        new Thread() {
            @Override
            public void run() {
                while(getSeconds() < waitingSeconds) {
                    try {
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        }.start();


    }
    void startLogin() {
        new Thread() {
            @Override
            public void run() {
                while(getSeconds() < waitingSeconds) {
                    try {
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        }.start();

    }
    Long getSeconds() {
        return (new Date().getTime() - start.getTime()) / 1000;
    }
}
