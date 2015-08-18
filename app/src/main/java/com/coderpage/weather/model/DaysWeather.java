package com.coderpage.weather.model;

import com.coderpage.weather.define.Icons;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abner-l on 15/8/4.
 */
public class DaysWeather extends Weather {

    private static boolean debug = true;
    private String TAG = "DaysWeather";

    private DaysWeather(int day) {
        super(day);
    }

    public static DaysWeather instanceByJson(JSONObject json, int day) {
        DaysWeather instance = new DaysWeather(day);

        if (json == null) {
            return instance;
        }

        update(instance, json);

        return instance;
    }

    public static void update(DaysWeather instance, JSONObject json) {
        try {

            instance.minTmp = json.getJSONObject("tmp").getString("min");
            instance.maxTmp = json.getJSONObject("tmp").getString("max");
            instance.dayCondition = json.getJSONObject("cond").getString("txt_d");
            instance.nightCondition = json.getJSONObject("cond").getString("txt_n");
            instance.wind = Wind.instanceByJson(json.getJSONObject("wind"));

            instance.iconDay = Icons.getDayIcon(json.getJSONObject("cond").getInt("code_d"));
            instance.iconNight = Icons.getNightIcon(json.getJSONObject("cond").getInt("code_n"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
