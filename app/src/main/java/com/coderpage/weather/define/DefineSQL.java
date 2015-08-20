package com.coderpage.weather.define;

/**
 * 定义数据库
 */
public class DefineSQL {

    /**
     * 国内所有城市列表，包括省/市/县/城市代码/城市拼音/城市名所有首拼/城市名首拼
     */
    public static class CityCodeDB {
        public static final String TABLE_NAME = "city";
        public static final String COLUMN_PROVINCE = "province";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_COUNTY = "county";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_ALL_PY = "all_py";
        public static final String COLUMN_ALL_FIRST_PY = "all_first_py";
        public static final String COLUMN_FIRST_PY = "first_py";
    }

    /**
     * 关心城市列表
     */
    public static class MyDbTableCareCities {

        public static final String TABLE_NAME = "care_cities";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CITY_NAME = "city_name";
        public static final String COLUMN_CITY_CODE = "city_code";
        public static final String COLUMN_LAST_UPDATE_TIME = "last_update_time";
        public static final String COLUMN_MAIN = "main";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_DELETED = "deleted";

        public static final String CREATE_TABLE_CARE_CITIES = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CITY_NAME + " TEXT," + COLUMN_CITY_CODE + " TEXT," + COLUMN_LAST_UPDATE_TIME + " TEXT," + COLUMN_MAIN + " INTEGER DEFAULT 0, " + COLUMN_LOCATION + " INTEGER DEFAULT 0, " + COLUMN_DELETED + " INTEGER DEFAULT 0);";

    }

    /**
     * 关心城市天气缓存表
     */
    public static class MyDbTableWeatherCache {
        public static final String TABLE_NAME = "weather_cache";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_CITY_ID = "city_id";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_DATA_1 = "data1";
        public static final String COLUMN_DATA_2 = "data2";
        public static final String COLUMN_DATA_3 = "data3";
        public static final String COLUMN_DATA_4 = "data4";
        public static final String COLUMN_DATA_5 = "data5";
        public static final String COLUMN_DATA_6 = "data6";
        public static final String COLUMN_DATA_7 = "data7";
        public static final String COLUMN_DATA_8 = "data8";
        public static final String COLUMN_DATA_9 = "data9";
        public static final String COLUMN_DATA_10 = "data10";
        public static final String COLUMN_DATA_11 = "data11";
        public static final String COLUMN_DATA_12 = "data12";
        public static final String COLUMN_DATA_13 = "data13";
        public static final String COLUMN_DATA_14 = "data14";
        public static final String COLUMN_DATA_15 = "data15";

        public static final String CREATE_TABLE_WEATHER_CACHE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TYPE + " INTEGER DEFAULT -1," + COLUMN_CITY_ID + " INTEGER DEFAULT -1,"+ COLUMN_DATA_1 + " TEXT," + COLUMN_DATA_2 + " TEXT,"
                + COLUMN_DATA_3 + " TEXT," + COLUMN_DATA_4 + " TEXT," + COLUMN_DATA_5 + " TEXT," + COLUMN_DATA_6 + " TEXT," + COLUMN_DATA_7 + " TEXT,"
                + COLUMN_DATA_8 + " TEXT," + COLUMN_DATA_9 + " TEXT," + COLUMN_DATA_10 + " TEXT," + COLUMN_DATA_11 + " TEXT," + COLUMN_DATA_12 + " TEXT,"
                + COLUMN_DATA_13 + " TEXT," + COLUMN_DATA_14 + " TEXT," + COLUMN_DATA_15 + " TEXT);";
    }

}
