package com.coderpage.weather.view;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.coderpage.weather.R;
import com.coderpage.weather.model.City;
import com.coderpage.weather.model.TodayWeather;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;


public class CityPage extends Fragment {

    private City city = new City();
    private TextView pm25TV;
    private TextView airQuailtTV;
    private TextView updateTimeTV;
    private TextView currentTmpTV;
    private TextView conditionTV;
    private TextView tmpTV;
    private TextView windSpeedTV;

    private OnFragmentInteractionListener mListener;
    PullToRefreshScrollView mPullRefreshScrollView;
    ScrollView mScrollView;

    LineView lineView;
    int mX = 0;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case 0:
                    lineView.setLinePoint(msg.arg1,msg.arg2);
                    break;
                default:
                    break;

            }

            super.handleMessage(msg);
        }
    };

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

        new Thread(){
            public void run() {
                for (int index=0; index<20; index++)
                {
                    Message message = new Message();
                    message.what = 0;
                    message.arg1 = mX;
                    message.arg2 = (int)(Math.random()*200);;
                    handler.sendMessage(message);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    mX += 30;
                }
            };
        }.start();
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
                pm25TV.setText("pm2.5：" + weather.getAirQuality().getPm25());
                airQuailtTV.setText("空气质量：" + weather.getAirQuality().getQualityType());
                updateTimeTV.setText(weather.getUpdateTime() + "更新");
                currentTmpTV.setText(weather.getCurrentTmp() + "℃");
                conditionTV.setText(weather.getDayCondition());
                tmpTV.setText(weather.getMinTmp() + "~" + weather.getMaxTmp());
                windSpeedTV.setText(weather.getWind().getDirection() + weather.getWind().getScale() + "级");
            }
        }
        return contentView;
    }

    private void initView(LinearLayout contentView){
        pm25TV = (TextView) contentView.findViewById(R.id.weather_pm25_tv);
        airQuailtTV = (TextView) contentView.findViewById(R.id.weather_quality_type);
        updateTimeTV = (TextView) contentView.findViewById(R.id.weather_update_time_tv);
        currentTmpTV = (TextView) contentView.findViewById(R.id.weather_current_tmp_tv);
        conditionTV = (TextView) contentView.findViewById(R.id.weather_now_cond_tv);
        tmpTV = (TextView) contentView.findViewById(R.id.weather_today_tmp);
        windSpeedTV = (TextView) contentView.findViewById(R.id.weather_now_wind_speed);

        lineView = (LineView)contentView.findViewById(R.id.line);
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
        Log.e("log:", "updateData");
        if (city != null && city.city == this.city.city) {
            if (city != null) {

                TodayWeather weather = city.weather;
                if (weather != null) {

                    pm25TV.setText("pm2.5：" + weather.getAirQuality().getPm25());
                    airQuailtTV.setText("空气质量：" + weather.getAirQuality().getQualityType());
                    updateTimeTV.setText(weather.getUpdateTime() + "更新");
                    currentTmpTV.setText(weather.getCurrentTmp() + "℃");
                    conditionTV.setText(weather.getDayCondition());
                    tmpTV.setText(weather.getMinTmp() + "~" + weather.getMaxTmp());
                    windSpeedTV.setText(weather.getWind().getDirection() + weather.getWind().getScale() + "级");
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

}
