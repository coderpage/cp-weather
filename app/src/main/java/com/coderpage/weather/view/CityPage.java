package com.coderpage.weather.view;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coderpage.weather.R;
import com.coderpage.weather.model.City;
import com.coderpage.weather.model.DaysWeather;
import com.coderpage.weather.model.MultiDays;
import com.coderpage.weather.model.TodayWeather;
import com.coderpage.weather.tool.ImageUtils;
import com.coderpage.weather.tool.TimeUtils;
import com.coderpage.weather.view.activity.MainActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;


public class CityPage extends Fragment {

    private City city = new City();
    private TextView pm25TV;
    private TextView airQualityTV;
    private TextView updateTimeTV;
    private TextView currentTmpTV;
    private TextView conditionTV;
    private TextView tmpTV;
    private TextView windSpeedTV;

    private MultiDayItemView[] multiDayItemViews = new MultiDayItemView[7];

    private OnFragmentInteractionListener mListener;
    private PullToRefreshScrollView mPullRefreshScrollView;
    private ScrollView mScrollView;
    private TrendView trendView;

    int mX = 0;

    public static CityPage newInstance(City city) {
        CityPage fragment = new CityPage();
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        fragment.setArguments(args);
        return fragment;
    }

    public CityPage() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            city = (City) getArguments().getSerializable("city");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout contentView = (LinearLayout) inflater.inflate(R.layout.fragment_detail, container, false);

        mPullRefreshScrollView = (PullToRefreshScrollView) contentView.findViewById(R.id.pull_refresh_scrollview);
        mPullRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                UpdateDataTask updateDataTask = new UpdateDataTask();
                updateDataTask.execute();
            }

        });

        mScrollView = mPullRefreshScrollView.getRefreshableView();

        initView(contentView);
        if (city != null) {

            TodayWeather weather = city.weather;
            if (weather != null && weather.getAirQuality() != null) {
                updateData();
            }
        }
        return contentView;
    }

    private void initView(LinearLayout contentView) {
        pm25TV = (TextView) contentView.findViewById(R.id.weather_pm25_tv);
        airQualityTV = (TextView) contentView.findViewById(R.id.weather_quality_type);
        updateTimeTV = (TextView) contentView.findViewById(R.id.weather_update_time_tv);
        currentTmpTV = (TextView) contentView.findViewById(R.id.weather_current_tmp_tv);
        conditionTV = (TextView) contentView.findViewById(R.id.weather_now_cond_tv);
        tmpTV = (TextView) contentView.findViewById(R.id.weather_today_tmp);
        windSpeedTV = (TextView) contentView.findViewById(R.id.weather_now_wind_speed);

        trendView = (TrendView) contentView.findViewById(R.id.trend_view);

        multiDayItemViews[0] = (MultiDayItemView) contentView.findViewById(R.id.multi_days_0);
        multiDayItemViews[1] = (MultiDayItemView) contentView.findViewById(R.id.multi_days_1);
        multiDayItemViews[2] = (MultiDayItemView) contentView.findViewById(R.id.multi_days_2);
        multiDayItemViews[3] = (MultiDayItemView) contentView.findViewById(R.id.multi_days_3);
        multiDayItemViews[4] = (MultiDayItemView) contentView.findViewById(R.id.multi_days_4);
        multiDayItemViews[5] = (MultiDayItemView) contentView.findViewById(R.id.multi_days_5);
        multiDayItemViews[6] = (MultiDayItemView) contentView.findViewById(R.id.multi_days_6);

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    public void updateData() {
        if (city != null && city.city == this.city.city) {
            if (city != null) {

                TodayWeather weather = city.weather;
                if (weather != null) {

                    pm25TV.setText("pm2.5：" + weather.getAirQuality().getPm25());
                    airQualityTV.setText("空气质量：" + weather.getAirQuality().getQualityType());
                    updateTimeTV.setText(weather.getUpdateTime() + "更新");
                    currentTmpTV.setText(weather.getCurrentTmp() + "℃");
                    conditionTV.setText(weather.getDayCondition());
                    tmpTV.setText(weather.getMinTmp() + "~" + weather.getMaxTmp());
                    windSpeedTV.setText(weather.getWind().getDirection() + weather.getWind().getScale() + "级");

                    int height = dip2px(getActivity().getApplicationContext(), 500f);
                    trendView.setWidthHeight(MainActivity.windowWidth, height);

                    SparseArray<DaysWeather> days = weather.getMultiDays().getAllDaysWeather();
                    List<Integer> maxTems = new ArrayList<>();
                    List<Integer> minTems = new ArrayList<>();
                    for (int i = 0; i < 7; i++) {
                        maxTems.add(Integer.parseInt(days.get(i).getMaxTmp()));
                        minTems.add(Integer.parseInt(days.get(i).getMinTmp()));
                    }
                    trendView.setTemperature(maxTems, minTems);

                    MultiDays multiDays = city.getWeather().getMultiDays();
                    for (int i = 0; i < multiDayItemViews.length; i++) {
                        DaysWeather daysWeather = multiDays.getDay(i);
                        MultiDayItemView view = multiDayItemViews[i];
                        view.setWeekTV(daysWeather.getDayOfWeek().value());
                        view.setDayConditionTV(daysWeather.getDayCondition());
                        view.setNightConditionTV(daysWeather.getNightCondition());
                        view.setDateTV(TimeUtils.getDate(daysWeather.getCalendar()));
                        view.setDayIcon(ImageUtils.getSmallIcon(getActivity().getApplicationContext(), daysWeather.getIconDay()));
                        view.setNightIcon(ImageUtils.getSmallIcon(getActivity().getApplicationContext(),daysWeather.getIconNight()));
                    }

                }
            }
        }
    }

    public City getCity() {
        return this.city;
    }

    private class UpdateDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            city.updateWeather();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mPullRefreshScrollView.onRefreshComplete();
            updateData();
            super.onPostExecute(result);
        }
    }


    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
