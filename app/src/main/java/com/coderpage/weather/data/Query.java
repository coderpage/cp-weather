package com.coderpage.weather.data;

import android.util.Log;

import com.coderpage.weather.NetworkCallback;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Query {
    private static boolean DEBUG = true;
    private static final String TAG = Query.class.getSimpleName();

    public static void weatherQueryAsync(final NetworkCallback callback, final String cityId) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                String URL = "http://weather.51wnl.com/weatherinfo/GetMoreWeather?cityCode=" + cityId
                        + "&weatherType=0";

                HttpGet httpRequest = new HttpGet(URL);
                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResponse = httpClient.execute(httpRequest);
                    if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                        String response = EntityUtils.toString(httpResponse.getEntity());
                        if (DEBUG) {
                            Log.e(TAG, "response: " + response);
                        }
                        callback.onSuccess(response);
                    } else {
                        callback.onError("查询数据出错，返回码：" + httpResponse.getStatusLine().getStatusCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (DEBUG) {
                        Log.d(TAG, "HTTP出错:" + e.getMessage());
                    }
                }

            }
        }).start();
    }

    public static String weatherQuerySync(NetworkCallback callback, String cityId) {

        String URL = "http://weather.51wnl.com/weatherinfo/GetMoreWeather?cityCode=" + cityId
                + "&weatherType=0";
        String response = null;

        HttpGet httpRequest = new HttpGet(URL);
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                response = EntityUtils.toString(httpResponse.getEntity());
                callback.onSuccess(response);
                if (DEBUG) {
                    Log.e(TAG, "response: " + response);
                }
            } else {
                callback.onError("查询数据出错，返回码：" + httpResponse.getStatusLine().getStatusCode());
                if (DEBUG) {
                    Log.d(TAG, "查询数据出错，返回码：" + httpResponse.getStatusLine().getStatusCode());
                }
            }
        } catch (Exception e) {
            callback.onError("HTTP出错:" + e.getMessage());
            if (DEBUG) {
                Log.d(TAG, "HTTP出错:" + e.getMessage());
            }
        }
        return response;
    }
}
