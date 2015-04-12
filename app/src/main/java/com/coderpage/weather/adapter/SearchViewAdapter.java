package com.coderpage.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coderpage.weather.R;
import com.coderpage.weather.model.City;

import java.util.ArrayList;
import java.util.List;
/**
 * SearchCityActivity中listview的adapter
 */
public class SearchViewAdapter extends android.widget.BaseAdapter{

	List<City>citys = new ArrayList<>();
	Context mContext;
	
	public SearchViewAdapter(Context context, List<City>citys){
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
		String province = item.province;
		String city = item.city;
		String county = item.county;

        if (county != null && city != null && province != null){
            tvCity.setText(county + "-" + city + "-"+ province);
        }else if (county == null && city != null && province != null){
            tvCity.setText(city + "-"+ province);
        }else if (county == null && city == null && province != null){
            tvCity.setText(province);
        }else if (county != null && city == null && province != null){
            tvCity.setText(county + "-"+ province);
        }

		return convertView;
	}

}
