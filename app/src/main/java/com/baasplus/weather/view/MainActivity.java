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
import com.baasplus.weather.controler.CitysList;
import com.baasplus.weather.controler.DetailFragmentList;
import com.baasplus.weather.define.DefineMessage;
import com.baasplus.weather.model.City;
import com.baasplus.weather.view.SlidingDrawerFragment.NavigationDrawerCallbacks;

public class MainActivity extends FragmentActivity implements NavigationDrawerCallbacks, DetailFragment.OnFragmentInteractionListener {

    public static Handler mHandler;

    private SlidingDrawerFragment mNavigationDrawerFragment;


    private TextView titleMenuTV;
    private TextView titileDetailTV;
    private TextView titleAddTV;
    private ViewPager viewPager;
    private BPFragmentPagerAdapter adapter;
    private DetailFragmentList detailFragments;


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
                        break;
                    case DefineMessage.MSG_UPDATE_WEATHER:
                        City c = (City) msg.obj;
                        DetailFragment detailFragment = detailFragments.getItem(c);
                        if (detailFragment != null) {
                            detailFragment.updateData();
                        }
                        break;

                    case DefineMessage.MSG_ADD_NEW_CITY:
                        City city = (City) msg.obj;
                        updateViewpager(city);
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
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        detailFragments = DetailFragmentList.getInstance();
        if (detailFragments.isEmpty()) {
            for (City city : CitysList.mCitysList) {
                detailFragments.add(DetailFragment.newInstance(city));
            }
        }

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        adapter = new BPFragmentPagerAdapter(manager, detailFragments);

        viewPager.setAdapter(adapter);

        mNavigationDrawerFragment = (SlidingDrawerFragment) getSupportFragmentManager().findFragmentById(
                R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));


    }


    /**
     * 更新 ViewPager; 注意：只有当添加新城市的时候，该方法调用且有效
     *
     * @param city
     */
    private void updateViewpager(City city) {
        if (detailFragments == null) {
            detailFragments = DetailFragmentList.getInstance();
        }

        if (!detailFragments.isExist(city)) {
            detailFragments.add(DetailFragment.newInstance(city));
            adapter.notifyDataSetChanged();
            if (viewPager != null){
                viewPager.setCurrentItem(detailFragments.size()-1);
            }
        }

    }

    /**
     * 通知天气被更新
     *
     * @param c 更新天气的城市
     */
    public static void updateWeather(City c) {
        if (mHandler != null) {
            Message msg = mHandler.obtainMessage();
            msg.what = DefineMessage.MSG_UPDATE_WEATHER;
            msg.obj = c;
            mHandler.sendMessage(msg);
        }
    }

    /**
     * 通知添加了新的城市
     *
     * @param city 新添加的城市
     */
    public static void addNewCity(City city) {
        if (mHandler != null) {
            Message msg = mHandler.obtainMessage();
            msg.what = DefineMessage.MSG_ADD_NEW_CITY;
            msg.obj = city;
            mHandler.sendMessage(msg);
        }
    }

    /**
     * 抽屉盒菜单 item 的点击回调
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (CitysList.mCitysList.size() <= position) {
            return;
        }
        if (viewPager != null) {
            viewPager.setCurrentItem(position);
        }
    }


    /**
     * 添加按钮点击事件
     *
     * @param v
     */
    public void titleAddClick(View v) {
        startActivity(new Intent(MainActivity.this, SearchCityActivity.class));
    }

    /**
     * 菜单按钮点击事件
     *
     * @param v
     */
    public void titleMenuClick(View v) {
        mNavigationDrawerFragment.open();
    }

    /**
     * 抽屉盒中 编辑城市 按钮点击事件
     *
     * @param v
     */
    public void editCitysClick(View v) {
        startActivity(new Intent(MainActivity.this, EditCitysActivity.class));
    }

    /**
     * 抽屉盒中 设置 按钮点击事件
     *
     * @param v
     */
    public void settingClick(View v) {


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
