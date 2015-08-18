package com.coderpage.weather.test;

import android.test.AndroidTestCase;
import android.util.Log;

import com.coderpage.weather.model.Week;
import com.coderpage.weather.tool.TimeUtils;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by abner-l on 15/8/14.
 */
public class TimeUtilsTest extends AndroidTestCase {
    private final String TAG = "TimeUtilsTest";

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetLaterDate() throws Exception {
        Calendar calendar = TimeUtils.getLaterDate(0);
        Week week = TimeUtils.getDayOfWeek(calendar);
        Log.e(TAG, calendar.getTime().toString());
        Log.e(TAG, week.value());

        calendar = TimeUtils.getLaterDate(1);
        week = TimeUtils.getDayOfWeek(calendar);
        Log.e(TAG, calendar.getTime().toString());
        Log.e(TAG, week.value());
    }

    public void testGetTimePast() throws Exception {

//        Date date = new Date("2015-08-14 17:44");
        String datePast = "2015-08-14 17:44";

        String[] split = datePast.split(" ");


        String[] splitYMD = split[0].split("-");


        String[] splitHM = split[1].split(":");


        int year = Integer.parseInt(splitYMD[0]);
        int month = Integer.parseInt(splitYMD[1]);
        int day1 = Integer.parseInt(splitYMD[2]);
        int hour1 = Integer.parseInt(splitHM[0]);
        int min1 = Integer.parseInt(splitHM[1]);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        calendar.set(year, month-1, day1, hour1, min1);
        Log.e(TAG, calendar.getTime().toString());
    }
}
