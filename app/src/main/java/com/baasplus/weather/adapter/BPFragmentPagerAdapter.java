package com.baasplus.weather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.baasplus.weather.controler.DetailFragmentList;

/**
 * Created by abner-l on 15/3/14.
 */
public class BPFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter{

    DetailFragmentList detailFragments;
    public BPFragmentPagerAdapter(FragmentManager fm,DetailFragmentList detailFragments) {
        super(fm);
        this.detailFragments = detailFragments;
    }

    public BPFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return detailFragments.get(i);
    }


    @Override
    public int getCount() {
        return detailFragments.size();
    }

}
