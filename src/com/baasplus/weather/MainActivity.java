package com.baasplus.weather;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.baasplus.weather.NavigationDrawerFragment.NavigationDrawerCallbacks;
import com.baasplus.weather.controler.CitysList;
import com.baasplus.weather.define.DefineMessage;
import com.baasplus.weather.model.City;
import com.baasplus.weather.model.Weather;

public class MainActivity extends ActionBarActivity implements NavigationDrawerCallbacks {

	public static Handler mHandler;

	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	private TextView tvDetail;

	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CitysList.getInstance(this);
		setContentView(R.layout.activity_main);
		CitysList.initCityWeather();
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case DefineMessage.MSG_UPDATEUI:
					updateListView();
					break;
				case DefineMessage.MSG_UPDATEUI_BY_CITY:
					City c = (City) msg.obj;
					showDetails(c);
					break;

				default:
					break;
				}

				super.handleMessage(msg);
			}
		};

		tvDetail = (TextView) findViewById(R.id.tv_detail);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(
				R.id.navigation_drawer);
		mTitle = getTitle();

		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
		
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
	}

	/**
	 * 更新ListView
	 */
	public void updateListView() {
		
	}

	// public static void updateUI() {
	// if (mHandler != null) {
	// Message msg = mHandler.obtainMessage();
	// msg.what = DefineMessage.MSG_UPDATEUI;
	// mHandler.sendMessage(msg);
	// }
	// }
	
	public static void updateViewByCity(City c){
		if (mHandler != null) {
			Message msg = mHandler.obtainMessage();
			msg.what = DefineMessage.MSG_UPDATEUI_BY_CITY;
			msg.obj = c;
			mHandler.sendMessage(msg);
		}
	}

	public void onSectionAttached(int number) {
		if (number == 0) {
			mTitle = "编辑地点";
		} else {
			mTitle = CitysList.mCitysList.get(number).displayName;
		}
	}

	@SuppressWarnings("deprecation")
	public void restoreActionBar() {
		actionBar = getSupportActionBar();
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		if (CitysList.mCitysList.size() <= position) {
			return;
		}
		if (position == 0) {
			startActivity(new Intent(MainActivity.this, EditCitysActivity.class));
		} else {
			City c = CitysList.mCitysList.get(position);
			mTitle = c.displayName;
			showDetails(c);
		}

	}

	/**
	 * 读取该city中的信息，然后显示到textView
	 * @param city city对象实例
	 */
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
		actionBar.setTitle(city.displayName);
	}

}
