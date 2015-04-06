package com.coderpage.weather.model;

import java.io.Serializable;

public class City implements Serializable{

	private static final long serialVersionUID = 1L;
	public String province;
	public String city;
	public String county;
	public String code;
	public String displayName;
	public Weather weather;
	public long lastUpdateTime;
	public boolean primary = false;
	public boolean location = false;
	
	public City() {

	}
	
	public City(String province, String city, String county, String code) {
		this.province = province;
		this.city = city;
		this.county = county;
		this.code = code;
		if (county != null) {
			this.displayName = county;
		} else if (city != null) {
			this.displayName = city;
		} else {
			this.displayName = province;
		}
	}

	@Override
	public String toString() {
		return "[ province:" + province + "  city:" + city + "  county:" + county + "  code:" + code + "  displayName:"
				+ displayName + " ]";
	}

}
