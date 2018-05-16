package com.bdwater.waterservice;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.bdwater.waterservice.model.Customer;
import com.bdwater.waterservice.model.User;
import com.bdwater.waterservice.remote.LoginRemote;
import com.bdwater.waterservice.remote.RemoteListener;
import com.bdwater.waterservice.utils.Utility;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.phoneTextInputLayout)
    TextInputLayout phoneTextInputLayout;
    @BindView(R.id.phoneEditText)
    EditText phoneEditText;
    @BindView(R.id.verifyCodeTextInputLayout)
    TextInputLayout verifyCodeTextInputLayout;
    @BindView(R.id.verifyCodeEditText)
    EditText verifyCodeEditText;
    @BindView(R.id.sendVerifyCodeButton)
    Button sendVerifyCodeButton;
    @BindView(R.id.loginButton)
    Button loginButton;

    LoginRemote remote = new LoginRemote();
    long smsID = -1;

    private static final boolean DEBUG = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(DEBUG) debug();

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        phoneEditText.setText("18003328885");
        verifyCodeEditText.setText("9999");
        remote.setRemoteListener(new RemoteListener() {
            @Override
            public void onResponse(String target, Call call, int state, String jsonResult, IOException exception) {

                if(state != LoginRemote.SUCCESS_STATE)  {
                    smsID = -1;
                    Utility.showAlertDialog(loginButton, LoginActivity.this, "错误",
                            exception.getMessage() == null ? exception.toString(): exception.getMessage());
                    completed();
                    return;
                }
                if(target == LoginRemote.SEND_VERIFY_CODE) {
                    final LoginRemote.VerifyCodeResponse response = new Gson().fromJson(jsonResult, LoginRemote.VerifyCodeResponse.class);
                    smsID = response.smsID;
                    if (response.sendStatus == 255) {
                        phoneTextInputLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                phoneTextInputLayout.setErrorEnabled(true);
                                phoneTextInputLayout.setError(response.sendMessage);
                            }
                        });
                    }
                    else {
                        // it's success to send verify code
                        phoneTextInputLayout.post(new Runnable() {
                            @Override
                            public void run() {
                                phoneTextInputLayout.setErrorEnabled(false);
                                phoneTextInputLayout.setError(null);
                                Utility.showAlertDialog(LoginActivity.this, "提示", "验证码已发出，稍后请查收短信");
                            }
                        });
                    }
                }
                else {
                    final LoginRemote.LoginResponse response = new Gson().fromJson(jsonResult, LoginRemote.LoginResponse.class);
                    if(response.loginSuccess == 0) {
                        User.instance.tel = phoneEditText.getText().toString();
                        User.Customer[] customers = new User.Customer[response.customerNoList.length];
                        for(int i = 0; i < response.customerNoList.length; i++) {
                            customers[i] = new User.Customer();
                            customers[i].customerNo = response.customerNoList[i].customerNo;
                        }
                        User.instance.customers = customers;
                        User.instance.currentCustomer = customers[0];
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        loginButton.post(new Runnable() {
                            @Override
                            public void run() {
                                Utility.showAlertDialog(LoginActivity.this, "错误", "登录失败");
                            }
                        });
                    }
                }
                completed();
                Log.e("Login", jsonResult);
            }
        });

        sendVerifyCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sending();
                phoneTextInputLayout.setErrorEnabled(false);
                phoneTextInputLayout.setError(null);
                sendVerifyCodeButton.setText(R.string.sending_verify_code);
                remote.sendVerifyCode(phoneEditText.getText().toString());
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validate()) return;
                sending();
                loginButton.setText(R.string.login_waitting);
                remote.login(phoneEditText.getText().toString(), verifyCodeEditText.getText().toString(), Long.toString(smsID));
            }
        });
    }
    void debug() {
        User.Customer[] customers = new User.Customer[2];
        customers[0] = new User.Customer();
        customers[0].customerName = "香槟小镇5-3-F2";
        customers[0].customerNo = 85437l;
        customers[1] = new User.Customer();
        customers[1].customerName = "香槟小镇11-2-C1";
        customers[1].customerNo = 86380l;

        User.instance.customers = customers;
        User.instance.tel = "18003328885";
        User.instance.currentCustomer = customers[0];

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    boolean validate(){
        boolean hasError = false;
        if(phoneEditText.getText().toString().trim().equals("")) {
            phoneTextInputLayout.setErrorEnabled(true);
            phoneTextInputLayout.setError(getString(R.string.phone_required));
            phoneTextInputLayout.requestFocus();
            hasError = true;
        } else {
            phoneTextInputLayout.setErrorEnabled(false);
            phoneTextInputLayout.setError(null);
        }

        if(verifyCodeEditText.getText().toString().trim().equals("")) {
            verifyCodeTextInputLayout.setErrorEnabled(true);
            verifyCodeTextInputLayout.setError(getString(R.string.verify_code_required));
            if(!hasError)
                verifyCodeTextInputLayout.requestFocus();
            hasError = true;
        } else {
            verifyCodeTextInputLayout.setErrorEnabled(false);
            verifyCodeTextInputLayout.setError(null);
        }
        return !hasError;
    }
    void sending() {
        progressBar.setVisibility(View.VISIBLE);
        phoneTextInputLayout.setError(null);
        phoneTextInputLayout.setErrorEnabled(false);
        phoneEditText.setEnabled(false);
        verifyCodeEditText.setEnabled(false);
        sendVerifyCodeButton.setEnabled(false);
        loginButton.setEnabled(false);
    }
    void completed() {
        sendVerifyCodeButton.post(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                phoneEditText.setEnabled(true);
                verifyCodeEditText.setEnabled(true);
                sendVerifyCodeButton.setEnabled(true);
                loginButton.setEnabled(true);
                sendVerifyCodeButton.setText(R.string.send_verify_code);
                loginButton.setText(R.string.login);
            }
        });

    }
}
