package com.baasplus.weather;

import com.baasplus.weather.model.City;

/**
 * Created by abner-l on 15/3/22.
 */
public interface DataChangeListener {
    public void onChange(City city);
}
