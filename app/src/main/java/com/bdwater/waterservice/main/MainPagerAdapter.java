package com.bdwater.waterservice.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.bdwater.waterservice.R;
import com.bdwater.waterservice.notification.NotificationFragment;
import com.bdwater.waterservice.query.QueryFragment;
import com.bdwater.waterservice.report.ReportFragment;
import com.bdwater.waterservice.site.SiteFragment;

import java.util.HashMap;

/**
 * Created by hegang on 18/4/13.
 */

public class MainPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private FragmentManager fragmentManager;
    private HashMap<Integer, String> fragmentTags;
    private Integer size;
    public MainPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.fragmentManager = fm;
        this.fragmentTags = new HashMap<Integer, String>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = HomeFragment.newInstance();
                break;
            case 1:
                fragment = QueryFragment.newInstance();
                break;
            case 2:
                fragment = ReportFragment.newInstance();
                break;
            case 3:
                fragment = SiteFragment.newInstance();
                break;
            case 4:
                fragment = NotificationFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return size;
    }
    public void setCount(int count) {
        size = count;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            String tag = fragment.getTag();
            this.fragmentTags.put(position, tag);
        }
        return object;
    }

    public Fragment getFragment(int position) {
        Fragment fragment = null;
        String tag = this.fragmentTags.get(position);
        if (tag != null) {
            fragment = this.fragmentManager.findFragmentByTag(tag);
        }
        return fragment;
    }
}
