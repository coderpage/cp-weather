package com.coderpage.weather.test;

import android.test.AndroidTestCase;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by abner-l on 15/8/3.
 */
public class WeatherJsonParse extends AndroidTestCase {
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testParseWeather() throws Exception{
        String jsonStr = loadJsonFromAssets("weather.json");
        JSONObject data = new JSONObject(jsonStr);
        JSONObject allData = data.getJSONArray("HeWeather data service 3.0").getJSONObject(0);
        JSONObject aqi = allData.getJSONObject("aqi").getJSONObject("city");
        Log.d("testParseWeather_tag", aqi.getString("pm25"));

    }


    private String loadJsonFromAssets(String name) {
        String result = null;
        try {
            InputStream is = getContext().getAssets().open(name);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int count = -1;
            while ((count = is.read(data, 0, 1024)) != -1) {
                bos.write(data, 0, count);
            }
            data = null;
            result = new String(bos.toByteArray());
        } catch (IOException e) {
            fail(e.getMessage());
        }
        return result;
    }

}
