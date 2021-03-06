package com.coderpage.weather.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coderpage.weather.R;
import com.coderpage.weather.adapter.EditCityViewAdapter;
import com.coderpage.weather.data.AllCity;
import com.coderpage.weather.data.AllCityPage;
import com.coderpage.weather.data.db.DBHelper;
import com.coderpage.weather.model.City;

import java.util.ArrayList;
import java.util.List;

public class EditCitysActivity extends ActionBarActivity {

	private City city;
	public Handler mHandler = null;
	private ListView lvMyCitys;
	private List<City> selectedCitys = new ArrayList<City>();
	private TextView tvDeleteCity;
	private BaseAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_citys);
		tvDeleteCity = (TextView) findViewById(R.id.tv_delete_city);
		lvMyCitys = (ListView) findViewById(R.id.lv_mycitys);
		adapter = new EditCityViewAdapter(this, AllCity.cities);
		lvMyCitys.setAdapter(adapter);
		lvMyCitys.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				TextView tvDeleteIcon = (TextView) view.findViewById(R.id.tv_delete_icon);
				City c = AllCity.cities.get(position);
				if (selectedCitys.contains(c)) {
					selectedCitys.remove(c);
					tvDeleteIcon.setTextColor(Color.rgb(159, 159, 159));
				} else {
					selectedCitys.add(c);
					tvDeleteIcon.setTextColor(Color.RED);
				}
				setDeleteTvVisible();
			}
		});
	}

	/**
	 * 删除城市按钮点击事件
	 * @param v
	 */
	public void deleteCitys(View v) {
		for (int i = 0; i < selectedCitys.size(); i++) {
			City c = selectedCitys.get(i);
			deleteCityByCityCode(c.code);
			selectedCitys.remove(i);
		}
		updateListView();
		setDeleteTvVisible();
		Toast.makeText(EditCitysActivity.this, "删除成功", Toast.LENGTH_LONG).show();
	}
	
	private void updateListView(){
		adapter.notifyDataSetChanged();
	}
	
	private void setDeleteTvVisible(){
		if (selectedCitys.size()>0) {
			tvDeleteCity.setVisibility(TextView.VISIBLE);
		}else {
			tvDeleteCity.setVisibility(TextView.GONE);
		}
	}

	private void deleteCityByCityCode(String code) {
		DBHelper dbHelper = DBHelper.getInstance(EditCitysActivity.this);
		boolean deleted = dbHelper.deleteCareCity(code);
		if (deleted) {
            AllCity.cities.deleteCity(code);
            AllCityPage.getInstance().deleteItemByCityCode(code);
		}
	}

	/**
	 * 添加城市
	 * @param v
	 */
	public void addCity(View v) {
		startActivity(new Intent(EditCitysActivity.this, SearchCityActivity.class));
	}

}
