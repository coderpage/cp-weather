package com.example.hzqweather.controler;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.example.hzqweather.db.DBHelper;
import com.example.hzqweather.define.DefineSQL.MyDbTableCareCitys;
import com.example.hzqweather.model.City;
import com.example.hzqweather.model.Weather;

public class CitysList extends ArrayList<City> {

	private static final long serialVersionUID = -5492195926551969982L;

	public static CitysList mCitysList = null;
	private  Context mContext;
	
	public static synchronized CitysList getInstance(Context context){
		if (mCitysList != null) {
			return mCitysList;
		}else {
			mCitysList = new CitysList(context);
			return mCitysList.initCitysList();
		}
		
	}
	private CitysList(Context context){
		mContext = context;
	}

	private  CitysList initCitysList() {
		DBHelper dbHelper = DBHelper.getInstance(mContext);
		Cursor cur = dbHelper.queryAll(MyDbTableCareCitys.TABLE_NAME);
		System.out.println("cur.getCount():  "+cur.getCount());
		while (cur.moveToNext()) {
			String cityName = cur.getString(cur.getColumnIndex(MyDbTableCareCitys.COLUMN_CITY_NAME));
			String cityCode = cur.getString(cur.getColumnIndex(MyDbTableCareCitys.COLUMN_CITY_CODE));
			long lastUpdateTime = cur.getLong(cur.getColumnIndex(MyDbTableCareCitys.COLUMN_LAST_UPDATE_TIME));
			City c = new City();
			c.displayName = cityName;
			c.code = cityCode;
			c.lastUpdateTime = lastUpdateTime;
			mCitysList.add(c);
		}
		System.out.println("mCitysList.size(): " + mCitysList.size());
		return mCitysList;
	}

	public static synchronized void initCityWeather() {
		for (City c : mCitysList) {
			WeatherHeiper weatherHeiper = new WeatherHeiper();
			weatherHeiper.queryWeather(c.code);
		}
	}

	public static synchronized void updateWeather(String cityCode, Weather weather) {
		for (City c : mCitysList) {
			System.out.println("citycode = " + cityCode + "  c = " + c.toString());
			if (cityCode.equals(c.code)) {
				c.weather = weather;
				return;
			}
		}
	}

}
