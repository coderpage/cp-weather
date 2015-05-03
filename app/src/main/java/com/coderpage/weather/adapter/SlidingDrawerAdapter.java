package com.coderpage.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.coderpage.weather.R;
import com.coderpage.weather.data.Cities;
import com.coderpage.weather.model.City;

/**
 * MainActivity中listview的adapter
 */
public class SlidingDrawerAdapter extends BaseAdapter {

    Cities citys = null;
    Context mContext;

    public SlidingDrawerAdapter(Context context, Cities citys) {
        this.mContext = context;
        this.citys = citys;
    }

    @Override
    public int getCount() {
        return citys.size();
    }

    @Override
    public Object getItem(int position) {
        return citys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.sliding_lv_item, null);
        }
        City item = citys.get(position);
        TextView tvCity = (TextView) convertView.findViewById(R.id.tv_city);

        tvCity.setText(item.displayName);

        return convertView;
    }

}
