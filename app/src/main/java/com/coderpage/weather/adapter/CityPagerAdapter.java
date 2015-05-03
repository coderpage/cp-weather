package com.coderpage.weather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.coderpage.weather.data.CityPages;

/**
 * Created by abner-l on 15/3/14.
 */
public class CityPagerAdapter extends FragmentStatePagerAdapter{

    CityPages detailFragments;
    public CityPagerAdapter(FragmentManager fm, CityPages detailFragments) {
        super(fm);
        this.detailFragments = CityPages.getInstance();
    }

    public CityPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return detailFragments.get(i);
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public int getCount() {
        return detailFragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

}
