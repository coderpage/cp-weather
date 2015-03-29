package com.baasplus.weather.controler;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

import com.baasplus.weather.define.DefineMessage;
import com.baasplus.weather.model.Weather;
import com.baasplus.weather.tool.Utility;

public class WeatherHeiper {
	
	private static WeatherHeiper mWeatherManager = null;
	public Weather weather;
	public String cityCode;
	public JSONObject weatherJson;
	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.arg1) {
			case DefineMessage.MSG_QUERY_WEATHER_SUCC:
				JSONObject weatherJson = (JSONObject) msg.obj;
				weather = recoverWeather(weatherJson);
				CitysList.updateWeather(cityCode, weather);
				break;

			default:
				break;
			}
		};
	};
	
	public WeatherHeiper(){
		
	}
	
	public void queryWeather(String cityId){
		cityCode = cityId;
		Query.WeatherQuery(mHandler, cityId);
	}
	
	private Weather recoverWeather(JSONObject weatherJson){
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
			String low = "null";
			String hight = "null";
			if (temp.contains("~")) {
				String[] lowAndHight = temp.split("~");
				low = lowAndHight[0];
				hight = lowAndHight[1];
			}
			weather.setCity(city);
			weather.setCityID(cityID);
			weather.setWeatherCondition(weatherCondition);
			weather.setDate(date);
			weather.setDayOfWeek(dayOfWeek);
			weather.setLow(low);
			weather.setHight(hight);
			String updateTime = Utility.DateFormater(System.currentTimeMillis());
			weather.setUpdateTime(updateTime);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return weather;
	}
	
}
