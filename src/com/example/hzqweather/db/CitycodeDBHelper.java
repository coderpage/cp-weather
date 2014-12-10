package com.example.hzqweather.db;

import com.example.hzqweather.tool.utility;

import android.content.Context;
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
		if (!utility.citycodeExist()) {
			utility.importDB(mContext);
		}
		SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath, null);

		return db;
	}

	public void closeDB() {
		mDatabase.close();
	}

}
