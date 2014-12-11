package com.example.hzqweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hzqweather.R;
import com.example.hzqweather.controler.CitysList;
import com.example.hzqweather.model.City;

public class MainViewAdaper extends BaseAdapter{

	CitysList citys = null;
	Context mContext;
	
	public MainViewAdaper(Context context, CitysList citys){
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
			convertView = LayoutInflater.from(mContext).inflate(R.layout.item, null);
		}
		City item = citys.get(position);
		TextView tvCity = (TextView) convertView.findViewById(R.id.tv_city);
		
		tvCity.setText(item.displayName);
		return convertView;
	}
	
}
