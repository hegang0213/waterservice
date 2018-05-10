package com.bdwater.waterservice.site;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.bdwater.waterservice.R;
import com.bdwater.waterservice.remote.RemoteBase;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SiteFragment extends Fragment {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView webView;


    public SiteFragment() {
        // Required empty public constructor
    }
    static SiteFragment instance;
    public static SiteFragment newInstance() {
        if(instance == null) {
            instance = new SiteFragment();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_site, container, false);
        ButterKnife.bind(this, view);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                reload();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if(newProgress == 100) {
                    onLoaded();
                }
            }
        });
        reload();

        return view;
    }
    private void reload() {
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);
        webView.loadUrl(RemoteBase.baseUrl + "WaterControlData/WaterPressure");

    }
    private void onLoaded() {
        progressBar.setVisibility(View.GONE);
        refreshLayout.finishRefresh();
    }


}
