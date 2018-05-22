package com.bdwater.waterservice;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bdwater.waterservice.model.User;
import com.bdwater.waterservice.remote.LoginRemote;
import com.bdwater.waterservice.remote.RemoteBase;
import com.bdwater.waterservice.remote.RemoteListener;
import com.bdwater.waterservice.utils.Utility;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;

public class SplashActivity extends AppCompatActivity {

    Date start;
    Long waitingSeconds = 3l;
    LoginRemote remote = new LoginRemote();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        start = new Date();
        User.instance.read(this);
        if(!User.instance.isLogon)
            startLogin();
        else
            loadAndStartMain();
    }

    void loadAndStartMain() {
        remote.setRemoteListener(remoteListener);
        validate();
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
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }.start();

    }
    Long getSeconds() {
        return (new Date().getTime() - start.getTime()) / 1000;
    }

    private void validate() {
        remote.validate(User.instance.tel);
    }
    private RemoteListener remoteListener = new RemoteListener() {
        @Override
        public void onResponse(String target, Call call, int state, String jsonResult, IOException exception) {
            if(state == RemoteBase.SUCCESS_STATE) {
                LoginRemote.LoginResponse response = new Gson().fromJson(jsonResult, LoginRemote.LoginResponse.class);
                User.Customer[] customers = new User.Customer[response.customerNoList.length];
                for(int i = 0; i < response.customerNoList.length; i++) {
                    customers[i] = new User.Customer();
                    customers[i].customerNo = response.customerNoList[i].customerNo;
                    customers[i].customerName = response.customerNoList[i].customerName;
                }
                User.instance.customers = customers;
                User.instance.currentCustomer = customers[0];
                User.instance.save(SplashActivity.this);
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
                        finish();
                    }
                }.start();
            }
            else {
                Utility.showRetryAlertDialog(SplashActivity.this, "提示",
                        exception.getMessage(), "重试", listener);
            }
        }
    };
    private DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            validate();
            dialogInterface.dismiss();
        }
    };
}
