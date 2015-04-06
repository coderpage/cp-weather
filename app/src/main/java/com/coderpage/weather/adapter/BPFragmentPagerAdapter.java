package com.coderpage.weather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.coderpage.weather.controler.DetailFragmentList;

/**
 * Created by abner-l on 15/3/14.
 */
public class BPFragmentPagerAdapter extends FragmentStatePagerAdapter{

    DetailFragmentList detailFragments;
    public BPFragmentPagerAdapter(FragmentManager fm,DetailFragmentList detailFragments) {
        super(fm);
        this.detailFragments = DetailFragmentList.getInstance();
    }

    public BPFragmentPagerAdapter(FragmentManager fm) {
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
