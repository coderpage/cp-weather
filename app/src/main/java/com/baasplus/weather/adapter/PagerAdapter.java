package com.baasplus.weather.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.baasplus.weather.controler.CityPageList;
import com.baasplus.weather.view.CityPage;

/**
 * Created by abner-l on 15/3/14.
 */
public class PagerAdapter extends android.support.v4.view.PagerAdapter {

    private CityPageList cityPages;

    public PagerAdapter(CityPageList cityPages) {
        this.cityPages = cityPages;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(cityPages.get(position));
        CityPage page = cityPages.get(position);
        Log.e("----","position : " + position +"   cityPages.size: " + cityPages.size());

        return cityPages.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(cityPages.get(position));
    }

//    @Override
//    public int getItemPosition(Object object) {
//        return cityPages.indexOf(object);
//    }


    @Override
    public int getCount() {
        return cityPages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }
}
