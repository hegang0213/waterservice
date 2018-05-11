package com.bdwater.waterservice.remote;

import android.app.DownloadManager;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by hegang on 2018/5/10.
 */

public class LoginRemote extends RemoteBase {
    public static final String SEND_VERIFY_CODE = "VerifyCode";
    public static final String LOGIN = "Login";
    public void sendVerifyCode(String phone) {
        Request.Builder builder = createRequestBuilder(SEND_VERIFY_CODE, "api/SendSms");
        RequestBody requestBodyPost = new FormBody.Builder()
                .add("PhoneNumber", phone)
                .add("UsageType", "1")
                .build();
        Request request = builder.post(requestBodyPost).build();
        execute(request);
    }
    public void login(String phone, String verifyCode, String smsId) {
        Request.Builder builder = createRequestBuilder(LOGIN, "api/LoginValidate");
        RequestBody requestBodyPost = new FormBody.Builder()
                .add("PhoneNumber", phone)
                .add("ValidateCode", verifyCode)
                .add("smsID", smsId)
                .build();
        Request request = builder.post(requestBodyPost).build();
        execute(request);
    }

    public static class VerifyCodeResponse {
        public Integer sendStatus;
        public String sendMessage;
        public Long smsID;
    }
    public static class LoginResponse {
        public Integer loginSuccess;
        public CustomerNoList[] customerNoList;
    }
    public static class CustomerNoList {
        public Long customerNo;
    }
}
