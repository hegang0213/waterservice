package com.bdwater.waterservice;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bdwater.waterservice.remote.LoginRemote;
import com.bdwater.waterservice.remote.RemoteListener;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.phoneEditText)
    EditText phoneEditText;
    @BindView(R.id.sendVerifyCodeButton)
    Button sendVerifyCodeButton;
    @BindView(R.id.loginButton)
    Button loginButton;

    LoginRemote remote = new LoginRemote();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        phoneEditText.setText("18003328885");
        remote.setRemoteListener(new RemoteListener() {
            @Override
            public void onResponse(String target, Call call, int state, String jsonResult, IOException exception) {

                final LoginRemote.VerifyCodeResponse response = (LoginRemote.VerifyCodeResponse)new Gson().fromJson(jsonResult, LoginRemote.VerifyCodeResponse.class);
                if(response.sendStatus == 255) {
                    phoneEditText.post(new Runnable() {
                        @Override
                        public void run() {
                            completed();
                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("提示")
                                    .setMessage(response.sendMessage)
                                    .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .create();
                            alertDialog.show();
                            alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);

                        }
                    });

                }
                Log.e("Login", jsonResult);
            }
        });

        sendVerifyCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sending();
                remote.sendVerifyCode(phoneEditText.getText().toString());
            }
        });
    }

    void sending() {
        phoneEditText.setEnabled(false);
        loginButton.setEnabled(false);
    }
    void completed() {
        phoneEditText.setEnabled(true);
        loginButton.setEnabled(true);
    }
}
