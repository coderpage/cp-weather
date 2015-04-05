package com.baasplus.weather.controler;

import android.content.Context;
import android.database.Cursor;

import com.baasplus.weather.db.DBHelper;
import com.baasplus.weather.define.DefineMessage;
import com.baasplus.weather.define.DefineSQL.MyDbTableCareCitys;
import com.baasplus.weather.model.City;
import com.baasplus.weather.model.Weather;
import com.baasplus.weather.view.MainActivity;
import com.baasplus.weather.view.SlidingDrawerFragment;

import java.util.ArrayList;

/**
 * 继承ArrayList，存储元素为City 单例实现，保证对象的唯一性，维护所要显示所有城市 对该CitysList的所有操作需有考虑线程安全问题
 */
public class CitysList extends ArrayList<City> {

	private static final long serialVersionUID = -5492195926551969982L;

	public static CitysList mCitysList;
	private Context mContext;

	private static DBHelper mDbHelper;

	public static synchronized CitysList getInstance(Context context) {
		if (mCitysList != null) {
			return mCitysList;
		} else {
			mCitysList = new CitysList(context);
			return mCitysList.initCitysList();
		}

	}

	private CitysList(Context context) {
		mContext = context;
		mDbHelper = DBHelper.getInstance(mContext);
	}


	/**
	 * 首次初始化CitysList，从数据库中读取所保存的城市
	 * @return CitysList
	 */
	private CitysList initCitysList() {
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

	/**
	 * 查询mCitysList所有城市的天气信息，
	 * 对mCitysList的查询操作使用synchronized做同步处理，保证查询的时候不会被其他线程修改mCitysList
	 * 该方法不会直接修改更新天气信息，只会去查询天气，更新操作在查询成功后调用updateWeather方法执行
	 */
	public static synchronized void initCityWeather() {
		for (City c : mCitysList) {
			if (c.code == null) {
				continue;
			}
			WeatherHelper weatherHeiper = new WeatherHelper();
			weatherHeiper.queryWeather(c.code);
		}
	}

	/**
	 * 更新mCitysList对应城市的天气信息
	 * 对mCitysList的修改操作使用synchronized做同步处理，保证修改的时候不会被其他线程对mCitysList操作
	 * @param cityCode 城市代码
	 * @param weather 天气信息
	 */
	public static synchronized void updateWeather(String cityCode, Weather weather) {
		for (City c : mCitysList) {
			if (cityCode.equals(c.code)) {
				c.weather = weather;
				mDbHelper.updateLastUpdateTime(System.currentTimeMillis(), c.code);
                MainActivity.updateWeather(c);
				return;
			}

		}
		
	}

	/**
	 * 向mCitysList添加城市
	 * @param id 需要添加的城市在my.db中对应主键id
	 */
	public void addCity(long id) {
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
			MainActivity.addNewCity(c);
			SlidingDrawerFragment.updateListView();
		}
	}

	/**
	 * 删除城市
	 * @param code 城市代码
	 */
	public void deleteCity(String code) {
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

    public boolean exist(City city){
        for(City c: mCitysList){
            if (c.code.equals(city.code)){
                return true;
            }
        }
        return false;
    }
}
