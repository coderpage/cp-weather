package com.example.hzqweather.db;

import com.example.hzqweather.tool.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

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
		if (!utility.citycodeExist()) {
			utility.importDB(mContext);
		}
		mDB = getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE citycode (id  INTEGER PRIMARY KEY AUTOINCREMENT, province TEXT, city TEXT, county TEXT, code TEXT)";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void insert(String province, String city, String county, String code) {
		ContentValues values = new ContentValues();
		values.put("province", province);
		values.put("city", city);
		values.put("county", county);
		values.put("code", code);
		mDB.insert("citycode", null, values);
	}

}
