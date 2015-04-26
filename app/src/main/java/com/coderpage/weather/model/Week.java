package com.coderpage.weather.model;

import java.io.Serializable;

/**
 * Created by abner-l on 15/4/26.
 */
public class Week implements Serializable {
    private Weather weather1;
    private Weather weather2;
    private Weather weather3;
    private Weather weather4;
    private Weather weather5;

    public Weather getWeather1() {
        return weather1;
    }

    public void setWeather1(Weather weather1) {
        this.weather1 = weather1;
    }

    public Weather getWeather2() {
        return weather2;
    }

    public void setWeather2(Weather weather2) {
        this.weather2 = weather2;
    }

    public Weather getWeather3() {
        return weather3;
    }

    public void setWeather3(Weather weather3) {
        this.weather3 = weather3;
    }

    public Weather getWeather4() {
        return weather4;
    }

    public void setWeather4(Weather weather4) {
        this.weather4 = weather4;
    }

    public Weather getWeather5() {
        return weather5;
    }

    public void setWeather5(Weather weather5) {
        this.weather5 = weather5;
    }
}
