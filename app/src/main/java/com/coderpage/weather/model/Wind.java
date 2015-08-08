package com.coderpage.weather.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by abner-l on 15/8/5.
 */
public class Wind implements Serializable {
    protected int deg; // 风向（角度）
    protected String direction = ""; //风向(方向)
    protected String scale = ""; //风力等级
    protected int speed; // 风速(Km/h)

    public static Wind instanceByJson(JSONObject json) {
        Wind instance = new Wind();

        if (json == null) {
            return instance;
        }

        update(instance, json);

        return instance;
    }

    public static void update(Wind wind, JSONObject json) {
        try {

            wind.deg = json.getInt("deg");
            wind.direction = json.getString("dir");
            wind.scale = json.getString("sc");
            wind.speed = json.getInt("spd");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getDeg() {
        return deg;
    }

    public String getDirection() {
        return direction;
    }

    public String getScale() {
        return scale;
    }

    public int getSpeed() {
        return speed;
    }
}
