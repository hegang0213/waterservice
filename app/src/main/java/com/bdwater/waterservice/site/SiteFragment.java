package com.bdwater.waterservice.site;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
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
import com.mikepenz.iconics.typeface.IIcon;
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
import com.tencent.tencentmap.mapsdk.map.UiSettings;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SiteFragment extends Fragment {
    @BindView(R.id.mapview)
    MapView mapview;
    @BindView(R.id.addressTextView)
    TextView addressTextView;
    @BindView(R.id.meButton)
    ImageView meButton;

    Boolean isFirst = true;
    TencentLocationManager locationManager;
    LatLng location;
    Marker marker;
    Circle circle;

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

        mapview.onCreate(savedInstanceState);
        mapview.getMap().setZoom(14);

        locationManager = TencentLocationManager.getInstance(getActivity());
        TencentLocationRequest request = TencentLocationRequest.create();
        int error = locationManager.requestLocationUpdates(request, locationListener);
        Log.e("SiteFragment", "Location request error:" + Integer.toString(error));

        addressTextView.setVisibility(View.GONE);
        meButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(location != null)
                    mapview.getMap().animateTo(location);
            }
        });

        return view;
    }

    private TencentLocationListener locationListener = new TencentLocationListener() {
        @Override
        public void onLocationChanged(final TencentLocation tencentLocation, int error, String reason) {
            if(error == TencentLocation.ERROR_OK) {
                addressTextView.post(new Runnable() {
                    @Override
                    public void run() {
                        addressTextView.setVisibility(View.VISIBLE);
                        addressTextView.setText(getResources().getString(R.string.current_location) + tencentLocation.getAddress());
                    }
                });
                location = new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude());
                if(isFirst) {
                    mapview.getMap().animateTo(location);
                    isFirst = false;
                }
                if(null == marker)
                    marker = mapview.getMap().addMarker(new MarkerOptions()
                            .position(location)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_arrow_up_bold_circle))
                            .anchor(0.5f, 0.5f)
                    );
                if(null == circle)
                    circle = mapview.getMap().addCircle(new CircleOptions()
                            .center(location)
                            .radius((double)tencentLocation.getAccuracy())
                            .fillColor(0x440000ff)
                            .strokeWidth(0f)
                    );

                marker.setPosition(location);
                marker.setRotation(tencentLocation.getBearing());
                circle.setCenter(location);
                circle.setRadius(tencentLocation.getAccuracy());
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

    @Override
    public void onDestroy() {
        mapview.onDestroy();
        locationManager.removeUpdates(locationListener);
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
