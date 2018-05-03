package com.bdwater.waterservice.main;


import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bdwater.waterservice.MainActivity;
import com.bdwater.waterservice.MainActivityFragment;
import com.bdwater.waterservice.R;
import com.bdwater.waterservice.utils.Utility;
import com.mikepenz.iconics.view.IconicsButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class HomeFragment extends MainActivityFragment {
    @BindView(R.id.detailButton)
    IconicsButton detailButton;
    @BindView(R.id.weixinButton)
    IconicsButton weixinButton;
    @BindView(R.id.alipayButton)
    IconicsButton alipayButton;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        final PackageManager packageManager
                = this.getActivity().getApplicationContext().getPackageManager();

        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(MainActivity.PAGE_QUERY);
            }
        });
        weixinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Utility.isAvailiable(getActivity(), "com.tencent.mm"))
                    return;
                Intent intent = new Intent();
                ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");// 报名该有activity

                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setComponent(cmp);

                startActivityForResult(intent, 0);
            }
        });
        alipayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Utility.isAvailiable(getActivity(), "com.eg.android.AlipayGhpone"))
                    return;
                Intent intent = packageManager.getLaunchIntentForPackage("com.eg.android.AlipayGphone");

                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivityForResult(intent, 0);
            }
        });

        return view;
    }

}
