package com.bdwater.waterservice;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.bdwater.waterservice.main.BottomNavigationCollection;
import com.bdwater.waterservice.main.MainPagerAdapter;
import com.bdwater.waterservice.model.User;
import com.bdwater.waterservice.pressure.PressureActivity;
import com.mikepenz.iconics.view.IconicsImageView;
import com.nikhilpanju.recyclerviewenhanced.OnActivityTouchListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements RecyclerTouchListener.RecyclerTouchListenerHelper {
    public static final int PAGE_MAIN = 0;
    public static final int PAGE_QUERY = 1;
    public static final int PAGE_REPORT = 2;
    public static final int PAGE_SITE = 3;
    public static final int PAGE_NOTIFICATION = 4;

    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.phoneTextView)
    TextView phoneTextView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.logoutButton)
    Button logoutButton;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.bottomNavigation)
    AHBottomNavigation bottomNavigation;

    MainAdapter mainAdapter;
    MainPagerAdapter adapter;
    private RecyclerTouchListener onTouchListener;
    private OnActivityTouchListener touchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        phoneTextView.setText(User.instance.tel);

        RowModel[] list = new RowModel[User.instance.customers.length];
        for(int i = 0; i < User.instance.customers.length; i++) {
            User.Customer customer = User.instance.customers[i];
            list[i] = new RowModel(Long.toString(customer.customerNo),
                    customer.customerName);
            list[i].current = User.instance.isCurrent(customer);
        }
        mainAdapter = new MainAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mainAdapter);

        this.onTouchListener = new RecyclerTouchListener(this, recyclerView);
        this.onTouchListener.setClickable(new RecyclerTouchListener.OnRowClickListener() {
            @Override
            public void onRowClicked(int position) {
                RowModel row = mainAdapter.getItem(position);
                Long no = Long.parseLong(row.mainText);
                if(!User.instance.isCurrent(no)) {
                    drawerLayout.closeDrawers();
                    User.instance.setCurrentCustomer(no);
                    Updatable updatable = (Updatable) adapter.getFragment(viewPager.getCurrentItem());
                    updatable.checkAndRunUpdate();
                    mainAdapter.setCurrent(position);
                    mainAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onIndependentViewClicked(int independentViewID, int position) {

            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User.instance.clear(MainActivity.this);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        adapter = new MainPagerAdapter(getSupportFragmentManager(), this);
        // setOffscreenPageLimit means that the number of pages will be keep in memory
        // beyond this number the page will be created
        viewPager.setOffscreenPageLimit(3);
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
            if(adapter.getFragment(position) instanceof Updatable) {
                ((Updatable)adapter.getFragment(position)).checkAndRunUpdate();
            }
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

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.addOnItemTouchListener(onTouchListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        recyclerView.removeOnItemTouchListener(onTouchListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (touchListener != null) touchListener.getTouchCoordinates(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setOnActivityTouchListener(OnActivityTouchListener listener) {
        this.touchListener = listener;
    }

    class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
        LayoutInflater inflater;
        RowModel[] modelList;

        public MainAdapter(Context context, RowModel[] list) {
            inflater = LayoutInflater.from(context);
            modelList = list;
        }

        @Override
        public MainAdapter.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.drawer_item, parent, false);
            return new MainViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MainAdapter.MainViewHolder holder, int position) {
            holder.bindData(modelList[position]);
        }

        @Override
        public int getItemCount() {
            return modelList.length;
        }
        public RowModel getItem(int position) {
            return modelList[position];
        }
        public void setCurrent(int position) {
            for(int i = 0; i < this.modelList.length; i++) {
                this.modelList[i].current = position == i;
            }
        }

        class MainViewHolder extends RecyclerView.ViewHolder {
            TextView mainText, subText;
            IconicsImageView icon;
            public MainViewHolder(View itemView) {
                super(itemView);
                icon = (IconicsImageView)itemView.findViewById(R.id.icon);
                mainText = (TextView)itemView.findViewById(R.id.mainText);
                subText = (TextView)itemView.findViewById(R.id.subText);
            }

            public void bindData(RowModel rowModel) {
                icon.setVisibility(rowModel.current ? View.VISIBLE: View.INVISIBLE);
                mainText.setText(rowModel.mainText);
                subText.setText(rowModel.subText);
            }
        }

    }
    class RowModel {
        public boolean current = false;
        public String mainText;
        public String subText;
        public RowModel(String main, String sub) {
            this.mainText = main;
            this.subText = sub;
        }
    }
}
