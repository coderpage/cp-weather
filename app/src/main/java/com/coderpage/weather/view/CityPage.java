package com.coderpage.weather.view;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coderpage.weather.R;
import com.coderpage.weather.data.Cities;
import com.coderpage.weather.data.WeatherHelper;
import com.coderpage.weather.model.City;
import com.coderpage.weather.model.Weather;
import com.coderpage.weather.model.Week;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


public class CityPage extends Fragment {

    private City city = new City();
    private TextView detailTV;
    private TextView tomorrowTV;
    private TextView dayAfterTomorrowTV;
    private OnFragmentInteractionListener mListener;
    PullToRefreshScrollView mPullRefreshScrollView;
    ScrollView mScrollView;

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
                new GetDataTask().execute();
            }
        });

        mScrollView = mPullRefreshScrollView.getRefreshableView();

        detailTV = (TextView) contentView.findViewById(R.id.fragment_detail_tv);
        tomorrowTV = (TextView) contentView.findViewById(R.id.tomorrow_weather_tv);
        dayAfterTomorrowTV = (TextView) contentView.findViewById(R.id.day_after_tomorrow_weather_tv);

        if (city != null) {

            Weather weather = city.weather;
            if (weather != null) {

                detailTV.setText("");
                detailTV.append("城市： " + city.getDisplayName() + "\n");
                detailTV.append("天气状况： " + weather.getWeatherCondition() + "\n");
                detailTV.append("最低气温： " + weather.getLow() + "\n");
                detailTV.append("最高气温： " + weather.getHight() + "\n");
                detailTV.append("日期： " + weather.getDate() + "\n");
                detailTV.append("星期： " + weather.getDayOfWeek() + "\n");
                detailTV.append("更新时间： " + weather.getUpdateTime() + "\n");
                detailTV.append("城市代码： " + city.getCode() + "\n");
                detailTV.append("定位： " + city.isLocation() + "\n");
                Week week = weather.getWeek();
                tomorrowTV.append("明天：" + week.getWeather1().getLow() + "~" + week.getWeather1().getHight() + "\n");
                tomorrowTV.append(week.getWeather1().getWeatherCondition() + "\n");
                dayAfterTomorrowTV.append("后天：" + week.getWeather2().getLow() + "~" + week.getWeather2().getHight() + "\n");
                dayAfterTomorrowTV.append(week.getWeather2().getWeatherCondition() + "\n");
//                detailTV.append("day1：" + "\n");
//                detailTV.append("天气状况： " + week.getWeather1().getWeatherCondition() + "\n");
//                detailTV.append("最低气温： " + week.getWeather1().getLow() + "\n");
//                detailTV.append("最高气温： " + week.getWeather1().getHight() + "\n");
//                detailTV.append("day2：" + "\n");
//                detailTV.append("天气状况： " + week.getWeather2().getWeatherCondition() + "\n");
//                detailTV.append("最低气温： " + week.getWeather2().getLow() + "\n");
//                detailTV.append("最高气温： " + week.getWeather2().getHight() + "\n");
//                detailTV.append("day3：" + "\n");
//                detailTV.append("天气状况： " + week.getWeather3().getWeatherCondition() + "\n");
//                detailTV.append("最低气温： " + week.getWeather3().getLow() + "\n");
//                detailTV.append("最高气温： " + week.getWeather3().getHight() + "\n");
//                detailTV.append("day4：" + "\n");
//                detailTV.append("天气状况： " + week.getWeather4().getWeatherCondition() + "\n");
//                detailTV.append("最低气温： " + week.getWeather4().getLow() + "\n");
//                detailTV.append("最高气温： " + week.getWeather4().getHight() + "\n");
//                detailTV.append("day5：" + "\n");
//                detailTV.append("天气状况： " + week.getWeather5().getWeatherCondition() + "\n");
//                detailTV.append("最低气温： " + week.getWeather5().getLow() + "\n");
//                detailTV.append("最高气温： " + week.getWeather5().getHight() + "\n");
            }
        }
        return contentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void updateData() {
        Log.e("log:", "updateData");
        if (city != null && city.city == this.city.city) {
            if (city != null) {

                Weather weather = city.weather;
                if (weather != null) {

                    detailTV.setText("");
                    detailTV.append("城市： " + city.getDisplayName()+ "\n");
                    detailTV.append("天气状况： " + weather.getWeatherCondition() + "\n");
                    detailTV.append("最低气温： " + weather.getLow() + "\n");
                    detailTV.append("最高气温： " + weather.getHight() + "\n");
                    detailTV.append("日期： " + weather.getDate() + "\n");
                    detailTV.append("星期： " + weather.getDayOfWeek() + "\n");
                    detailTV.append("更新时间： " + weather.getUpdateTime() + "\n");
                    detailTV.append("城市代码： " + city.getCode() + "\n");
                    detailTV.append("定位： " + city.isLocation() + "\n");
                    Week week = weather.getWeek();
                    tomorrowTV.setText("");
                    dayAfterTomorrowTV.setText("");
                    tomorrowTV.append("明天：" + week.getWeather1().getLow() + "~" + week.getWeather1().getHight() + "\n");
                    tomorrowTV.append(week.getWeather1().getWeatherCondition() + "\n");
                    dayAfterTomorrowTV.append("后天：" + week.getWeather2().getLow() + "~" + week.getWeather2().getHight() + "\n");
                    dayAfterTomorrowTV.append(week.getWeather2().getWeatherCondition() + "\n");
                }
            }
        }
    }

    public City getCity() {
        return this.city;
    }

    private class GetDataTask extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(Void... params) {
            JSONObject weatherJson = new JSONObject();
            String url = "http://weather.51wnl.com/weatherinfo/GetMoreWeather?cityCode=" + getCity().code
                    + "&weatherType=0";
            String weatherResponse = "";
            HttpGet httpRequest = new HttpGet(url);
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpResponse httpResponse = httpClient.execute(httpRequest);
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    String response = EntityUtils.toString(httpResponse.getEntity());
                    weatherJson = new JSONObject(response);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("error", "出错。。。");
            }

            return weatherJson;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            Weather weather = new Weather();
            WeatherHelper helper = new WeatherHelper();
            weather = helper.recoverWeather(result);
            Cities.mCities.updateWeather(getCity().code, weather);
            mPullRefreshScrollView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }
}
