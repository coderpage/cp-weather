package com.example.hzqweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hzqweather.controler.CitysList;
import com.example.hzqweather.controler.WeatherHeiper;
import com.example.hzqweather.db.DBHelper;
import com.example.hzqweather.define.DefineMessage;
import com.example.hzqweather.model.City;
import com.example.hzqweather.model.Weather;

public class WeatherDetailsActivity extends Activity {

	private TextView tvDetail;
	private City city;
	public Handler mHandler = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_details);
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case DefineMessage.MSG_UPDATEUI:
					updateUI();
					break;

				default:
					break;
				}
			}
		};
		tvDetail = (TextView) findViewById(R.id.tv_detail);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		
		city = (City) bundle.get("city");
		if (city.lastUpdateTime < System.currentTimeMillis()) {
			 WeatherHeiper wm = new WeatherHeiper();
			 wm.queryWeather(city.code);
		}
		
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
		boolean deleted = dbHelper.deleteCareCity(city.code);
		if (deleted) {
			CitysList.mCitysList.deleteCity(city.code);
			Toast.makeText(WeatherDetailsActivity.this, "删除成功", Toast.LENGTH_LONG).show();
		}
	}
	
	private void updateUI(){
		for (City c : CitysList.mCitysList) {
			if (c.code.equals(city.code)) {
				showDetails(c);
			}
		}
	}
}
