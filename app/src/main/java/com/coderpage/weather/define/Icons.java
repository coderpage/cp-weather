package com.coderpage.weather.define;

import android.util.SparseIntArray;

import com.coderpage.weather.R;

/**
 * Created by abner-l on 15/8/4.
 */
public abstract class Icons {

    private static final int SUN_DAY = R.drawable.w0;             // 晴天
    private static final int CLOUDY_DAY = R.drawable.w1;          // 多云
    private static final int OVERCAST = R.drawable.w2;            // 阴天
    private static final int SHOWERY_RAIN_DAY = R.drawable.w3;    // 阵雨
    private static final int THUNDER_SHOWERS = R.drawable.w4;     // 雷阵雨
    private static final int RAY_SNOW = R.drawable.w5;            // 雷阵雪
    private static final int SLEET = R.drawable.w6;               // 雨夹雪
    private static final int LIGHT_RAIN = R.drawable.w7;          // 小雨
    private static final int MODERATE_RAIN = R.drawable.w8;       // 中雨
    private static final int HEAVY_RAIN = R.drawable.w9;          // 大雨
    private static final int RAIN_STORM = R.drawable.w10;         // 暴雨
    private static final int SHOWERY_SNOW_DAY = R.drawable.w13;   // 阵雪
    private static final int LIGHT_SNOW = R.drawable.w14;         // 小雪
    private static final int MODERATE_SNOW = R.drawable.w15;      // 中雪
    private static final int HEAVY_SNOW = R.drawable.w16;         // 大雪
    private static final int SNOW_STORM = R.drawable.w17;         // 暴雪
    private static final int FOGGY_DAY = R.drawable.w18;          // 雾天

    private static final int NOT_KNOW_0 = R.drawable.w19;         // 不知道什么玩意
    private static final int NOT_KNOW_1 = R.drawable.w20;         // 不知道什么玩意
    private static final int NOT_KNOW = R.drawable.w21;           // 空
    private static final int NOT_KNOW_3 = R.drawable.w29;         // 不知道什么玩意

    private static final int SUN_NIGHT = R.drawable.w30;          // 晴天 晚上
    private static final int CLOUDY_NIGHT = R.drawable.w31;       // 多云 晚上
    private static final int FOGGY_NIGHT = R.drawable.w32;        // 雾天 晚上
    private static final int SHOWERY_RAIN_NIGHT = R.drawable.w33; // 阵雨 晚上
    private static final int SHOWERY_SNOW_NIGHT = R.drawable.w34; // 阵雪 晚上
    private static final int DUST_NIGHT = R.drawable.w35;         // 浮尘 晚上
    private static final int DUST_DAY = R.drawable.w36;           // 浮尘 白天
    private static final int DUST_STORM = R.drawable.w45;          // 沙尘暴

    private static SparseIntArray dayIcons = new SparseIntArray();
    private static SparseIntArray nightIcons = new SparseIntArray();

    static {
        dayIcons.append(100, SUN_DAY);
        dayIcons.append(101, CLOUDY_DAY);
        dayIcons.append(102, CLOUDY_DAY);
        dayIcons.append(103, CLOUDY_DAY);
        dayIcons.append(104, OVERCAST);
        dayIcons.append(300, SHOWERY_RAIN_DAY);
        dayIcons.append(301, SHOWERY_RAIN_DAY);
        dayIcons.append(302, THUNDER_SHOWERS);
        dayIcons.append(303, THUNDER_SHOWERS);
        dayIcons.append(304, THUNDER_SHOWERS);
        dayIcons.append(305, LIGHT_RAIN);
        dayIcons.append(306, MODERATE_RAIN);
        dayIcons.append(307, HEAVY_RAIN);
        dayIcons.append(308, HEAVY_RAIN);
        dayIcons.append(309, LIGHT_RAIN);
        dayIcons.append(310, RAIN_STORM);
        dayIcons.append(311, RAIN_STORM);
        dayIcons.append(312, RAIN_STORM);
        dayIcons.append(313, RAIN_STORM);
        dayIcons.append(400, LIGHT_SNOW);
        dayIcons.append(401, MODERATE_SNOW);
        dayIcons.append(402, HEAVY_SNOW);
        dayIcons.append(403, SNOW_STORM);
        dayIcons.append(404, SLEET);
        dayIcons.append(405, SLEET);
        dayIcons.append(406, RAY_SNOW);
        dayIcons.append(407, SHOWERY_SNOW_DAY);
        dayIcons.append(500, FOGGY_DAY);
        dayIcons.append(501, FOGGY_DAY);
        dayIcons.append(502, FOGGY_DAY);
        dayIcons.append(503, DUST_DAY);
        dayIcons.append(504, DUST_DAY);
        dayIcons.append(506, DUST_DAY);
        dayIcons.append(507, DUST_STORM);
        dayIcons.append(508, DUST_STORM);

        nightIcons.append(100, SUN_NIGHT);
        nightIcons.append(101, CLOUDY_NIGHT);
        nightIcons.append(102, CLOUDY_NIGHT);
        nightIcons.append(103, CLOUDY_NIGHT);
        nightIcons.append(104, OVERCAST);
        nightIcons.append(300, SHOWERY_RAIN_NIGHT);
        nightIcons.append(301, SHOWERY_RAIN_NIGHT);
        nightIcons.append(302, THUNDER_SHOWERS);
        nightIcons.append(303, THUNDER_SHOWERS);
        nightIcons.append(304, THUNDER_SHOWERS);
        nightIcons.append(305, LIGHT_RAIN);
        nightIcons.append(306, MODERATE_RAIN);
        nightIcons.append(307, HEAVY_RAIN);
        nightIcons.append(308, HEAVY_RAIN);
        nightIcons.append(309, LIGHT_RAIN);
        nightIcons.append(310, RAIN_STORM);
        nightIcons.append(311, RAIN_STORM);
        nightIcons.append(312, RAIN_STORM);
        nightIcons.append(313, RAIN_STORM);
        nightIcons.append(400, LIGHT_SNOW);
        nightIcons.append(401, MODERATE_SNOW);
        nightIcons.append(402, HEAVY_SNOW);
        nightIcons.append(403, SNOW_STORM);
        nightIcons.append(404, SLEET);
        nightIcons.append(405, SLEET);
        nightIcons.append(406, RAY_SNOW);
        nightIcons.append(407, SHOWERY_SNOW_NIGHT);
        nightIcons.append(500, FOGGY_NIGHT);
        nightIcons.append(501, FOGGY_NIGHT);
        nightIcons.append(502, FOGGY_NIGHT);
        nightIcons.append(503, DUST_NIGHT);
        nightIcons.append(504, DUST_NIGHT);
        nightIcons.append(506, DUST_NIGHT);
        nightIcons.append(507, DUST_STORM);
        nightIcons.append(508, DUST_STORM);
    }

    public static int getDayIcon(int code) {
        return dayIcons.get(code, NOT_KNOW);
    }

    public static int getNightIcon(int code) {
        return nightIcons.get(code, NOT_KNOW);
    }

    public static int getDayIconCode(int resource_id) {
        int keyIndex =  dayIcons.indexOfValue(resource_id);
        if (keyIndex == -1){
            return -1;
        }
        return dayIcons.keyAt(keyIndex);
    }

    public static int getNightIconCode(int resource_id) {
        int keyIndex =  nightIcons.indexOfValue(resource_id);
        if (keyIndex == -1){
            return -1;
        }
        return nightIcons.keyAt(keyIndex);
    }
}
