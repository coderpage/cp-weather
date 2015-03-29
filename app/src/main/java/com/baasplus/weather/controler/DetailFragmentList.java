package com.baasplus.weather.controler;

import com.baasplus.weather.model.City;
import com.baasplus.weather.view.DetailFragment;

import java.util.ArrayList;

/**
 * Created by abner-l on 15/3/8.
 */
public class DetailFragmentList extends ArrayList<DetailFragment> {

    private static DetailFragmentList detailFragmentList = null;

    public static DetailFragmentList getInstance() {
        if (detailFragmentList == null) {
            detailFragmentList = new DetailFragmentList();
        }
        return detailFragmentList;
    }

    private DetailFragmentList() {
    }

    public DetailFragmentList getDetailFragmentList() {
        return detailFragmentList;
    }

    public DetailFragment getItem(City city) {
        for (DetailFragment detailFragment : detailFragmentList) {
            City city1 = detailFragment.getCity();
            if (city.code.equals(city1.code)) {
                return detailFragment;
            }
        }
        return null;
    }

    public boolean isExist(City city) {
        String cityCode = city.code;
        for (DetailFragment detailFragment : detailFragmentList) {
            if (cityCode.equals(detailFragment.getCity().code)) {
                return true;
            }
        }
        return false;
    }
}
