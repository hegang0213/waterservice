package com.bdwater.waterservice;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.bdwater.waterservice.main.BottomNavigationCollection;
import com.bdwater.waterservice.main.MainPagerAdapter;

public class MainActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.bottomNavigation)
    AHBottomNavigation bottomNavigation;

    MainPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);

        setSupportActionBar(toolbar);

        adapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(pageChangeListener);

        BottomNavigationCollection collection = new BottomNavigationCollection(this);
        collection.setupWithBottomNavigation(bottomNavigation);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.navigationAccentColor));
        bottomNavigation.setTitleTextSize(18, 18);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                viewPager.setCurrentItem(position);
                return true;
            }
        });

    }
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            bottomNavigation.setCurrentItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
