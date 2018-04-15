package com.bdwater.waterservice.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bdwater.waterservice.R;
import com.bdwater.waterservice.site.SiteFragment;

/**
 * Created by hegang on 18/4/13.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
            case 1:
            case 2:
                fragment = HomeFragment.newInstance();
                break;
            case 3:
                fragment = SiteFragment.newInstance();
                break;
            case 4:
                fragment = HomeFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
