package com.bdwater.waterservice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.bdwater.waterservice.model.User;
import com.inthecheesefactory.thecheeselibrary.fragment.support.v4.app.StatedFragment;

/**
 * Created by hegang on 2018/5/3.
 */

public class MainActivityFragment extends Fragment implements Updatable {
    private static final String TAG = "MainActivityFragment";
    private MainActivity mainActivity;
    protected User.Customer currentCustomer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
    }

    protected void changePage(int position) {
        mainActivity.changePage(position);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        Log.e(TAG, "currentCustomer == User.instance.currentCustomer : " + User.instance.isCurrent(this.currentCustomer));
        super.onResume();
        if(!User.instance.isCurrent(this.currentCustomer)) {
            this.currentCustomer = User.instance.currentCustomer;
            this.onCustomerSelectionChanged();
        }
    }
    protected void onCustomerSelectionChanged() {
        Log.e(TAG, "onCustomerSelectionChanged");
    }

    @Override
    public void checkAndRunUpdate() {
        Log.e(TAG, "checkAndRunUpdate()");
        if(!User.instance.isCurrent(this.currentCustomer)) {
            this.currentCustomer = User.instance.currentCustomer;
            Log.e(TAG, "onUpdate()");
            this.onUpdate();
        }
    }

    @Override
    public void onUpdate() {

    }
}
