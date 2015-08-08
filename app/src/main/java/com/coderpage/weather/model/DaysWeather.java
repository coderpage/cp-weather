package com.coderpage.weather.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abner-l on 15/8/4.
 */
public class DaysWeather extends Weather {

    private DaysWeather(){
        super();
    }

    public static DaysWeather instanceByJson(JSONObject json){
        DaysWeather instance = new DaysWeather();

        if (json == null){
            return instance;
        }

        update(instance,json);

        return instance;
    }

    public static void update(DaysWeather instance, JSONObject json){
        try {

            instance.minTmp = json.getJSONObject("tmp").getString("min");
            instance.maxTmp = json.getJSONObject("tmp").getString("max");
            instance.dayCondition = json.getJSONObject("cond").getString("txt_d");
            instance.nightCondition = json.getJSONObject("cond").getString("txt_n");
            instance.wind = Wind.instanceByJson(json.getJSONObject("wind"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
