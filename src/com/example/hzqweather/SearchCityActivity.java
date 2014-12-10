package com.example.hzqweather;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.hzqweather.controler.WeatherManager;
import com.example.hzqweather.db.CitycodeDBHelper;
import com.example.hzqweather.define.DefineSQL;
import com.example.hzqweather.model.City;

public class SearchCityActivity extends Activity implements OnItemClickListener {

	private EditText etCity;
	private ListView lvCitys;
	private List<City> citys;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_city);
		etCity = (EditText) findViewById(R.id.et_city);
		lvCitys = (ListView) findViewById(R.id.lv_citys);
	}

	public void query(View v) {
		String cityName = etCity.getText().toString().trim();
		if (cityName == null) {
			Toast.makeText(SearchCityActivity.this, "请输入城市名称", Toast.LENGTH_SHORT).show();
			return;
		}
		citys = searchCitys(cityName);
		if (citys.size() == 0) {
			Toast.makeText(SearchCityActivity.this, "未找到相关城市", Toast.LENGTH_SHORT).show();
			return;
		}
		ListAdapter adapter = new com.example.hzqweather.adapter.BaseAdapter(SearchCityActivity.this, citys);
		lvCitys.setVisibility(ListView.VISIBLE);
		lvCitys.setAdapter(adapter);
		lvCitys.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		City city = citys.get(position);
		 WeatherManager wm = WeatherManager.getInstance();
		 wm.queryWeather(city.code);
		 lvCitys.setVisibility(ListView.GONE);
		 startActivity(new Intent(SearchCityActivity.this, MainActivity.class));
	}

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
			System.out.println(c.toString());
			citys.add(c);
		}
		db.closeDB();

		return citys;
	}

}
