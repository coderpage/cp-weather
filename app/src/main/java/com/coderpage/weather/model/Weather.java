package com.coderpage.weather.model;

import com.coderpage.weather.tool.TimeUtils;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by abner-l on 15/8/4.
 */
public abstract class Weather implements Serializable {
    protected String minTmp = ""; // 最低气温
    protected String maxTmp = ""; // 最高气温
    protected Week dayOfWeek; // 星期几？
    protected Calendar calendar; // 日历
    protected String dayCondition = ""; // 白天天气状况
    protected String nightCondition = ""; // 晚上天气状况
    protected Wind wind;

    protected int iconDay; // 白天天气图标资源ID
    protected int iconNight; // 晚上天气图标资源ID

    protected Weather(int day) {
        this.calendar = TimeUtils.getLaterDate(day);
        this.dayOfWeek = TimeUtils.getDayOfWeek(calendar);
    }


    public String getMinTmp() {
        return minTmp;
    }

    public String getMaxTmp() {
        return maxTmp;
    }

    public Week getDayOfWeek() {
        return dayOfWeek;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public String getDayCondition() {
        return dayCondition;
    }

    public String getNightCondition() {
        return nightCondition;
    }

    public Wind getWind() {
        return wind;
    }

    public int getIconDay() {
        return iconDay;
    }

    public int getIconNight() {
        return iconNight;
    }
}
