package com.coderpage.weather.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coderpage.weather.model.City;
import com.coderpage.weather.model.Weather;

/**
 * Created by abner-l on 15/3/13.
 */
public class CityPage extends ViewGroup {

    public  City city;
    TextView detailTV;

//    public static CityPage newInstace(Context context,City c){
//
//        return new CityPage(context);
//    }

    public CityPage(Context context) {
        this(context, null);
        detailTV = new TextView(context);
    }



    public CityPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CityPage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View view = getChildAt(0);
        view.layout(l,t,r,b);
    }

    public void setCity(City city){
        this.city = city;
    }

    public void initView(){
        if (city != null) {
            Weather weather = city.weather;
            if (weather != null) {
                detailTV.setText("");
                detailTV.append("城市： " + city.getDisplayName() + "\n");
                detailTV.append("天气状况： " + weather.getWeatherCondition() + "\n");
                detailTV.append("最低气温： " + weather.getLow() + "\n");
                detailTV.append("最高气温： " + weather.getHight() + "\n");
                detailTV.append("日期： " + weather.getDate() + "\n");
                detailTV.append("星期： " + weather.getDayOfWeek() + "\n");
                detailTV.append("更新时间： " + weather.getUpdateTime() + "\n");
            }else {
                detailTV.setText("weather == null");
            }
        }else {
            detailTV.setText("city == null");
        }
        detailTV.setTextSize(20);
        detailTV.setTextColor(Color.BLACK);
        addView(detailTV);
    }
}
