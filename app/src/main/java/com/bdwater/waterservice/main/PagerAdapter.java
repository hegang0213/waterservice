package com.bdwater.waterservice.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bdwater.waterservice.R;

/**
 * Created by hegang on 18/4/13.
 */

public class PagerAdapter extends FragmentPagerAdapter {
    private String[] titles;
    private Context context;
    public PagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.titles = context.getResources().getStringArray(R.array.page_titles);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
            case 1:
            case 2:
            case 3:
                fragment = HomeFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
