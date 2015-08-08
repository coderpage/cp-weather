package com.coderpage.weather.tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by abner-l on 15/8/4.
 */
public class Parser {
    private JSONObject all = null;

    public Parser(String json) {
        parseAll(json);
    }

    private void parseAll(String json) {
        try {
            JSONObject object = new JSONObject(json);
            all = object.getJSONArray("HeWeather data service 3.0").getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addTadayCondition(String txt_d) {
        try {
            JSONArray daily = parseDays();
            JSONObject today = daily.getJSONObject(0);
            JSONObject cond = today.getJSONObject("cond");
            cond.put("txt_d", txt_d);
            today.put("cond", cond);
            daily.put(0, today);
            all.put("daily_forecast", daily);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject parseAirQuality() {
        JSONObject aqi = null;

        if (all == null) {
            return aqi;
        }

        try {
            aqi = all.getJSONObject("aqi").getJSONObject("city");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return aqi;
    }

    public JSONObject parseNow() {

        JSONObject now = null;

        if (all == null) {
            return now;
        }

        try {
            now = all.getJSONObject("now");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return now;
    }

    public JSONObject parseToday() {
        JSONObject today = null;


        if (all == null) {
            return today;
        }

        try {
            today = parseDays().getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return today;
    }

    public JSONArray parseDays() {
        JSONArray days = null;

        if (all == null) {
            return days;
        }

        try {
            days = all.getJSONArray("daily_forecast");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return days;
    }

    public JSONObject parseBasic() {
        JSONObject basic = null;

        if (all == null) {
            return basic;
        }

        try {
            basic = all.getJSONObject("basic");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return basic;
    }
}
