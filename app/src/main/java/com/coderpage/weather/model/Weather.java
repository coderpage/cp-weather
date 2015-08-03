package com.coderpage.weather.model;

import com.coderpage.weather.tool.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Weather implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String low  = "";
	private String hight = "";
	private String date = "";
	private String dayOfWeek = "";
	private String weatherCondition = "";
	private String updateTime = "";
    private Week week = new Week();

	public static Weather instanceByJson(String weather){

		Weather instance = new Weather();
		if (weather == null) {
			return instance;
		}

		try {
			Week week = new Week();
			JSONObject weatherJson = new JSONObject(weather);
			JSONObject weatherInfo = weatherJson.getJSONObject("weatherinfo");
			String city = weatherInfo.getString("city");
			String cityID = weatherInfo.getString("cityid");
			String weatherCondition = weatherInfo.getString("weather1");
			String dayOfWeek = weatherInfo.getString("week");
			String date = weatherInfo.getString("date_y");
			String temp = weatherInfo.getString("temp1");

			Class<Week> weekClass = Week.class;
			for (int i=2; i<7;i++){
				Weather weatherN = new Weather();
				String weatherConditionN = weatherInfo.getString("weather"+i);
				String tempN = weatherInfo.getString("temp"+i);
				String lowN = "null";
				String hightN = "null";
				if (tempN.contains("~")) {
					String[] lowAndHightN = tempN.split("~");
					lowN = lowAndHightN[0];
					hightN = lowAndHightN[1];
				}
				weatherN.setLow(lowN);
				weatherN.setHight(hightN);
				weatherN.setWeatherCondition(weatherConditionN);
				Method setter;
				try {
					String methodName = "setWeather" + (i-1);
					setter = weekClass.getMethod(methodName,Weather.class);
				} catch (NoSuchMethodException e) {
					throw new NoSuchMethodError("找不到方法：setWeather" + (i-1) + "()");
				}

				try {
					setter.invoke(week,weatherN);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					throw new IllegalAccessError("无法执行方法：setWeather" + (i-1) + "()");
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					throw new RuntimeException("执行方法：setWeather" + (i-1) + "() 时出错");
				}
			}
			String low = "null";
			String hight = "null";
			if (temp.contains("~")) {
				String[] lowAndHight = temp.split("~");
				low = lowAndHight[0];
				hight = lowAndHight[1];
			}
			instance.setWeatherCondition(weatherCondition);
			instance.setDate(date);
			instance.setDayOfWeek(dayOfWeek);
			instance.setLow(low);
			instance.setHight(hight);
			String updateTime = Utility.DateFormater(System.currentTimeMillis());
			instance.setUpdateTime(updateTime);
			instance.setWeek(week);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return instance;
	}


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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    @Override
	public String toString() {
		return weatherCondition;//city + cityID +
	}

	public void update(String weather){
		if (weather == null) {
			return ;
		}

		try {
			Week week = new Week();
			JSONObject weatherJson = new JSONObject(weather);
			JSONObject weatherInfo = weatherJson.getJSONObject("weatherinfo");
			String city = weatherInfo.getString("city");
			String cityID = weatherInfo.getString("cityid");
			String weatherCondition = weatherInfo.getString("weather1");
			String dayOfWeek = weatherInfo.getString("week");
			String date = weatherInfo.getString("date_y");
			String temp = weatherInfo.getString("temp1");

			Class<Week> weekClass = Week.class;
			for (int i=2; i<7;i++){
				Week.WeatherBase weatherN = new Week.WeatherBase();
				String weatherConditionN = weatherInfo.getString("weather"+i);
				String tempN = weatherInfo.getString("temp"+i);
				String lowN = "null";
				String hightN = "null";
				if (tempN.contains("~")) {
					String[] lowAndHightN = tempN.split("~");
					lowN = lowAndHightN[0];
					hightN = lowAndHightN[1];
				}
				weatherN.setLow(lowN);
				weatherN.setHight(hightN);
				weatherN.setWeatherCondition(weatherConditionN);
				Method setter;
				try {
					String methodName = "setWeather" + (i-1);
					setter = weekClass.getMethod(methodName,Week.WeatherBase.class);
				} catch (NoSuchMethodException e) {
					throw new NoSuchMethodError("找不到方法：setWeather" + (i-1) + "()");
				}

				try {
					setter.invoke(week,weatherN);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					throw new IllegalAccessError("无法执行方法：setWeather" + (i-1) + "()");
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					throw new RuntimeException("执行方法：setWeather" + (i-1) + "() 时出错");
				}
			}
			String low = "null";
			String hight = "null";
			if (temp.contains("~")) {
				String[] lowAndHight = temp.split("~");
				low = lowAndHight[0];
				hight = lowAndHight[1];
			}
			this.setWeatherCondition(weatherCondition);
			this.setDate(date);
			this.setDayOfWeek(dayOfWeek);
			this.setLow(low);
			this.setHight(hight);
			String updateTime = Utility.DateFormater(System.currentTimeMillis());
			this.setUpdateTime(updateTime);
			this.setWeek(week);

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
