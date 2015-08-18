package com.coderpage.weather.model;

import android.text.TextUtils;

import com.coderpage.weather.tool.Parser;
import com.coderpage.weather.tool.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class TodayWeather extends Weather implements Serializable {
    private static final long serialVersionUID = 1L;

    private String currentTmp = "";   // 当前温度
    private String updateTime = "";   // 更新时间
    private MultiDays multiDays;      // 一周天气
    private Quality airQuality;       // 空气质量
    private int pressure;             // 气压
    private String sunrise = "";      // 日出时间
    private String sunset = "";       // 日落时间


    public TodayWeather() {
        super(0);
    }

    public static TodayWeather instanceByJson(String json) {

        TodayWeather instance = new TodayWeather();
        if (json == null) {
            return instance;
        }

        update(instance, json);

        return instance;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCurrentTmp() {
        return currentTmp;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public MultiDays getMultiDays() {
        return multiDays;
    }

    public Quality getAirQuality() {
        return airQuality;
    }

    public int getPressure() {
        return pressure;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    @Override
    public String toString() {
        return dayCondition;//city + cityID +
    }

    public static void update(TodayWeather instance, String json) {

        if (instance == null || TextUtils.isEmpty(json)) {
            return;
        }

        try {
            Parser parser = new Parser(json);
            JSONObject now = parser.parseNow();
            instance.dayCondition = now.getJSONObject("cond").getString("txt");
            int condCode = now.getJSONObject("cond").getInt("code");
            parser.addTodayCondition(instance.dayCondition, condCode);

            instance.currentTmp = now.getString("tmp");
            instance.wind = Wind.instanceByJson(now.getJSONObject("wind"));
            instance.pressure = now.getInt("pres");
            instance.airQuality = Quality.instanceByJson(parser.parseAirQuality());

            String updateTimeStr = parser.parseBasic().getJSONObject("update").getString("loc");
            instance.updateTime = TimeUtils.timePast(updateTimeStr);
//            instance.updateTime =parser.parseBasic().getJSONObject("update").getString("loc");

            JSONObject today = parser.parseToday();
            instance.maxTmp = today.getJSONObject("tmp").getString("max");
            instance.minTmp = today.getJSONObject("tmp").getString("min");
            instance.sunrise = today.getJSONObject("astro").getString("sr");
            instance.sunset = today.getJSONObject("astro").getString("ss");
            instance.nightCondition = today.getJSONObject("cond").getString("txt_n");

            instance.multiDays = MultiDays.instanceByJson(parser.parseDays());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
