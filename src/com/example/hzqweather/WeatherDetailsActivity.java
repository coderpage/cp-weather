package com.example.hzqweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hzqweather.db.DBHelper;
import com.example.hzqweather.model.City;
import com.example.hzqweather.model.Weather;

public class WeatherDetailsActivity extends Activity {

	private TextView tvDetail;
	private City city;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_details);
		tvDetail = (TextView) findViewById(R.id.tv_detail);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		city = (City) bundle.get("city");
		showDetails(city);
	}

	public void showDetails(City city) {
		if (city == null) {
			return;
		}
		Weather weather = city.weather;
		if (weather == null) {
			return;
		}
		tvDetail.setText("");
		tvDetail.append("城市： " + weather.getCity() + "\n");
		tvDetail.append("天气状况： " + weather.getWeatherCondition() + "\n");
		tvDetail.append("最低气温： " + weather.getLow() + "\n");
		tvDetail.append("最高气温： " + weather.getHight() + "\n");
		tvDetail.append("日期： " + weather.getDate() + "\n");
		tvDetail.append("星期： " + weather.getDayOfWeek() + "\n");
		tvDetail.append("更新时间： " + weather.getUpdateTime() + "\n");
	}

	public void deleteCity(View v){
		DBHelper dbHelper = DBHelper.getInstance(WeatherDetailsActivity.this);
		dbHelper.deleteCareCity(city.code);
	}
}
