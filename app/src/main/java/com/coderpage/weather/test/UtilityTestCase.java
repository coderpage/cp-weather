package com.coderpage.weather.test;

import android.test.AndroidTestCase;

import com.coderpage.weather.tool.Utility;

public class UtilityTestCase extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        // TODO Auto-generated method stub
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        // TODO Auto-generated method stub
        super.tearDown();
    }

    public void testCitycodeExist() throws Exception {
        System.out.println(Utility.citycodeExist());
        assertTrue(Utility.citycodeExist());
    }


}
