package com.coderpage.weather.data;

import android.os.Message;

import com.coderpage.weather.define.DefineMessage;
import com.coderpage.weather.model.City;
import com.coderpage.weather.view.DetailFragment;
import com.coderpage.weather.view.MainActivity;

import java.util.ArrayList;

/**
 * Created by abner-l on 15/3/8.
 */
public class DetailFragmentList extends ArrayList<DetailFragment> {

    private static DetailFragmentList detailFragments = null;

    public static DetailFragmentList getInstance() {
        if (detailFragments == null) {
            detailFragments = new DetailFragmentList();
        }
        return detailFragments;
    }

    private DetailFragmentList() {
    }

    public DetailFragmentList getDetailFragmentList() {
        return detailFragments;
    }

    public DetailFragment getItem(City city) {
        for (DetailFragment detailFragment : detailFragments) {
            City city1 = detailFragment.getCity();
            if (city.code.equals(city1.code)) {
                return detailFragment;
            }
        }
        return null;
    }

    public boolean isExist(City city) {
        String cityCode = city.code;
        for (DetailFragment detailFragment : detailFragments) {
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
        for (int i = 0; i < detailFragments.size(); i++) {
            if (code.equals(detailFragments.get(i).getCity().code)) {
                synchronized (DetailFragmentList.class) {
                    detailFragments.remove(i);
                    Message msg = new Message();
                    msg.what = DefineMessage.MSG_DEL_CITY;
                    msg.obj = i;
                    MainActivity.mHandler.sendMessage(msg);
                }
            }
        }
    }

}
