package com.bdwater.waterservice.site;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bdwater.waterservice.R;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SiteFragment extends Fragment {
    @BindView(R.id.mapview)
    MapView mapview;

    public SiteFragment() {
        // Required empty public constructor
    }
    public static SiteFragment newInstance() {
        return new SiteFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_site, container, false);
        ButterKnife.bind(this, view);

        mapview.getMap().setOnMyLocationChangeListener(new TencentMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                Log.i("AA", location.toString());
            }
        });
        return view;
    }

    @Override
    public void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        mapview.onPause();
        super.onPause();
    }
    @Override
    public void onResume() {
        mapview.onResume();
        super.onResume();
    }

    @Override
    public void onStop() {
        mapview.onStop();
        super.onStop();
    }

}
