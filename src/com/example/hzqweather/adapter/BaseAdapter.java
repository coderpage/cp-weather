package com.example.hzqweather.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hzqweather.R;
import com.example.hzqweather.model.City;

public class BaseAdapter extends android.widget.BaseAdapter{

	List<City>citys = null;
	Context mContext;
	
	public BaseAdapter(Context context, List<City>citys){
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
		String province = item.province;
		if (province == null ) {
			province = "";
		}
		String city = item.city;
		if (city == null) {
			city = "";
		}
		String county = item.county;
		if (county == null) {
			county = "";
		}
		tvCity.setText(province + "  " + city + "  " + county);
		return convertView;
	}

	

}
