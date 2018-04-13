package com.bdwater.waterservice;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

import com.bdwater.waterservice.main.MainPagerAdapter;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.bottomNavigation)
    BottomNavigation bottomNavigation;

    MainPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        adapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);
    }
}
