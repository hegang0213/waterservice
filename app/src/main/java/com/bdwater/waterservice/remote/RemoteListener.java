package com.bdwater.waterservice.remote;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by hegang on 2018/5/10.
 */

public interface RemoteListener {
    void onResponse(String target, Call call, int state, String jsonResult, IOException exception);
}
