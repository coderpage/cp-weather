package com.example.hzqweather;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hzqweather.controler.WeatherManager;
import com.example.hzqweather.db.CitycodeDBHelper;
import com.example.hzqweather.define.DefineMessage;
import com.example.hzqweather.define.DefineSQL;
import com.example.hzqweather.model.Weather;
import com.example.hzqweather.tool.Utility;

public class MainActivity extends ActionBarActivity {

	private TextView tvDetail;
	public static Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		System.out.println(Utility.citycodeExist());
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case DefineMessage.MSG_UPDATEUI:
					setUI();
					break;

				default:
					break;
				}

				super.handleMessage(msg);
			}
		};

		tvDetail = (TextView) findViewById(R.id.tv_detail);
		setUI();

	}

	private void setUI() {
		Weather weather = WeatherManager.weather;
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

	public static void updateUI() {
		if (mHandler != null) {
			Message msg = mHandler.obtainMessage();
			msg.what = DefineMessage.MSG_UPDATEUI;
			mHandler.sendMessage(msg);
		}
	}

	public void query(View v) {
		startActivity(new Intent(MainActivity.this, SearchCityActivity.class));
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
