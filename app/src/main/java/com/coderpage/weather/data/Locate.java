package com.coderpage.weather.data;

import android.content.Context;
import android.database.Cursor;

import com.coderpage.weather.data.db.CitycodeDBHelper;
import com.coderpage.weather.data.db.DBHelper;
import com.coderpage.weather.define.DefineSQL;
import com.coderpage.weather.model.City;

/**
 * Created by abner-l on 15/4/6.
 */
public class Locate {
    private CitycodeDBHelper db;
    private final String province;
    private final String city;
    private final String district;
    private Context context;

    public Locate(Context context, String province, String city, String district) {
        this.context = context;
        this.province = province;
        this.city = city;
        this.district = district;
        db = CitycodeDBHelper.getInstance(context);
    }

    public boolean addLocationCity() {
        City location;
        location = compareDistrict();
        if (location != null) {
            //找到定位城市，添加
            addLocation(location);
            return true;
        }

        location = compareCity();
        if (location != null) {
            //找到定位城市，添加
            addLocation(location);
            return true;
        }

        //查询定位城市失败，返回
        return false;
    }


    private City compareDistrict() {
        if (district == null || district.equals("")) {
            return null;
        }
        Cursor cursor = db.queryAll();
        while (cursor.moveToNext()) {
            String districtQ = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_COUNTY));
            String cityQ = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_CITY));
            String provinceQ = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_PROVINCE));
            String cityCodeQ = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_CODE));
            if (districtQ == null || !district.contains(districtQ)) {
                continue;
            }
            if (city == null || !city.contains(cityQ)) {
                continue;
            }
            if (province == null || !province.contains(provinceQ)) {
                continue;
            }

            //找到定位城市
            City result = new City(provinceQ, cityQ, districtQ, cityCodeQ);
            result.setLocation(true);
            return result;
        }
        return null;
    }

    private City compareCity() {
        if (city == null || city.equals("")) {
            return null;
        }
        Cursor cursor = db.queryAll();
        while (cursor.moveToNext()) {
            String cityQ = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_CITY));
            String provinceQ = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_PROVINCE));
            String cityCodeQ = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_CODE));

            if (!city.contains(cityQ)) {
                continue;
            }
            if (province == null || !province.contains(provinceQ)) {
                continue;
            }

            //找到定位城市
            City result = new City(provinceQ, cityQ, null, cityCodeQ);
            result.setLocation(true);
            return result;
        }
        return null;
    }

    private boolean addLocation(City city) {
        if (AllCity.cities.exist(city)){
            return true;
        }

        city.updateWeatherAsync();
        DBHelper dbHelper = DBHelper.getInstance(context);
        long rowID = dbHelper.insertCareCitys(city.displayName, city.code, System.currentTimeMillis(),true);
        if (rowID == -1) {
            return false;
        }
        AllCity.cities.addCity(rowID);
        return true;
    }
}
