package com.coderpage.weather.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abner-l on 15/8/4.
 */
public class Quality {
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
            instance.co = json.getInt("co");
            instance.no2 = json.getInt("no2");
            instance.o3 = json.getInt("o3");
            instance.pm10 = json.getInt("pm10");
            instance.pm25 = json.getInt("pm25");
            instance.qualityType = json.getString("qlty");
            instance.so2 = json.getInt("so2");

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
}
