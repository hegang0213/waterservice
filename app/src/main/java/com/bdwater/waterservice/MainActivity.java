package com.bdwater.waterservice;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.bdwater.waterservice.main.BottomNavigationCollection;
import com.bdwater.waterservice.main.MainPagerAdapter;
import com.bdwater.waterservice.pressure.PressureActivity;

public class MainActivity extends BaseActivity {
    public static final int PAGE_MAIN = 0;
    public static final int PAGE_QUERY = 1;
    public static final int PAGE_REPORT = 2;
    public static final int PAGE_SITE = 3;
    public static final int PAGE_NOTIFICATION = 4;

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
        // setOffscreenPageLimit means that the number of pages will be keep in memory
        // beyond this number the page will be created
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(pageChangeListener);

        // create items of bottom navigation
        BottomNavigationCollection collection = new BottomNavigationCollection(this);
        collection.setupWithBottomNavigation(bottomNavigation);
        // set title always show
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        // set color of selected item
        bottomNavigation.setAccentColor(getResources().getColor(R.color.navigationAccentColor));
        // set size of font
        bottomNavigation.setTitleTextSize(18, 18);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // set current page when tab was selected
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
            // set current item of navigation when page was changed
            bottomNavigation.setCurrentItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    public void changePage(int position) {
        bottomNavigation.setCurrentItem(position);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_pressure:
                Intent intent =  new Intent(MainActivity.this, PressureActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }
}
