package com.bdwater.waterservice.report;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bdwater.waterservice.R;
import com.bdwater.waterservice.component.BottomDialog;
import com.mikepenz.iconics.view.IconicsImageView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportFragment extends Fragment {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.plusImageView)
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

        icoImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    BottomDialog dialog = new BottomDialog(getActivity(), R.layout.layout_report_add);
                    dialog.show();
                }
                return true;
            }
        });
        return view;
    }

}
