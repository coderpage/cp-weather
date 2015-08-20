package com.coderpage.weather.model;

import com.coderpage.weather.define.DefineSQL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by abner-l on 15/8/4.
 */
public class Quality implements Serializable {
    protected int qualityIndex; // 空气质量指数
    protected int co; // 一氧化碳1小时平均值(ug/m³)
    protected int no2; //二氧化氮1小时平均值(ug/m³)
    protected int o3; //臭氧1小时平均值(ug/m³)
    protected int pm10; //PM10 1小时平均值(ug/m³)
    protected int pm25; //PM2.5 1小时平均值(ug/m³)
    protected String qualityType; //空气质量类别
    protected int so2; //二氧化硫1小时平均值(ug/m³)

    public static Quality instanceByJson(JSONObject json) {
        Quality instance = new Quality();

        if (json == null) {
            return instance;
        }

        update(instance, json);

        return instance;
    }

    public static void update(Quality instance, JSONObject json) {
        try {

            instance.qualityIndex = json.getInt("aqi");
            instance.pm10 = json.getInt("pm10");
            instance.pm25 = json.getInt("pm25");
            instance.qualityType = json.getString("qlty");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getQualityIndex() {
        return qualityIndex;
    }

    public int getCo() {
        return co;
    }

    public int getNo2() {
        return no2;
    }

    public int getO3() {
        return o3;
    }

    public int getPm10() {
        return pm10;
    }

    public int getPm25() {
        return pm25;
    }

    public String getQualityType() {
        return qualityType;
    }

    public int getSo2() {
        return so2;
    }

    public static final int TYPE = 1;
    public static final String QUALITY_INDEX = DefineSQL.MyDbTableWeatherCache.COLUMN_DATA_1;
    public static final String CO = DefineSQL.MyDbTableWeatherCache.COLUMN_DATA_2;
    public static final String NO2 = DefineSQL.MyDbTableWeatherCache.COLUMN_DATA_3;
    public static final String O3 = DefineSQL.MyDbTableWeatherCache.COLUMN_DATA_4;
    public static final String PM10 = DefineSQL.MyDbTableWeatherCache.COLUMN_DATA_5;
    public static final String PM25 = DefineSQL.MyDbTableWeatherCache.COLUMN_DATA_6;
    public static final String QUALITY_TYPE = DefineSQL.MyDbTableWeatherCache.COLUMN_DATA_7;
    public static final String SO2 = DefineSQL.MyDbTableWeatherCache.COLUMN_DATA_7;

}
