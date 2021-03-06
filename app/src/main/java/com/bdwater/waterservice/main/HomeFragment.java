package com.bdwater.waterservice.main;


import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bdwater.waterservice.CustomApplication;
import com.bdwater.waterservice.MainActivity;
import com.bdwater.waterservice.MainActivityFragment;
import com.bdwater.waterservice.R;
import com.bdwater.waterservice.model.Customer;
import com.bdwater.waterservice.model.User;
import com.bdwater.waterservice.remote.HomeRemote;
import com.bdwater.waterservice.remote.RemoteBase;
import com.bdwater.waterservice.remote.RemoteListener;
import com.bdwater.waterservice.utils.Utility;
import com.google.gson.Gson;
import com.mikepenz.iconics.view.IconicsButton;
import com.nikhilpanju.recyclerviewenhanced.OnActivityTouchListener;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.w3c.dom.Text;

import java.io.IOException;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 *
 */
public class HomeFragment extends MainActivityFragment {
    public static String TAG = "HomeFragment";

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.customerNoTextView)
    TextView customerNoTextView;
    @BindView(R.id.customerNameTextView)
    TextView customerNameTextView;
    @BindView(R.id.customerAddressTextView)
    TextView customerAddressTextView;
    @BindView(R.id.customerPhoneTextView)
    TextView customerPhoneTextView;

    @BindView(R.id.isBillTextView)
    TextView isBillTextView;
    @BindView(R.id.billPayTextView)
    TextView billPayTextView;
    @BindView(R.id.depositTextView)
    TextView depositTextView;

    @BindView(R.id.detailButton)
    IconicsButton detailButton;
    @BindView(R.id.weixinButton)
    IconicsButton weixinButton;
    @BindView(R.id.alipayButton)
    IconicsButton alipayButton;

    HomeRemote homeRemote = new HomeRemote();
    Customer customer;
    boolean isLoading = false;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    public HomeFragment() {
        // Required empty public constructor
        homeRemote.setRemoteListener(this.listener);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        final PackageManager packageManager
                = this.getActivity().getApplicationContext().getPackageManager();

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                load();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePage(MainActivity.PAGE_QUERY);
            }
        });
        weixinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Utility.isAvailiable(getActivity(), "com.tencent.mm")) {
                    Snackbar.make(CustomApplication.getMainActivity().getCoordinatorLayout(), R.string.no_weixin, Snackbar.LENGTH_LONG).show();
                    return;
                }
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
                if(!Utility.isAvailiable(getActivity(), "com.eg.android.AlipayGhpone")) {
                    Snackbar.make(CustomApplication.getMainActivity().getCoordinatorLayout(), R.string.no_alipay, Snackbar.LENGTH_LONG).show();
                    return;
                }
                Intent intent = packageManager.getLaunchIntentForPackage("com.eg.android.AlipayGphone");

                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivityForResult(intent, 0);
            }
        });

        return view;
    }

    @Override
    public void onUpdate() {
        this.load();
    }

    @Override
    protected void onCustomerSelectionChanged() {
        super.onCustomerSelectionChanged();
        Log.e(TAG, "onCustomerSelectionChanged");
        this.load();
    }
    private void load() {
        Log.e(TAG, "load()");
        if(isLoading) return;
        isLoading = true;
        homeRemote.getCustomer(User.instance.currentCustomer.customerNo);
        progressBar.setVisibility(View.VISIBLE);
    }

    private RemoteListener listener = new RemoteListener() {
        @Override
        public void onResponse(String target, Call call, int state, String jsonResult, IOException exception) {
            Log.e(TAG, "onResponse");
            if(state == RemoteBase.SUCCESS_STATE) {
                customer = new Gson().fromJson(jsonResult, Customer.class);
                customerNoTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        updateUI();
                    }
                });
                currentCustomer = User.instance.currentCustomer;
            }
            else {
                Utility.showAlertDialog(customerNoTextView, getActivity(), "错误",
                        exception.getMessage() == null? exception.toString(): exception.getMessage());
            }
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    completed();
                }
            });

        }
    };
    private void updateUI() {
        Log.e(TAG, "updateUI()");
        this.customerPhoneTextView.setText(User.instance.tel);

        this.customerNoTextView.setText(Long.toString(this.customer.customerNo));
        this.customerNameTextView.setText(this.customer.customerName);
        this.customerAddressTextView.setText(this.customer.address);

        this.isBillTextView.setText(this.customer.billTotalCurrentPay > this.customer.deposit ? "有": "无");
        this.billPayTextView.setText(Double.toString(this.customer.billTotalCurrentPay));
        this.depositTextView.setText(Double.toString(this.customer.deposit));

    }
    private void completed() {
        Log.e(TAG, "completed()");
        progressBar.setVisibility(View.INVISIBLE);
        refreshLayout.finishRefresh();
        isLoading = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.e(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }
}
