package com.baasplus.weather.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.baasplus.weather.define.DefineSQL;
import com.baasplus.weather.tool.Utility;

public class CitycodeDBHelper {

	private static CitycodeDBHelper mDbHelper = null;
	private SQLiteDatabase mDatabase;
	private Context mContext;

	public static synchronized CitycodeDBHelper getInstance(Context context) {
		if (mDbHelper == null) {
			return new CitycodeDBHelper(context);
		} else {
			return mDbHelper;
		}
	}

	private CitycodeDBHelper(Context context) {
		mContext = context;
		if (mDatabase != null) {
			this.closeDB();
		}
		mDatabase = openDB();
	}

	/**
	 * 打开数据库
	 * 如果在databases的路径下找不到citycode.db数据库文件，需要从assets文件夹下导入，这个操作只会在APP首次打开时执行
	 * @return 数据库对象
	 */
	private SQLiteDatabase openDB() {
		String PACKAGE_NAME = "com.baasplus.weather";
		String dbPath = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME
				+ "/databases/citycode.db";
		if (!Utility.citycodeExist()) {
			Utility.importDB(mContext);
		}
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath, null);

		return db;
	}

	/**
	 * 关闭数据库
	 */
	public void closeDB() {
		mDatabase.close();
	}

	/**
	 * 查询数据库中所有信息
	 * @return Cursor
	 */
	public Cursor queryAll() {
		return mDatabase.query(DefineSQL.CityCodeDB.TABLE_NAME, null, null, null, null, null, null);
	}

	/**
	 * 通过城市名称查询
	 * @param cityName 城市名称
	 * @return cursor
	 */
	public Cursor queryByCityname(String cityName) {
		String selection = DefineSQL.CityCodeDB.COLUMN_CITY + " =? or " + DefineSQL.CityCodeDB.COLUMN_COUNTY
				+ " =? or " + DefineSQL.CityCodeDB.COLUMN_PROVINCE + " =?";
		String[] selectionArgs = new String[] { cityName, cityName, cityName };
		return mDatabase.query(DefineSQL.CityCodeDB.TABLE_NAME, null, selection, selectionArgs, null, null, null);
	}

	/**
	 * 通过城市代码查询
	 * @param cityCode 城市代码
	 * @return Cursor
	 */
	public Cursor queryByCitycode(String cityCode) {
		String selection = DefineSQL.CityCodeDB.COLUMN_CODE + " =?";
		String[] selectionArgs = new String[] { cityCode };
		return mDatabase.query(DefineSQL.CityCodeDB.TABLE_NAME, null, selection, selectionArgs, null, null, null);
	}
}
