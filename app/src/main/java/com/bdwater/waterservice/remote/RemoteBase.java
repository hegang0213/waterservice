package com.bdwater.waterservice.remote;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hegang on 18/5/2.
 */

public class RemoteBase {
    public final static String baseUrl = "http://121.18.65.232:50030/";
    public final static String updateUrl = "http://121.18.65.232:50030/update/update.json";
    public final static int SUCCESS_STATE = 0;
    public final static int FAILED_STATE = -1;
    private String url;
    private String target;
    private OkHttpClient client;
    private Request.Builder requestBuilder;
    private RemoteListener listener;
    public RemoteBase() {
        this.client = new OkHttpClient();
    }

    protected Request.Builder createRequestBuilder(String target, String url) {
        this.target = target;
        this.url = url;
        return new Request.Builder().url(baseUrl + url).tag(target);
    }

    public void setRemoteListener(RemoteListener listener) {
        this.listener = listener;
    }


    public void execute(final Request request) {
        Call call = this.client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(listener != null)
                    listener.onResponse(call.request().tag().toString(), call, FAILED_STATE, null, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(listener != null)
                    listener.onResponse(call.request().tag().toString(), call, SUCCESS_STATE, response.body().string(), null);
            }
        });
    }

}
