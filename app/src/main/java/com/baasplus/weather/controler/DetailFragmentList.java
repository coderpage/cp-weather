package com.baasplus.weather.controler;

import com.baasplus.weather.view.DetailFragment;

import java.util.ArrayList;

/**
 * Created by abner-l on 15/3/8.
 */
public class DetailFragmentList extends ArrayList<DetailFragment> {

    private static DetailFragmentList detailFragmentList = null;

    public DetailFragmentList getInstance() {
        if (detailFragmentList == null) {
            detailFragmentList = new DetailFragmentList();
        }
        return detailFragmentList;
    }

    private DetailFragmentList() {
    }

}
