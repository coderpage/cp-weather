package com.coderpage.weather.data;

import android.content.Context;
import android.database.Cursor;

import com.coderpage.weather.data.db.DBHelper;
import com.coderpage.weather.define.DefineMessage;
import com.coderpage.weather.define.DefineSQL.MyDbTableCareCitys;
import com.coderpage.weather.model.City;
import com.coderpage.weather.model.Weather;
import com.coderpage.weather.view.MainActivity;
import com.coderpage.weather.view.SlidingDrawerFragment;

import java.util.ArrayList;

/**
 * 继承ArrayList，存储元素为City 单例实现，保证对象的唯一性，维护所要显示所有城市 对该CitysList的所有操作需有考虑线程安全问题
 */
public class Cities extends ArrayList<City> {

    private static final long serialVersionUID = -5492195926551969982L;

    public static Cities mCities;
    private Context mContext;

    private static DBHelper mDbHelper;

    public static synchronized Cities getInstance(Context context) {
        if (mCities != null) {
            return mCities;
        } else {
            mCities = new Cities(context);
            return mCities.initCitysList();
        }

    }

    private Cities(Context context) {
        mContext = context;
        mDbHelper = DBHelper.getInstance(mContext);
    }


    /**
     * 首次初始化CitysList，从数据库中读取所保存的城市
     *
     * @return CitysList
     */
    private Cities initCitysList() {
        Cursor cur = mDbHelper.queryAll(MyDbTableCareCitys.TABLE_NAME);
        while (cur.moveToNext()) {
            String cityName = cur.getString(cur.getColumnIndex(MyDbTableCareCitys.COLUMN_CITY_NAME));
            String cityCode = cur.getString(cur.getColumnIndex(MyDbTableCareCitys.COLUMN_CITY_CODE));
            long lastUpdateTime = cur.getLong(cur.getColumnIndex(MyDbTableCareCitys.COLUMN_LAST_UPDATE_TIME));
            int location = cur.getInt(cur.getColumnIndex(MyDbTableCareCitys.COLUMN_LOCATION));
            City c = new City();
            c.setDisplayName(cityName);
            c.setCode(cityCode);
            c.setLastUpdateTime(lastUpdateTime);
            boolean isLocation = location == 1 ? true : false;
            c.setLocation(isLocation);
            mCities.add(c);
        }
        return mCities;
    }

    /**
     * 查询mCitysList所有城市的天气信息，
     * 对mCitysList的查询操作使用synchronized做同步处理，保证查询的时候不会被其他线程修改mCitysList
     * 该方法不会直接修改更新天气信息，只会去查询天气，更新操作在查询成功后调用updateWeather方法执行
     */
    public static synchronized void initCityWeather() {
        for (City c : mCities) {
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
     *
     * @param cityCode 城市代码
     * @param weather  天气信息
     */
    public static synchronized void updateWeather(String cityCode, Weather weather) {
        for (City c : mCities) {
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
     *
     * @param id 需要添加的城市在my.db中对应主键id
     */
    public void addCity(long id) {
        Cursor cur = mDbHelper.queryByRowId(MyDbTableCareCitys.TABLE_NAME, id);
        if (cur.moveToNext()) {
            String cityName = cur.getString(cur.getColumnIndex(MyDbTableCareCitys.COLUMN_CITY_NAME));
            String cityCode = cur.getString(cur.getColumnIndex(MyDbTableCareCitys.COLUMN_CITY_CODE));
            long lastUpdateTime = cur.getLong(cur.getColumnIndex(MyDbTableCareCitys.COLUMN_LAST_UPDATE_TIME));
            int location = cur.getInt(cur.getColumnIndex(MyDbTableCareCitys.COLUMN_LOCATION));
            City c = new City();
            c.displayName = cityName;
            c.code = cityCode;
            c.lastUpdateTime = lastUpdateTime;
            c.setLocation(location == 1 ? true : false);
            synchronized (Cities.class) {
                mCities.add(c);
            }
            MainActivity.addNewCity(c);
            SlidingDrawerFragment.updateListView();
        }
    }

    /**
     * 删除城市
     *
     * @param code 城市代码
     */
    public void deleteCity(String code) {
        for (int i = 0; i < mCities.size(); i++) {
            if (code.equals(mCities.get(i).code)) {
                synchronized (Cities.class) {
                    mCities.remove(i);
                }
                MainActivity.mHandler.sendEmptyMessage(DefineMessage.MSG_UPDATEUI);
                return;
            }
        }
    }

    public boolean exist(City city) {
        for (City c : mCities) {
            if (c.code.equals(city.code)) {
                return true;
            }
        }
        return false;
    }
}
