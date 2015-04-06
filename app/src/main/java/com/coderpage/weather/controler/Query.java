package com.coderpage.weather.controler;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.coderpage.weather.define.DefineMessage;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class Query {

	public static void WeatherQuery(final Handler handler, final String cityId) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				String URL = "http://weather.51wnl.com/weatherinfo/GetMoreWeather?cityCode=" + cityId
						+ "&weatherType=0";
				// String URL =
				// "http://weather.123.duba.net/static/weather_info/"+ cityId
				// +".html";
				String weatherResponse = "";
				HttpGet httpRequest = new HttpGet(URL);
				try {
					HttpClient httpClient = new DefaultHttpClient();
					HttpResponse httpResponse = httpClient.execute(httpRequest);
					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						String response = EntityUtils.toString(httpResponse.getEntity());
					//	weatherResponse = response.substring(response.indexOf("(") + 1, response.indexOf(")"));
						weatherResponse = response;
						Log.i("response:  ", weatherResponse.toString());
						JSONObject weatherJson = new JSONObject(response);
						Message msg = new Message();
						msg.arg1 = DefineMessage.MSG_QUERY_WEATHER_SUCC;
						msg.obj = weatherJson;
						handler.sendMessage(msg);
						Log.i("Weather_Result", weatherJson.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
					Log.i("error", "出错。。。");
				}

			}
		}).start();
	}


}
