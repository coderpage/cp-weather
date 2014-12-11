package com.example.hzqweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hzqweather.define.DefineSQL;
import com.example.hzqweather.define.DefineSQL.MyDbTableCareCitys;

public class DBHelper extends SQLiteOpenHelper {

	private static DBHelper mDbHelper;
	private SQLiteDatabase mDB;
	private Context mContext;

	public static synchronized DBHelper getInstance(Context context) {
		if (mDbHelper != null) {
			return mDbHelper;
		} else {
			return new DBHelper(context, "my.db", null, 1);
		}
	}

	private DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		mContext = context;
		mDB = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(DefineSQL.MyDbTableCareCitys.CREATE_TABLE_CARE_CITYS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public Cursor queryAll(String tableName) {
		if (tableName.equals(MyDbTableCareCitys.TABLE_NAME)) {
			String selection = MyDbTableCareCitys.COLUMN_DELETED + "!=?";
			String[] selectionArgs = new String[] { "1" };
			return mDB.query(tableName, null, selection, selectionArgs, null, null, null);
		}
		return mDB.query(tableName, null, null, null, null, null, null);
	}

	public void insertCareCitys(String cityName, String cityCode, long lasteUpdateTime) {
		ContentValues values = new ContentValues();
		values.put(MyDbTableCareCitys.COLUMN_CITY_NAME, cityName);
		values.put(MyDbTableCareCitys.COLUMN_CITY_CODE, cityCode);
		values.put(MyDbTableCareCitys.COLUMN_LAST_UPDATE_TIME, lasteUpdateTime);
		mDB.insert(MyDbTableCareCitys.TABLE_NAME, null, values);
	}

	public void updateLastUpdateTime(String time, String cityCode) {
		String whereClause = MyDbTableCareCitys.COLUMN_CITY_CODE + " = ?";
		String[] whereArgs = new String[] { cityCode };
		ContentValues values = new ContentValues();
		values.put(MyDbTableCareCitys.COLUMN_LAST_UPDATE_TIME, time);
		mDB.update(MyDbTableCareCitys.TABLE_NAME, values, whereClause, whereArgs);
	}

	public void updatePrimary(Boolean b, String cityCode) {
		String whereClause = MyDbTableCareCitys.COLUMN_CITY_CODE + " = ?";
		String[] whereArgs = new String[] { cityCode };
		ContentValues values = new ContentValues();
		values.put(MyDbTableCareCitys.COLUMN_MAIN, b);
		mDB.update(MyDbTableCareCitys.TABLE_NAME, values, whereClause, whereArgs);
	}

	public void updateLocation(Boolean b, String cityCode) {
		String whereClause = MyDbTableCareCitys.COLUMN_CITY_CODE + " = ?";
		String[] whereArgs = new String[] { cityCode };
		ContentValues values = new ContentValues();
		values.put(MyDbTableCareCitys.COLUMN_LOCATION, b);
		mDB.update(MyDbTableCareCitys.TABLE_NAME, values, whereClause, whereArgs);
	}

	public void deleteCareCity(String cityCode) {
		String whereClause = MyDbTableCareCitys.COLUMN_CITY_CODE + " = ?";
		String[] whereArgs = new String[] { cityCode };
		ContentValues values = new ContentValues();
		values.put(MyDbTableCareCitys.COLUMN_DELETED, 1);
		mDB.update(MyDbTableCareCitys.TABLE_NAME, values, whereClause, whereArgs);
	}

}
