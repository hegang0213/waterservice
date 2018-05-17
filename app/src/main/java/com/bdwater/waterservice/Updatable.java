package com.bdwater.waterservice;

/**
 * Created by hegang on 2018/5/16.
 */

public interface Updatable {
    void checkAndRunUpdate();
    void onUpdate();
}
