package com.coderpage.weather.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

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

    protected int icon1; // 白天天气图标资源ID
    protected int icon2; // 晚上天气图标资源ID

    protected Weather() {
        initDayOfWeek();
    }

    protected void initCalendar() {
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
    }

    protected void initDayOfWeek() {
        initCalendar();
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                dayOfWeek = Week.SUNDAY;
                break;
            case Calendar.MONDAY:
                dayOfWeek = Week.MONDAY;
                break;
            case Calendar.TUESDAY:
                dayOfWeek = Week.TUESDAY;
                break;
            case Calendar.WEDNESDAY:
                dayOfWeek = Week.WEDNESDAY;
                break;
            case Calendar.THURSDAY:
                dayOfWeek = Week.THURSDAY;
                break;
            case Calendar.FRIDAY:
                dayOfWeek = Week.FRIDAY;
                break;
            case Calendar.SATURDAY:
                dayOfWeek = Week.SATURDAY;
                break;
        }
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

    public int getIcon1() {
        return icon1;
    }

    public int getIcon2() {
        return icon2;
    }
}
