package com.example.hzqweather.model;

public class City {

	public String province;
	public String city;
	public String county;
	public String code;

	public City() {

	}

	public City(String province, String city, String county, String code) {
		this.province = province;
		this.city = city;
		this.county = county;
		this.code = code;
	}

	@Override
	public String toString() {
		return province + "  " + city + "  " + county + "  " + code;
	}

}
