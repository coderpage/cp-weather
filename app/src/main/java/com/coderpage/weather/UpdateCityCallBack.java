package com.coderpage.weather;

import com.coderpage.weather.model.City;

/**
 * Created by abner-l on 15/7/14.
 */
public interface UpdateCityCallBack {
    public void onSuccess(City city);
    public void onError(String msg);
}
