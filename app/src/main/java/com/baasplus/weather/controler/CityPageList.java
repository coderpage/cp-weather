package com.baasplus.weather.controler;

import com.baasplus.weather.view.CityPage;

import java.util.ArrayList;

/**
 * Created by abner-l on 15/3/13.
 */
public class CityPageList extends ArrayList<CityPage>{

    public static CityPageList cityPageList = null;

    public static CityPageList getInstance() {
        if (cityPageList == null) {
            cityPageList = new CityPageList();
        }
        return cityPageList;
    }

    public CityPageList() {
    }


}
