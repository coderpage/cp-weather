package com.coderpage.weather.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.coderpage.weather.R;
import com.coderpage.weather.adapter.SearchViewAdapter;
import com.coderpage.weather.controler.CitysList;
import com.coderpage.weather.controler.WeatherHelper;
import com.coderpage.weather.db.CitycodeDBHelper;
import com.coderpage.weather.db.DBHelper;
import com.coderpage.weather.define.DefineSQL;
import com.coderpage.weather.model.City;

import java.util.ArrayList;
import java.util.List;

public class SearchCityActivity extends ActionBarActivity implements OnItemClickListener {

    private EditText etCity;
    private ListView lvCitys;
    private List<City> citys = new ArrayList<>();
    private SearchViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_city);
        initView();
    }

    private void initView() {
        etCity = (EditText) findViewById(R.id.et_city);
        lvCitys = (ListView) findViewById(R.id.lv_citys);
        setTextChangeListener();
    }

    private void setTextChangeListener() {
        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String cityName = etCity.getText().toString().trim();
                if (cityName.equals("")) {
                    citys.clear();
                    lvCitys.setVisibility(ListView.VISIBLE);
                    adapter = new SearchViewAdapter(SearchCityActivity.this, citys);
                    lvCitys.setAdapter(adapter);
                    lvCitys.setOnItemClickListener(SearchCityActivity.this);
                    return;
                }
                citys = fuzzySearchCities(cityName);
                if (citys.size() == 0) {
                    Toast.makeText(SearchCityActivity.this, "未找到相关城市", Toast.LENGTH_SHORT).show();
                    return;
                }
                lvCitys.setVisibility(ListView.VISIBLE);
                adapter = new SearchViewAdapter(SearchCityActivity.this, citys);
                lvCitys.setAdapter(adapter);
                lvCitys.setOnItemClickListener(SearchCityActivity.this);
            }
        });
    }


    /**
     * 查询按钮点击事件
     *
     * @param v
     */
    public void query(View v) {
        String cityName = etCity.getText().toString().trim();
        if (cityName == null || cityName.equals("")) {
            Toast.makeText(SearchCityActivity.this, "请输入城市名称", Toast.LENGTH_SHORT).show();
            return;
        }
        citys = fuzzySearchCities(cityName);
        if (citys.size() == 0) {
            Toast.makeText(SearchCityActivity.this, "未找到相关城市", Toast.LENGTH_SHORT).show();
            return;
        }

        ListAdapter adapter = new com.coderpage.weather.adapter.SearchViewAdapter(SearchCityActivity.this, citys);
        lvCitys.setVisibility(ListView.VISIBLE);
        lvCitys.setAdapter(adapter);
        lvCitys.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        City city = citys.get(position);
        if (CitysList.mCitysList.exist(city)) {
            Toast.makeText(SearchCityActivity.this, "不能重复添加城市", Toast.LENGTH_SHORT).show();
            return;
        }
        WeatherHelper wm = new WeatherHelper();
        wm.queryWeather(city.code);
        lvCitys.setVisibility(ListView.GONE);
        DBHelper dbHelper = DBHelper.getInstance(SearchCityActivity.this);
        long rowID = dbHelper.insertCareCitys(city.displayName, city.code, System.currentTimeMillis(),false);
        if (rowID == -1) {
            Toast.makeText(SearchCityActivity.this, "城市添加失败，请重新添加", Toast.LENGTH_LONG).show();
            return;
        }
        CitysList.mCitysList.addCity(rowID);
        startActivity(new Intent(SearchCityActivity.this, MainActivity.class));
    }

    /**
     * 通过输入的城市名称从数据库中查询对应城市信息
     *
     * @param cityName 城市名称
     * @return 包含所查询到的城市的list集合
     */
    private List<City> searchCitys(String cityName) {
        List<City> citys = new ArrayList<City>();
        CitycodeDBHelper db = CitycodeDBHelper.getInstance(this);
        Cursor cursor = db.queryByCityname(cityName);
        while (cursor.moveToNext()) {
            String province = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_PROVINCE));
            String city = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_CITY));
            String county = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_COUNTY));
            String code = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_CODE));
            City c = new City(province, city, county, code);
//			System.out.println(c.toString());
            citys.add(c);
        }
        db.closeDB();

        return citys;
    }

    /**
     * 模糊查询
     *
     * @param cityName
     * @return
     */
    private List<City> fuzzySearchCities(String cityName) {
        List<City> citys = new ArrayList<City>();
        CitycodeDBHelper db = CitycodeDBHelper.getInstance(this);
        Cursor cursor = db.fuzzySearch(cityName);
        while (cursor.moveToNext()) {
            String province = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_PROVINCE));
            String city = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_CITY));
            String county = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_COUNTY));
            String code = cursor.getString(cursor.getColumnIndex(DefineSQL.CityCodeDB.COLUMN_CODE));
            City c = new City(province, city, county, code);
//            System.out.println(c.toString());
            citys.add(c);
        }
        db.closeDB();

        return citys;
    }

}
