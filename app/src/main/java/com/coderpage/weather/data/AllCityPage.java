package com.coderpage.weather.data;

import android.os.Message;

import com.coderpage.weather.define.DefineMessage;
import com.coderpage.weather.model.City;
import com.coderpage.weather.view.CityPage;
import com.coderpage.weather.view.activity.MainActivity;

import java.util.ArrayList;

/**
 * Created by abner-l on 15/3/8.
 */
public class AllCityPage extends ArrayList<CityPage> {

    private static AllCityPage mCityPages = null;

    public synchronized static AllCityPage getInstance() {
        if (mCityPages == null) {
            mCityPages = new AllCityPage();
        }
        return mCityPages;
    }

    private AllCityPage() {
    }

    public AllCityPage getDetailFragmentList() {
        return mCityPages;
    }

    public CityPage getItem(City city) {
        for (CityPage detailFragment : mCityPages) {
            City city1 = detailFragment.getCity();
            if (city.code.equals(city1.code)) {
                return detailFragment;
            }
        }
        return null;
    }

    public boolean isExist(City city) {
        String cityCode = city.code;
        for (CityPage detailFragment : mCityPages) {
            if (cityCode.equals(detailFragment.getCity().code)) {
                return true;
            }
        }
        return false;
    }

    public void deleteItemByCityCode(String code) {
        if (code == null) {
            throw new NullPointerException("code 不能为空");
        }
        for (int i = 0; i < mCityPages.size(); i++) {
            if (code.equals(mCityPages.get(i).getCity().code)) {
                synchronized (AllCityPage.class) {
                    mCityPages.remove(i);
                    Message msg = new Message();
                    msg.what = DefineMessage.MSG_DEL_CITY;
                    msg.obj = i;
                    MainActivity.mHandler.sendMessage(msg);
                }
            }
        }
    }

}
