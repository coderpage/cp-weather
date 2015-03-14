package com.baasplus.weather.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.TextView;

import com.baasplus.weather.R;
import com.baasplus.weather.adapter.BPFragmentPagerAdapter;
import com.baasplus.weather.controler.CityPageList;
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

    private TextView tvDetail;


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

        intiView();

    }

    private void intiView() {
        titleAddTV = (TextView) findViewById(R.id.title_add);
        titileDetailTV = (TextView) findViewById(R.id.title_detail);
        titleMenuTV = (TextView) findViewById(R.id.title_menu);


        mNavigationDrawerFragment = (SlidingDrawerFragment) getSupportFragmentManager().findFragmentById(
                R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));


        ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);

        //citys
        City city1 = new City();
        city1.city = "suzhou";
        Weather weather1 = new Weather();
        weather1.setCityID("1");
        weather1.setCity("suzhou");
        city1.weather = weather1;

        City city2 = new City();
        city2.city = "xuzhou";
        Weather weather2 = new Weather();
        weather2.setCityID("1");
        weather2.setCity("xuzhou");
        city2.weather = weather2;

//        CityPage cityPage1 = CityPage.newInstace(this,city1);
//        CityPage cityPage2 = CityPage.newInstace(this,city2);

        CityPage cityPage1 = new CityPage(this);
        CityPage cityPage2 = new CityPage(this);

        cityPage1.setCity(city1);
        cityPage1.initView();

        cityPage2.setCity(city2);
        cityPage2.initView();

        CityPageList cityPages = CityPageList.getInstance();
        cityPages.add(cityPage1);
        cityPages.add(cityPage2);

//        PagerAdapter adapter = new PagerAdapter(cityPages);

        DetailFragmentList detailFragments = DetailFragmentList.getInstance();

        DetailFragment detailFragment1 = DetailFragment.newInstance(city1);
        DetailFragment detailFragment2 = DetailFragment.newInstance(city2);

        detailFragments.add(detailFragment1);
        detailFragments.add(detailFragment2);

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
//        FragmentManager manager = getFragmentManager();
        BPFragmentPagerAdapter adapter = new BPFragmentPagerAdapter(manager,detailFragments);

        viewPager.setAdapter(adapter);

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
        if (position == 0) {
            startActivity(new Intent(MainActivity.this, EditCitysActivity.class));
        } else {
            City c = CitysList.mCitysList.get(position);
            titileDetailTV.setText(c.displayName);
            showDetails(c);
        }

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
        tvDetail.setText("");
        tvDetail.append("城市： " + weather.getCity() + "\n");
        tvDetail.append("天气状况： " + weather.getWeatherCondition() + "\n");
        tvDetail.append("最低气温： " + weather.getLow() + "\n");
        tvDetail.append("最高气温： " + weather.getHight() + "\n");
        tvDetail.append("日期： " + weather.getDate() + "\n");
        tvDetail.append("星期： " + weather.getDayOfWeek() + "\n");
        tvDetail.append("更新时间： " + weather.getUpdateTime() + "\n");
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
