package com.bdwater.waterservice.report;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.bdwater.waterservice.MainActivityFragment;
import com.bdwater.waterservice.R;
import com.bdwater.waterservice.component.BottomDialog;
import com.bdwater.waterservice.model.User;
import com.bdwater.waterservice.remote.RemoteBase;
import com.mikepenz.iconics.view.IconicsImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends MainActivityFragment {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.backImageView)
    IconicsImageView icoImageView;
    public static ReportFragment newInstance() {
        return new ReportFragment();
    }
    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        ButterKnife.bind(this, view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(false);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                String listUrl = RemoteBase.baseUrl + "WaterServices/ServiceTaskList";
                if(url.contains(listUrl))
                    icoImageView.setVisibility(View.VISIBLE);
                else
                    icoImageView.setVisibility(View.GONE);
                super.onPageStarted(view, url, favicon);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if(newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }

        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                reload();
                refreshLayout.finishRefresh();
            }
        });


        icoImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    webView.goBack();
                }
                return true;
            }
        });
        return view;
    }
    @Override
    public void onUpdate() {
        this.reload();
    }

    private void reload() {
        this.webView.loadUrl(RemoteBase.baseUrl + "WaterServices/ServiceTask/?customerNo="
                + this.currentCustomer.customerNo
                + "&phoneNo=" + User.instance.tel);
        //this.icoImageView.setVisibility(View.GONE);
        this.progressBar.setProgress(0);
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCustomerSelectionChanged() {
        super.onCustomerSelectionChanged();
        this.reload();
    }

}
