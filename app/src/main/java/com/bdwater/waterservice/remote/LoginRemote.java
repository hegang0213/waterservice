package com.bdwater.waterservice.remote;

import android.app.DownloadManager;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by hegang on 2018/5/10.
 */

public class LoginRemote extends RemoteBase {
    public void sendVerifyCode(String phone) {
        Request.Builder builder = createRequestBuilder("VerifyCode", "api/SendSms");
        RequestBody requestBodyPost = new FormBody.Builder()
                .add("PhoneNumber", phone)
                .add("UsageType", "1")
                .build();
        Request request = builder.post(requestBodyPost).build();
        execute(request);
    }

    public static class VerifyCodeResponse {
        public Integer sendStatus;
        public String sendMessage;
        public long smsID;
    }
}
