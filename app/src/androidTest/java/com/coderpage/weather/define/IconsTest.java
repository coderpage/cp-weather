package com.coderpage.weather.define;

import android.test.AndroidTestCase;

import com.coderpage.weather.R;

public class IconsTest extends AndroidTestCase{
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetDayIcon() throws Exception{
        int resoure_id = Icons.getDayIcon(100);
        assertEquals(R.drawable.w0, resoure_id);
    }

    public void testGetNightIcon() throws Exception{
        int resoure_id = Icons.getNightIcon(100);
        assertEquals(R.drawable.w30, resoure_id);
    }

    public void testGetDayIconCode() throws Exception{
        int code = Icons.getDayIconCode(R.drawable.w0);
        assertEquals(100, code);
    }

    public void testGetNightIconCode() throws Exception{
        int code = Icons.getNightIconCode(R.drawable.w30);
        assertEquals(100,code);
    }
}