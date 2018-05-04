package com.bdwater.waterservice.remote;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hegang on 18/5/2.
 */

public class RemoteManager {
    public final static String baseUrl = "http://121.18.65.232:50030/";
    private OkHttpClient client;
    private Request.Builder requestBuilder;
    public RemoteManager() {
        this.client = new OkHttpClient();
        this.requestBuilder = new Request.Builder().url(baseUrl);
    }
    public void execute(RequestBody requestBody) {
         Request request = this.requestBuilder.post(requestBody).build();
         execute(request);
    }
    public void execute(Request request) {
        Call call = this.client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

}
