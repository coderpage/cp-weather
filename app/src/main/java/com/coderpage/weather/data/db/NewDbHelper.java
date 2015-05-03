package com.coderpage.weather.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abner-l on 15/5/3.
 */
public class NewDbHelper extends SQLiteOpenHelper {

    private static NewDbHelper mDbHelper;
    private SQLiteDatabase mDB;
    private Context mContext;

    public static synchronized NewDbHelper getInstance(Context context, String name) {
        if (mDbHelper != null) {
            return mDbHelper;
        }
        return new NewDbHelper(context, name, null, 1);
    }

    private NewDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
        mDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table city(_id integer primary key autoincrement, province text, city text, county text, code text, all_py text, all_first_py text, first_py text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String province, String city, String county, String code, String all_py, String all_first_py, String first_py){
        ContentValues values = new ContentValues();
        values.put("province",province);
        values.put("city",city);
        values.put("county",county);
        values.put("code",code);
        values.put("all_py",all_py);
        values.put("all_first_py",all_first_py);
        values.put("first_py",first_py);

        mDB.insert("city",null,values);
    }

}
