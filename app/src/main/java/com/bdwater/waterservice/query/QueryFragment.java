package com.bdwater.waterservice.query;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.bdwater.waterservice.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class QueryFragment extends Fragment {
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
        return view;
    }

}
