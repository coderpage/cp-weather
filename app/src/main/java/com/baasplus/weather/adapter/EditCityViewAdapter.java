package com.baasplus.weather.adapter;

import android.content.Context;
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
public class EditCityViewAdapter extends BaseAdapter {

	CitysList citys;
	Context mContext;

	public EditCityViewAdapter(Context context, CitysList citys) {
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
		convertView = LayoutInflater.from(mContext).inflate(R.layout.edit_city_lv_item, null);
		City item = citys.get(position);
		TextView tvCity = (TextView) convertView.findViewById(R.id.tv_city);
		tvCity.setText(item.displayName);
		return convertView;
	}

}
