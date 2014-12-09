package com.example.controler;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Query {

	public static void WeatherQuery(final Handler handler, final String cityId){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//http://weather.51wnl.com/weatherinfo/GetMoreWeather?cityCode=101010100&weatherType=0
				String URL = "http://weather.123.duba.net/static/weather_info/"+ cityId +".html";
				String weatherResponse="";
				HttpGet httpRequest = new HttpGet(URL);
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpResponse httpResponse = httpClient.execute(httpRequest);
					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						String response = EntityUtils.toString(httpResponse.getEntity());
						weatherResponse = response.substring(response.indexOf("(")+1, response.indexOf(")"));
						Log.i("response:  ", weatherResponse.toString());
						JSONObject weatherJson = new JSONObject(weatherResponse);
						Message msg = new Message();
						msg.arg1 = 0;
						msg.obj = weatherJson;
						handler.sendMessage(msg);
						Log.i("Weather_Result",weatherJson.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.i("error", "出错。。。");
				}
				
			}
		}).start();
	}
	
	
}
