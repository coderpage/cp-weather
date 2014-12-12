package com.example.hzqweather.controler;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

import com.example.hzqweather.MainActivity;
import com.example.hzqweather.db.DBHelper;
import com.example.hzqweather.define.DefineMessage;
import com.example.hzqweather.define.DefineSQL.MyDbTableCareCitys;
import com.example.hzqweather.model.City;
import com.example.hzqweather.model.Weather;

public class CitysList extends ArrayList<City> {

	private static final long serialVersionUID = -5492195926551969982L;

	public static CitysList mCitysList = null;
	private  Context mContext;

	private static DBHelper mDbHelper;
	
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
		mDbHelper = DBHelper.getInstance(mContext);
	}

	private  CitysList initCitysList() {
		Cursor cur = mDbHelper.queryAll(MyDbTableCareCitys.TABLE_NAME);
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
			if (cityCode.equals(c.code)) {
				c.weather = weather;
				mDbHelper.updateLastUpdateTime(System.currentTimeMillis(), c.code);
				return;
			}
		}
		
	}
	
	public void addCity(long id){
		Cursor cur = mDbHelper.queryByRowId(MyDbTableCareCitys.TABLE_NAME, id);
		if (cur.moveToNext()) {
			String cityName = cur.getString(cur.getColumnIndex(MyDbTableCareCitys.COLUMN_CITY_NAME));
			String cityCode = cur.getString(cur.getColumnIndex(MyDbTableCareCitys.COLUMN_CITY_CODE));
			long lastUpdateTime = cur.getLong(cur.getColumnIndex(MyDbTableCareCitys.COLUMN_LAST_UPDATE_TIME));
			City c = new City();
			c.displayName = cityName;
			c.code = cityCode;
			c.lastUpdateTime = lastUpdateTime;
			synchronized (CitysList.class) {
				mCitysList.add(c);
			}
			MainActivity.mHandler.sendEmptyMessage(DefineMessage.MSG_UPDATEUI);
		}
	}
	
	public void deleteCity(String code){
		for (int i = 0; i < mCitysList.size(); i++) {
			if (code.equals(mCitysList.get(i).code)) {
				synchronized (CitysList.class) {
					mCitysList.remove(i);
				}
				MainActivity.mHandler.sendEmptyMessage(DefineMessage.MSG_UPDATEUI);
				return;
			}
		}
	}

}
