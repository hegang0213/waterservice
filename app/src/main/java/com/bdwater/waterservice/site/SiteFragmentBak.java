package com.bdwater.waterservice.site;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bdwater.waterservice.R;
import com.bdwater.waterservice.utils.IconicsUtil;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.Circle;
import com.tencent.mapsdk.raster.model.CircleOptions;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SiteFragmentBak extends Fragment {
    @BindView(R.id.mapview)
    MapView mapview;
    @BindView(R.id.addressTextView)
    TextView addressTextView;
    @BindView(R.id.meButton)
    ImageView meButton;

    // map is loaded when the first time
    Boolean isFirstLocate = true;
    TencentLocationManager locationManager;
    TencentLocation tencentLocation;
    Bitmap icoNavigation;
    // current position
//    LatLng location;
    Marker marker;
    // accuracy of position of map
    Circle circle;

    public SiteFragmentBak() {
        // Required empty public constructor
    }
    static SiteFragmentBak instance;
    public static SiteFragmentBak newInstance() {
        if(instance == null) {
            instance = new SiteFragmentBak();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_site, container, false);
        ButterKnife.bind(this, view);

        // me position icon, use to marker of map
        icoNavigation = IconicsUtil.getBitmap(getActivity(),
                CommunityMaterial.Icon.cmd_map_marker_radius, 48, getResources().getColor(R.color.locationColor));

        // zoom level 14 : street level
        // level range: 5 - 19
        mapview.getMap().setZoom(14);

        // location manager
        // error = 0 is success
        // locationListener was called at intervals
        locationManager = TencentLocationManager.getInstance(getActivity());
        TencentLocationRequest request = TencentLocationRequest.create();
        int error = locationManager.requestLocationUpdates(request, locationListener);
        Log.e("SiteFragment", "Location request error:" + Integer.toString(error));

        addressTextView.setVisibility(View.GONE);
        meButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToCurrentLocation();
            }
        });

        return view;
    }
    private void moveToCurrentLocation() {
        if(tencentLocation == null) return;
        LatLng location = new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude());
        mapview.getMap().animateTo(location);
    }

    boolean preventListen = false;
    private TencentLocationListener locationListener = new TencentLocationListener() {
        @Override
        public void onLocationChanged(final TencentLocation tencentLocationArgs, int error, String reason) {
            if(preventListen) return;
            if(error == TencentLocation.ERROR_OK) {
                // it's success to get location
                // set title by address
                addressTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        addressTextView.setVisibility(View.VISIBLE);
                        addressTextView.setText(getResources().getString(R.string.current_location) + tencentLocation.getAddress());
                    }
                });
                // get lat
                tencentLocation = tencentLocationArgs;
                drawMapUIs();
                if(isFirstLocate) {
                    // first locate to set center of map and
                    // move to
                    moveToCurrentLocation();
                    isFirstLocate = false;
                }

                Log.e("location", "location:" + reason);
            }
            else
                Log.e("location", "location failed:" + reason);
        }

        @Override
        public void onStatusUpdate(String arg0, int arg1, String arg2) {
        // TODO Auto-generated method stub
            String desc = "";
            switch (arg1) {
                case STATUS_DENIED:
                    desc = "权限被禁止";
                    break;
                case STATUS_DISABLED:
                    desc = "模块关闭";
                    break;
                case STATUS_ENABLED:
                    desc = "模块开启";
                    break;
                case STATUS_GPS_AVAILABLE:
                    desc = "GPS可用，代表GPS开关打开，且搜星定位成功";
                    break;
                case STATUS_GPS_UNAVAILABLE:
                    desc = "GPS不可用，可能 gps 权限被禁止或无法成功搜星";
                    break;
                case STATUS_LOCATION_SWITCH_OFF:
                    desc = "位置信息开关关闭，在android M系统中，此时禁止进行wifi扫描";
                    break;
                case STATUS_UNKNOWN:
                    break;
            }
            Log.e("location", "location status:" + arg0 + ", " + arg2 + " " + desc);
        }
    };

    private void drawMapUIs() {
        // return if tencentLocation is never be set
        if(null == tencentLocation) return;
        LatLng location = new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude());

        if(null == marker) {
            // add a marker into map, the marker indicates user's position
            marker = mapview.getMap().addMarker(new MarkerOptions()
                    .position(location)
                    .title(getResources().getString(R.string.current_location_name))
                    .icon(BitmapDescriptorFactory.fromBitmap(icoNavigation))
                    .anchor(0.5f, 0.5f)
            );
            marker.showInfoWindow();
        }

        if(null == circle)
            // the circle indicate accuracy of position
            // you can see the circle util map becomeS big enough
            circle = mapview.getMap().addCircle(new CircleOptions()
                    .center(location)
                    .radius((double)tencentLocation.getAccuracy())
                    .fillColor(0x44808080)
                    .strokeColor(0x808080)
                    .strokeWidth(1f)
            );


        // update marker and circle
        marker.setPosition(location);
        marker.setRotation(tencentLocation.getBearing());
        circle.setCenter(location);
        circle.setRadius(tencentLocation.getAccuracy());
    }

    // clear all marks and circles of this map
    // remove listener of location manager
    private void clearAll() {
        try {
            preventListen = true;
            locationManager.removeUpdates(locationListener);
            if (null != marker) {
                marker.remove();
                marker = null;
            }
            if (null != circle) {
                circle.remove();
                circle = null;
            }
            isFirstLocate = true;
        }
        finally {
            preventListen = false;
        }
    }

    /////////////////////////////////
    // Notice
    // 1. create new fragment will call
    // onCreate, onCreateView, onResume
    // 2. exit this fragment will call
    // onPause, onStop, onDestroyView
    // 3. recreate fragment will call
    // onCreateView, onResume
    /////////////////////////////////
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapview.onDestroyView();
        clearAll();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if(null != mapview)
            mapview.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapview.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapview.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapview.onResume();

        // move to current location if location has been requested
        moveToCurrentLocation();
        // draw map UIs if markers has been set
        drawMapUIs();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapview.onStop();
    }

}
