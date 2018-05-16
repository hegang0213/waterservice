package com.bdwater.waterservice.remote;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by hegang on 2018/5/10.
 */

public class HomeRemote extends RemoteBase {
    private static final String TAG = "HomeRemote";
    public void getCustomer(long customerNo) {
        Request.Builder builder = createRequestBuilder(TAG, "api/Customer/" + Long.toString(customerNo));
        Request request = builder.get().build();
        execute(request);
    }
}
