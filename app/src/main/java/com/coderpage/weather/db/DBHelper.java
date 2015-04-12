package com.coderpage.weather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.coderpage.weather.define.DefineSQL;
import com.coderpage.weather.define.DefineSQL.MyDbTableCareCitys;

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

	/**
	 * 查询表中所有信息
	 * @param tableName 表名
	 * @return Cursor
	 */
	public Cursor queryAll(String tableName) {
		if (tableName.equals(MyDbTableCareCitys.TABLE_NAME)) {
			String selection = MyDbTableCareCitys.COLUMN_DELETED + "!=?";
			String[] selectionArgs = new String[] { "1" };
			return mDB.query(tableName, null, selection, selectionArgs, null, null, null);
		}
		return mDB.query(tableName, null, null, null, null, null, null);
	}

	/**
	 * 通过id查询
	 * @param tableName 表名
	 * @param id 主键id值
	 * @return Cursor
	 */
	public Cursor queryByRowId(String tableName, long id) {
		if (tableName.equals(MyDbTableCareCitys.TABLE_NAME)) {
			String selection = MyDbTableCareCitys.COLUMN_DELETED + "!=? and " + MyDbTableCareCitys.COLUMN_ID + " =? ";
			String[] selectionArgs = new String[] { "1", String.valueOf(id) };
			return mDB.query(tableName, null, selection, selectionArgs, null, null, null);
		}
		String selection = MyDbTableCareCitys.COLUMN_ID + " =? ";
		String[] selectionArgs = new String[] { String.valueOf(id) };
		return mDB.query(tableName, null, selection, selectionArgs, null, null, null);
	}

	/**
	 * 添加城市
	 * @param cityName 城市名称
	 * @param cityCode 城市代码
	 * @param lasteUpdateTime 最后更新时间
	 * @return 插入记录对应的id
	 */
	public long insertCareCitys(String cityName, String cityCode, long lasteUpdateTime,boolean location) {
		ContentValues values = new ContentValues();
		values.put(MyDbTableCareCitys.COLUMN_CITY_NAME, cityName);
		values.put(MyDbTableCareCitys.COLUMN_CITY_CODE, cityCode);
		values.put(MyDbTableCareCitys.COLUMN_LAST_UPDATE_TIME, lasteUpdateTime);
        values.put(MyDbTableCareCitys.COLUMN_LOCATION,location);
		long id = mDB.insert(MyDbTableCareCitys.TABLE_NAME, null, values);
		return id;
	}

	/**
	 * 更新城市最后更新天气的时间
	 * @param time 最后更新的时间
	 * @param cityCode 需要更新的城市的代码
	 * @return 是否更新成功
	 */
	public boolean updateLastUpdateTime(long time, String cityCode) {
		String whereClause = MyDbTableCareCitys.COLUMN_CITY_CODE + " = ?";
		String[] whereArgs = new String[] { cityCode };
		ContentValues values = new ContentValues();
		values.put(MyDbTableCareCitys.COLUMN_LAST_UPDATE_TIME, time);
		mDB.update(MyDbTableCareCitys.TABLE_NAME, values, whereClause, whereArgs);
		return true;
	}

	/**
	 * 更新主要城市（主要城市是用户上次关闭时显示的城市，我们需要保存用户的设置信息，然后还原，现在还没实现这个功能）
	 * @param b 是否是主要城市
	 * @param cityCode 城市代码
	 * @return 是否更新成功
	 */
	public boolean updatePrimary(Boolean b, String cityCode) {
		String whereClause = MyDbTableCareCitys.COLUMN_CITY_CODE + " = ?";
		String[] whereArgs = new String[] { cityCode };
		ContentValues values = new ContentValues();
		values.put(MyDbTableCareCitys.COLUMN_MAIN, b);
		mDB.update(MyDbTableCareCitys.TABLE_NAME, values, whereClause, whereArgs);
		return true;
	}

	/**
	 * 更新是否是当前所在城市，（现在还没有实现GPS的定位功能）
	 * @param b 是否是当前城市
	 * @param cityCode 城市代码
	 * @return 是否更新成功
	 */
	public boolean updateLocation(Boolean b, String cityCode) {
		String whereClause = MyDbTableCareCitys.COLUMN_CITY_CODE + " = ?";
		String[] whereArgs = new String[] { cityCode };
		ContentValues values = new ContentValues();
		values.put(MyDbTableCareCitys.COLUMN_LOCATION, b);
		mDB.update(MyDbTableCareCitys.TABLE_NAME, values, whereClause, whereArgs);
		return true;
	}

	/**
	 * 删除城市
	 * @param cityCode 城市代码 
	 * @return 是否删除成功
	 */
	public boolean deleteCareCity(String cityCode) {
		String whereClause = MyDbTableCareCitys.COLUMN_CITY_CODE + " = ?";
		String[] whereArgs = new String[] { cityCode };
		ContentValues values = new ContentValues();
		values.put(MyDbTableCareCitys.COLUMN_DELETED, 1);
		mDB.update(MyDbTableCareCitys.TABLE_NAME, values, whereClause, whereArgs);
		return true;
	}

}
