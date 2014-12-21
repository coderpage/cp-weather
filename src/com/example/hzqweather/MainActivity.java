package com.example.hzqweather;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SlidingDrawer;

import com.example.hzqweather.adapter.MainViewAdaper;
import com.example.hzqweather.controler.CitysList;
import com.example.hzqweather.controler.WeatherHeiper;
import com.example.hzqweather.define.DefineMessage;
import com.example.hzqweather.tool.Utility;

public class MainActivity extends ActionBarActivity implements OnItemClickListener {

	public static Handler mHandler;
	private ListView lvCareCitys;
	private MainViewAdaper adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case DefineMessage.MSG_UPDATEUI:
					updateListView();
					break;

				default:
					break;
				}

				super.handleMessage(msg);
			}
		};

		CitysList.getInstance(this);
		
		lvCareCitys = (ListView) findViewById(R.id.lv_care_citys);
		adapter = new MainViewAdaper(this, CitysList.mCitysList);
		lvCareCitys.setAdapter(adapter);
		lvCareCitys.setOnItemClickListener(this);
		CitysList.initCityWeather();
	}
	
	

	@Override
	protected void onPostResume() {
		super.onPostResume();
		lvCareCitys = (ListView) findViewById(R.id.lv_care_citys);
		adapter = new MainViewAdaper(this, CitysList.mCitysList);
		lvCareCitys.setAdapter(adapter);
		lvCareCitys.setOnItemClickListener(this);
	}


	/**
	 * 更新ListView
	 */
	public  void updateListView() {
		adapter.notifyDataSetChanged();
	}

//	public static void updateUI() {
//		if (mHandler != null) {
//			Message msg = mHandler.obtainMessage();
//			msg.what = DefineMessage.MSG_UPDATEUI;
//			mHandler.sendMessage(msg);
//		}
//	}

	public void addCity(View v) {
		startActivity(new Intent(MainActivity.this, SearchCityActivity.class));
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(MainActivity.this, WeatherDetailsActivity.class);
		intent.putExtra("city", CitysList.mCitysList.get(position));
		startActivity(intent);
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
