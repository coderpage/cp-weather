package com.baasplus.weather.define;

/**
 * 定义数据库
 */
public class DefineSQL {

	public static class CityCodeDB {
		public static final String TABLE_NAME = "citycode";
		public static final String COLUMN_PROVINCE = "province";
		public static final String COLUMN_CITY = "city";
		public static final String COLUMN_COUNTY = "county";
		public static final String COLUMN_CODE = "code";
	}

	public static class MyDbTableCareCitys {

		public static final String TABLE_NAME = "care_citys";
		public static final String COLUMN_ID = "id";
		public static final String COLUMN_CITY_NAME = "city_name";
		public static final String COLUMN_CITY_CODE = "city_code";
		public static final String COLUMN_LAST_UPDATE_TIME = "last_update_time";
		public static final String COLUMN_MAIN = "main";
		public static final String COLUMN_LOCATION = "location";
		public static final String COLUMN_DELETED = "deleted";

		public static final String CREATE_TABLE_CARE_CITYS = "CREATE TABLE " +TABLE_NAME + "(" + COLUMN_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CITY_NAME + " TEXT," + COLUMN_CITY_CODE + " TEXT,"
				+ COLUMN_LAST_UPDATE_TIME + " TEXT," + COLUMN_MAIN + " INTEGER DEFAULT 0, " + COLUMN_LOCATION + " INTEGER DEFAULT 0, "
				+ COLUMN_DELETED + " INTEGER DEFAULT 0);";

	}

}
