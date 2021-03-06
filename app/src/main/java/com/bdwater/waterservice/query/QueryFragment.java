package com.bdwater.waterservice.query;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
import com.bdwater.waterservice.model.User;
import com.bdwater.waterservice.remote.RemoteBase;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueryFragment extends MainActivityFragment {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView webView;

    public static QueryFragment newInstance() {
        return new QueryFragment();
    }
    public QueryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_query, container, false);
        ButterKnife.bind(this, view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                progressBar.setVisibility(View.GONE);
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
        return view;
    }

    @Override
    public void onUpdate() {
        this.reload();
    }

    private void reload() {
        this.webView.loadUrl(RemoteBase.baseUrl + "WaterSale/CustomerView/" + this.currentCustomer.customerNo);
        this.progressBar.setProgress(0);
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCustomerSelectionChanged() {
        super.onCustomerSelectionChanged();
        this.reload();
    }
}
