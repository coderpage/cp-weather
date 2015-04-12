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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean location) {
        this.location = location;
    }

    @Override
	public String toString() {
		return "[ province:" + province + "  city:" + city + "  county:" + county + "  code:" + code + "  displayName:"
				+ displayName + " ]";
	}

}
