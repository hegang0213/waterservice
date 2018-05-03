package com.bdwater.waterservice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by hegang on 2018/5/3.
 */

public class MainActivityFragment extends Fragment {
    private MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity)getActivity();
    }

    protected void changePage(int position) {
        mainActivity.changePage(position);
    }
}
