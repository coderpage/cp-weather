package com.example.hzqweather.db;

import com.example.hzqweather.define.DefineSQL;
import com.example.hzqweather.tool.Utility;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

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

	private SQLiteDatabase openDB() {
		String PACKAGE_NAME = "com.example.hzqweather";
		String dbPath = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME
				+ "/databases/citycode.db";
		if (!Utility.citycodeExist()) {
			Utility.importDB(mContext);
		}
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath, null);

		return db;
	}

	public void closeDB() {
		mDatabase.close();
	}

	public Cursor queryAll() {
		return mDatabase.query(DefineSQL.CityCodeDB.TABLE_NAME, null, null, null, null, null, null);
	}

	public Cursor queryByCityname(String cityName){
		String selection = DefineSQL.CityCodeDB.COLUMN_CITY + " =? or " + DefineSQL.CityCodeDB.COLUMN_COUNTY + " =? or " + DefineSQL.CityCodeDB.COLUMN_PROVINCE + " =?";
		String[] selectionArgs = new String[]{cityName, cityName, cityName};
		return mDatabase.query(DefineSQL.CityCodeDB.TABLE_NAME, null, selection, selectionArgs, null, null, null);
	}
	
	public Cursor queryByCitycode(String cityCode){
		String selection = DefineSQL.CityCodeDB.COLUMN_CODE + " =?";
		String[] selectionArgs = new String[]{cityCode};
		return mDatabase.query(DefineSQL.CityCodeDB.TABLE_NAME, null, selection, selectionArgs, null, null, null);
	}
}
