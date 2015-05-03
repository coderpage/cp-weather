package com.coderpage.weather.data;

import android.os.Handler;

import com.coderpage.weather.define.DefineMessage;
import com.coderpage.weather.model.Weather;
import com.coderpage.weather.model.Week;
import com.coderpage.weather.tool.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class WeatherHelper {
	
	private static WeatherHelper mWeatherManager = null;
	public Weather weather;
	public String cityCode;
	public JSONObject weatherJson;
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.arg1) {
			case DefineMessage.MSG_QUERY_WEATHER_SUCC:
				JSONObject weatherJson = (JSONObject) msg.obj;
				weather = recoverWeather(weatherJson);
				Cities.updateWeather(cityCode, weather);
				break;

			default:
				break;
			}
		};
	};
	
	public WeatherHelper(){
		
	}
	
	public void queryWeather(String cityId){
		cityCode = cityId;
		Query.WeatherQuery(mHandler, cityId);
	}
	
	public Weather recoverWeather(JSONObject weatherJson){
		if (weatherJson == null) {
			return null;
		}
		
		Weather weather = new Weather();
		try {
//			String updateTime = weatherJson.getString("update_time");
			JSONObject weatherInfo = weatherJson.getJSONObject("weatherinfo");
			String city = weatherInfo.getString("city");
			String cityID = weatherInfo.getString("cityid");
			String weatherCondition = weatherInfo.getString("weather1");
			String dayOfWeek = weatherInfo.getString("week");
			String date = weatherInfo.getString("date_y");
			String temp = weatherInfo.getString("temp1");

            Week week = new Week();
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
			weather.setWeatherCondition(weatherCondition);
			weather.setDate(date);
			weather.setDayOfWeek(dayOfWeek);
			weather.setLow(low);
			weather.setHight(hight);
			String updateTime = Utility.DateFormater(System.currentTimeMillis());
			weather.setUpdateTime(updateTime);
            weather.setWeek(week);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return weather;
	}
	
}
