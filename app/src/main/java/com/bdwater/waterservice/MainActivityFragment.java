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

public class MainActivityFragment extends Fragment {
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
        Log.e(TAG, "onResume");
        Log.e(TAG, "currentCustomer == User.instance.currentCustomer : " + Boolean.toString(this.currentCustomer == User.instance.currentCustomer));
        super.onResume();
        if(this.currentCustomer != User.instance.currentCustomer) {
            this.currentCustomer = User.instance.currentCustomer;
            this.onCustomerSelectionChanged();
        }
    }
    protected void onCustomerSelectionChanged() {
        Log.e(TAG, "onCustomerSelectionChanged");
    }
}
