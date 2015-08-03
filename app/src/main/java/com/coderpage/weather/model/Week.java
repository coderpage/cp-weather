package com.coderpage.weather.model;

import java.io.Serializable;

/**
 * Created by abner-l on 15/4/26.
 */
public class Week implements Serializable {
    private WeatherBase weather1 = new WeatherBase();
    private WeatherBase weather2 = new WeatherBase();
    private WeatherBase weather3 = new WeatherBase();
    private WeatherBase weather4 = new WeatherBase();
    private WeatherBase weather5 = new WeatherBase();

    public WeatherBase getWeather1() {
        return weather1;
    }

    public void setWeather1(WeatherBase weather1) {
        this.weather1 = weather1;
    }

    public WeatherBase getWeather2() {
        return weather2;
    }

    public void setWeather2(WeatherBase weather2) {
        this.weather2 = weather2;
    }

    public WeatherBase getWeather3() {
        return weather3;
    }

    public void setWeather3(WeatherBase weather3) {
        this.weather3 = weather3;
    }

    public WeatherBase getWeather4() {
        return weather4;
    }

    public void setWeather4(WeatherBase weather4) {
        this.weather4 = weather4;
    }

    public WeatherBase getWeather5() {
        return weather5;
    }

    public void setWeather5(WeatherBase weather5) {
        this.weather5 = weather5;
    }

    public void update(){}

    public static class WeatherBase implements Serializable{
        private String low  = "";
        private String hight = "";
        private String dayOfWeek = "";
        private String weatherCondition = "";

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getHight() {
            return hight;
        }

        public void setHight(String hight) {
            this.hight = hight;
        }

        public String getDayOfWeek() {
            return dayOfWeek;
        }

        public void setDayOfWeek(String dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }

        public String getWeatherCondition() {
            return weatherCondition;
        }

        public void setWeatherCondition(String weatherCondition) {
            this.weatherCondition = weatherCondition;
        }
    }
}
