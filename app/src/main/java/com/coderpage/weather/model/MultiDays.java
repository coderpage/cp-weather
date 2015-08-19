package com.coderpage.weather.model;

import android.util.SparseArray;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.Serializable;

/**
 * Created by abner-l on 15/4/26.
 */
public class MultiDays implements Serializable {
    private final SparseArray<DaysWeather> allDaysWeather = new MySparseArray();


    public static MultiDays instanceByJson(JSONArray json) {
        MultiDays instance = new MultiDays();

        if (json == null) {
            return instance;
        }

        update(instance, json);

        return instance;
    }

    public static void update(MultiDays instance, JSONArray json) {
        DaysWeather daysWeather;

        try {
            for (int i = 0; i < json.length(); i++) {
                daysWeather = DaysWeather.instanceByJson(json.getJSONObject(i), i);
                instance.addWeather(i, daysWeather);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public SparseArray<DaysWeather> getAllDaysWeather() {
        return allDaysWeather;
    }

    void addWeather(int index, DaysWeather weather) {
        allDaysWeather.put(index, weather);
    }

    public DaysWeather getDay(int day) {
        return allDaysWeather.get(day);
    }

    static class MySparseArray extends SparseArray implements Serializable{}
}
