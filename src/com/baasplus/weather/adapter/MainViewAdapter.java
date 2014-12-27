package com.baasplus.weather.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baasplus.weather.R;
import com.baasplus.weather.controler.CitysList;
import com.baasplus.weather.model.City;

/**
 * MainActivity中listview的adapter
 */
public class MainViewAdapter extends BaseAdapter{

	CitysList citys = null;
	Context mContext;
	
	public MainViewAdapter(Context context, CitysList citys){
		this.mContext = context;
		this.citys = citys;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return citys.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return citys.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.sliding_lv_item, null);
		}
		City item = citys.get(position);
		TextView tvCity = (TextView) convertView.findViewById(R.id.tv_city);
		if (position == 0) {
			tvCity.setText("编辑地点");
			tvCity.setTextColor(Color.RED);
		}else {
			tvCity.setText(item.displayName);
		}
		return convertView;
	}
	
}
