package com.coderpage.weather;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.coderpage.weather.data.Locate;

public class BPApplication extends Application {

    public LocationClient locationClient;
    public LocationListener locationListener;
    private Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        locationClient = new LocationClient(this.getApplicationContext());
        locationListener = new LocationListener();
        locationClient.registerLocationListener(locationListener);
    }

    public class LocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String city = bdLocation.getCity();
            String province = bdLocation.getProvince();
            String district = bdLocation.getDistrict();
            Log.e("locationg info :", "province=" + province + "  city=" + city + "  district=" + district);
            Locate locate = new Locate(context, province, city, district);
            boolean isAddLoacation = locate.addLocationCity();
            Log.e("isAddLoacation",isAddLoacation + "");
            locationClient.stop();
        }
    }
}
