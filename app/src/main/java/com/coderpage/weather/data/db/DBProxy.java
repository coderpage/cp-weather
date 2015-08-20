package com.coderpage.weather.data.db;

import android.content.Context;

import com.coderpage.weather.data.AllCity;
import com.coderpage.weather.model.City;
import com.coderpage.weather.model.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * @author abner
 * @since 2015-08-20
 */
public class DBProxy {

    public static List<City> getAllCity(Context ctx){
        List allCity = new ArrayList<City>();

        DBHelper helper = DBHelper.getInstance(ctx);


        return allCity;
    }

    public static City getCity(Context ctx){
        City city = new City();

        return city;
    }

//    public static Weather getWeathe(Context ctx){
//        Weather weather = new Weather() {
//        }
//    }
}
