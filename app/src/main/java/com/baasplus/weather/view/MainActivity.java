package com.baasplus.weather.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baasplus.weather.DataChangeListener;
import com.baasplus.weather.R;
import com.baasplus.weather.adapter.BPFragmentPagerAdapter;
import com.baasplus.weather.controler.CitysList;
import com.baasplus.weather.controler.DetailFragmentList;
import com.baasplus.weather.define.DefineMessage;
import com.baasplus.weather.model.City;
import com.baasplus.weather.model.Weather;
import com.baasplus.weather.view.SlidingDrawerFragment.NavigationDrawerCallbacks;

public class MainActivity extends FragmentActivity implements NavigationDrawerCallbacks,DetailFragment.OnFragmentInteractionListener {

    public static Handler mHandler;

    private SlidingDrawerFragment mNavigationDrawerFragment;


    private TextView titleMenuTV;
    private TextView titileDetailTV;
    private TextView titleAddTV;
    private ViewPager viewPager;
    private BPFragmentPagerAdapter adapter;
    private DetailFragmentList detailFragments;
    private DataChangeListener dataChangeListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CitysList.getInstance(this);
        setContentView(R.layout.activity_main);
        dataChangeListener = new DataChangeListener() {
            @Override
            public void onChange(City city) {

            }
        };
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
//                        dataChangeListener.onChange(c);
                        updateViewpager(c);

                        DetailFragment detailFragment = detailFragments.getItem(c);
                        if (detailFragment != null){
                            detailFragment.updateData();
                        }

                        break;

                    default:
                        break;
                }

                super.handleMessage(msg);
            }
        };

        intiView();

    }

    private void intiView() {
        titleAddTV = (TextView) findViewById(R.id.title_add);
        titileDetailTV = (TextView) findViewById(R.id.title_detail);
        titleMenuTV = (TextView) findViewById(R.id.title_menu);


        mNavigationDrawerFragment = (SlidingDrawerFragment) getSupportFragmentManager().findFragmentById(
                R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        viewPager = (ViewPager)findViewById(R.id.viewpager);

        detailFragments = DetailFragmentList.getInstance();
        if (detailFragments.size() == 0) {
            for (City city : CitysList.mCitysList) {
                Log.e("log", "---");
                detailFragments.add(DetailFragment.newInstance(city));
            }
        }

        Log.e("detailFragments.size: ", detailFragments.size() + "");


        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        adapter = new BPFragmentPagerAdapter(manager,detailFragments);

        viewPager.setAdapter(adapter);

    }

    /**
     * 更新ListView
     */
    public void updateListView() {

    }

    private void updateViewpager(City city){
        if (detailFragments == null) {
            detailFragments = DetailFragmentList.getInstance();
        }
        detailFragments.add(DetailFragment.newInstance(city));
        adapter.notifyDataSetChanged();
    }

    // public static void updateUI() {
    // if (mHandler != null) {
    // Message msg = mHandler.obtainMessage();
    // msg.what = DefineMessage.MSG_UPDATEUI;
    // mHandler.sendMessage(msg);
    // }
    // }

    public static void updateViewByCity(City c) {
        if (mHandler != null) {
            Message msg = mHandler.obtainMessage();
            msg.what = DefineMessage.MSG_UPDATEUI_BY_CITY;
            msg.obj = c;
            mHandler.sendMessage(msg);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (CitysList.mCitysList.size() <= position) {
            return;
        }
//            startActivity(new Intent(MainActivity.this, EditCitysActivity.class));
            City c = CitysList.mCitysList.get(position);

    }

    /**
     * 读取该city中的信息，然后显示到textView
     *
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
//        tvDetail.setText("");
//        tvDetail.append("城市： " + weather.getCity() + "\n");
//        tvDetail.append("天气状况： " + weather.getWeatherCondition() + "\n");
//        tvDetail.append("最低气温： " + weather.getLow() + "\n");
//        tvDetail.append("最高气温： " + weather.getHight() + "\n");
//        tvDetail.append("日期： " + weather.getDate() + "\n");
//        tvDetail.append("星期： " + weather.getDayOfWeek() + "\n");
//        tvDetail.append("更新时间： " + weather.getUpdateTime() + "\n");
    }

    /**
     * 添加按钮点击事件
     * @param v
     */
    public void titleAddClick(View v) {

    }

    /**
     * 菜单按钮点击事件
     * @param v
     */
    public void titleMenuClick(View v) {
        mNavigationDrawerFragment.open();
    }

    /**
     * 抽屉盒中 编辑城市 按钮点击事件
     * @param v
     */
    public void editCitysClick(View v){
        startActivity(new Intent(MainActivity.this, EditCitysActivity.class));
    }

    /**
     * 抽屉盒中 设置 按钮点击事件
     * @param v
     */
    public void settingClick(View v){


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
