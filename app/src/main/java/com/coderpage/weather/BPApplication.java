package com.coderpage.weather;

import android.app.Application;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

public class BPApplication extends Application {

    public LocationClient locationClient;
    public LocationListener locationListener;
	@Override
	public void onCreate() {
		super.onCreate();
        locationClient = new LocationClient(this.getApplicationContext());
        locationListener = new LocationListener();
        locationClient.registerLocationListener(locationListener);
	}

    public class LocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String city = bdLocation.getCity();
            String province = bdLocation.getProvince();
            String district = bdLocation.getDistrict();
        }
    }
}
