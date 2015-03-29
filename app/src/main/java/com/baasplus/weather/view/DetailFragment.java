package com.baasplus.weather.view;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baasplus.weather.R;
import com.baasplus.weather.model.City;
import com.baasplus.weather.model.Weather;


public class DetailFragment extends Fragment {

    private City city = new City();
    private TextView detailTV;
    private OnFragmentInteractionListener mListener;

    public static DetailFragment newInstance(City city) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        fragment.setArguments(args);
        return fragment;
    }

    public DetailFragment() {

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

        detailTV = (TextView) contentView.findViewById(R.id.fragment_detail_tv);

        if (city != null) {

            Weather weather = city.weather;
            if (weather != null) {

                detailTV.setText("");
                detailTV.append("城市： " + weather.getCity() + "\n");
                detailTV.append("天气状况： " + weather.getWeatherCondition() + "\n");
                detailTV.append("最低气温： " + weather.getLow() + "\n");
                detailTV.append("最高气温： " + weather.getHight() + "\n");
                detailTV.append("日期： " + weather.getDate() + "\n");
                detailTV.append("星期： " + weather.getDayOfWeek() + "\n");
                detailTV.append("更新时间： " + weather.getUpdateTime() + "\n");
            }
        }
        return contentView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        Log.e("log: ", "onButtonPressed is called, the uri is: " + uri.toString());
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        Log.e("log: ", "onAttach is called");
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
        Log.e("log:", "onDetach is called");
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
                    detailTV.append("城市： " + weather.getCity() + "\n");
                    detailTV.append("天气状况： " + weather.getWeatherCondition() + "\n");
                    detailTV.append("最低气温： " + weather.getLow() + "\n");
                    detailTV.append("最高气温： " + weather.getHight() + "\n");
                    detailTV.append("日期： " + weather.getDate() + "\n");
                    detailTV.append("星期： " + weather.getDayOfWeek() + "\n");
                    detailTV.append("更新时间： " + weather.getUpdateTime() + "\n");
                }
            }
        }
    }

    public City getCity() {
        return this.city;
    }

}
