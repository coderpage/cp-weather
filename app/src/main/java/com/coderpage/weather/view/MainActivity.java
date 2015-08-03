package com.coderpage.weather.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.coderpage.weather.BPApplication;
import com.coderpage.weather.R;
import com.coderpage.weather.adapter.CityPagerAdapter;
import com.coderpage.weather.data.AllCity;
import com.coderpage.weather.data.AllCityPage;
import com.coderpage.weather.define.DefineMessage;
import com.coderpage.weather.model.City;
import com.coderpage.weather.view.SlidingDrawerFragment.NavigationDrawerCallbacks;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements NavigationDrawerCallbacks, CityPage.OnFragmentInteractionListener, ViewPager.OnPageChangeListener {

    public static Handler mHandler;

    private SlidingDrawerFragment mNavigationDrawerFragment;

    private TextView titleMenuTV;
    private TextView titileDetailTV;
    private ImageView titileLocationIV;
    private TextView titleAddTV;
    private ViewPager viewPager;
    private LinearLayout tabsContainer;
    private CityPagerAdapter adapter;
    private AllCityPage cityPages;
    private int dotCurIndex;
    private ArrayList<ImageView> tabDots = new ArrayList<>();

    private LocationClientOption.LocationMode tempMode = LocationClientOption.LocationMode.Hight_Accuracy;
    private LocationClient locationClient;
    private String tempcoor = "gcj02";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AllCity.getInstance(this);
        setContentView(R.layout.activity_main);
        AllCity.initCityWeather();

        locationClient = ((BPApplication) getApplication()).locationClient;
        initLocation();
        locationClient.start();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case DefineMessage.MSG_UPDATEUI:
                        break;
                    case DefineMessage.MSG_UPDATE_WEATHER:
                        City c = (City) msg.obj;
                        CityPage detailFragment = cityPages.getItem(c);
                        if (detailFragment != null) {
                            detailFragment.updateData();
                        }
                        Log.e("log", "msg update weather");
                        break;

                    case DefineMessage.MSG_ADD_NEW_CITY:
                        City city = (City) msg.obj;
                        updateViewpager(city, DefineMessage.MSG_ADD_NEW_CITY);
                        break;
                    case DefineMessage.MSG_DEL_CITY:
                        updateViewpager(null, DefineMessage.MSG_DEL_CITY);
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
        titileDetailTV = (TextView) findViewById(R.id.title_city_name);
        titileLocationIV = (ImageView) findViewById(R.id.titile_iv_loaction);
        titleMenuTV = (TextView) findViewById(R.id.title_menu);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        cityPages = AllCityPage.getInstance();
        if (cityPages.isEmpty()) {
            for (City city : AllCity.cities) {
                cityPages.add(CityPage.newInstance(city));
            }
        }
        initTabDots();

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        adapter = new CityPagerAdapter(manager, cityPages);

        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);

        mNavigationDrawerFragment = (SlidingDrawerFragment) getSupportFragmentManager().findFragmentById(
                R.id.navigation_drawer);

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        if (!AllCity.cities.isEmpty()) {
            City city = AllCity.cities.get(0);
            titileDetailTV.setText(city.displayName);
            if (city.isLocation()){
                titileLocationIV.setVisibility(View.VISIBLE);
            }else {
                titileLocationIV.setVisibility(View.GONE);
            }
        }
    }

    private void initTabDots() {
        tabsContainer = (LinearLayout) findViewById(R.id.tabs);
        for (int i = 0; i < cityPages.size(); i++) {
            ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.tab_dot_image_view, null);
            imageView.setEnabled(false);
            tabsContainer.addView(imageView);
            tabDots.add(imageView);
        }
        dotCurIndex = 0;
        if (!tabDots.isEmpty()) {
            tabDots.get(dotCurIndex).setEnabled(true);
        }
    }

    private void addTabDot() {
        if (tabsContainer == null) {
            tabsContainer = (LinearLayout) findViewById(R.id.tabs);
        }
        ImageView imageView = (ImageView) LayoutInflater.from(this).inflate(R.layout.tab_dot_image_view, null);
        imageView.setEnabled(true);
        tabsContainer.addView(imageView);
        tabDots.add(imageView);
    }

    private void removeTabDot() {
        if (tabsContainer == null) {
            tabsContainer = (LinearLayout) findViewById(R.id.tabs);
        }
        tabsContainer.removeViewAt(0);
        tabDots.remove(0);
    }


    /**
     * 更新 ViewPager; 注意：只有当添加新城市的时候，该方法调用且有效
     *
     * @param city
     */
    private void updateViewpager(City city, int type) {
        if (cityPages == null) {
            cityPages = AllCityPage.getInstance();
        }

        if (type == DefineMessage.MSG_ADD_NEW_CITY) {
            if (!cityPages.isExist(city)) {
                addTabDot();
                cityPages.add(CityPage.newInstance(city));
                adapter.notifyDataSetChanged();
                if (viewPager != null) {
                    int index = cityPages.size() - 1;
                    viewPager.setCurrentItem(index);
                    if (index == 0) {
                        titileDetailTV.setText(cityPages.get(0).getCity().displayName);
                        if (cityPages.get(0).getCity().isLocation()){
                            titileLocationIV.setVisibility(View.VISIBLE);
                        }else {
                            titileLocationIV.setVisibility(View.GONE);
                        }
                    }
                }
            }
        }

        if (type == DefineMessage.MSG_DEL_CITY) {
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            removeTabDot();
            setTabDotsLastTrue();
            if (viewPager != null) {
                viewPager.setCurrentItem(cityPages.size() - 1);
            }
        }
    }

    private void setTabDotsLastTrue() {
        for (ImageView imageView : tabDots) {
            imageView.setEnabled(false);
        }
        tabDots.get(tabDots.size() - 1).setEnabled(true);
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
        if (AllCity.cities.size() <= position) {
            return;
        }
        if (viewPager != null) {
            viewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        setCurDot(i);
        titileDetailTV.setText(cityPages.get(i).getCity().displayName);
        if (cityPages.get(i).getCity().isLocation()){
            titileLocationIV.setVisibility(View.VISIBLE);
        }else {
            titileLocationIV.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void setCurDot(int position) {
        if (position < 0 || position > tabDots.size() - 1
                || dotCurIndex == position) {
            return;
        }

        tabDots.get(position).setEnabled(true);
        tabDots.get(dotCurIndex).setEnabled(false);

        dotCurIndex = position;
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

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(tempMode);//设置定位模式
        option.setCoorType(tempcoor);//返回的定位结果是百度经纬度，默认值gcj02
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
    }
}
