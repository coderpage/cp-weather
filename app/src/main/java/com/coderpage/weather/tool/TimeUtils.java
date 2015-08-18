package com.coderpage.weather.tool;

import android.text.TextUtils;

import com.coderpage.weather.model.Week;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 操作时间的工具类
 *
 * @author abner-l
 * @since 2015-8-14
 */
public class TimeUtils {

    /**
     * 获取当前时间
     *
     * @return 当前时间
     */
    public static long currentTimeLong() {

        return System.currentTimeMillis();
    }

    /**
     * 获取当前相对之前日期的差值
     *
     * @param datePast 之前的日期
     * @return 当前相对之前日期的差值
     */
    public static long timePast(Date datePast) {

        return currentTimeLong() - datePast.getTime();
    }

    /**
     * 获取当前相对之前日期的差值
     *
     * @param datePast 之前的日期
     * @return 当前相对之前日期的差值
     */
    public static String timePast(String datePast) {
//        2015-08-14 17:44
        if (TextUtils.isEmpty(datePast)) {
            return "--";
        }

        String[] split = datePast.split(" ");
        if (split.length != 2) {
            return "--";
        }

        String[] splitYMD = split[0].split("-");
        if (splitYMD.length != 3) {
            return "--";
        }

        String[] splitHM = split[1].split(":");
        if (splitHM.length != 2) {
            return "--";
        }

        int year = Integer.parseInt(splitYMD[0]);
        int month = Integer.parseInt(splitYMD[1]);
        int day1 = Integer.parseInt(splitYMD[2]);
        int hour1 = Integer.parseInt(splitHM[0]);
        int min1 = Integer.parseInt(splitHM[1]);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        calendar.set(year, month - 1, day1, hour1, min1);

        long oneMinMS = 60000;
        long oneHourMS = 3600000;
        long oneDayMS = 86400000;
        long timeDiff = currentTimeLong() - calendar.getTimeInMillis();

        long day = timeDiff / oneDayMS;
        if (day > 0) {
            return day + "天前";
        }

        long hour = timeDiff / oneHourMS;
        if (hour > 0) {
            return hour + "小时前";
        }
        long min = timeDiff / oneMinMS;

        return min + "分钟前";
    }

    /**
     * 获取未来日期
     *
     * @param dayAfter 未来的第几天
     * @return 未来日期
     */
    public static Calendar getLaterDate(int dayAfter) {
        long oneDayMS = 86400000;
        long dayAfterMS = currentTimeLong() + (dayAfter * oneDayMS);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
        calendar.setTimeInMillis(dayAfterMS);

        return calendar;
    }

    /**
     * 获取日期 String 形式，只包含月份和天，以 '/' 分隔
     *
     * @param calendar 日期 {@link Calendar}
     * @return 日期，只包含月份和天，以 '/' 分隔
     */
    public static String getDate(Calendar calendar) {
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        return sdf.format(date);
    }


    /**
     * 获取星期
     *
     * @param calendar 日期
     * @return 星期 {@link Week} 形式
     */
    public static Week getDayOfWeek(Calendar calendar) {

        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return Week.SUNDAY;

            case Calendar.MONDAY:
                return Week.MONDAY;

            case Calendar.TUESDAY:
                return Week.TUESDAY;

            case Calendar.WEDNESDAY:
                return Week.WEDNESDAY;

            case Calendar.THURSDAY:
                return Week.THURSDAY;

            case Calendar.FRIDAY:
                return Week.FRIDAY;

            case Calendar.SATURDAY:
                return Week.SATURDAY;

            default:
                return Week.SUNDAY;
        }
    }

}
