package com.coderpage.weather.model;

/**
 * Created by abner-l on 15/8/4.
 */
public enum Week {
    SUNDAY("星期日"),
    MONDAY("星期一"),
    TUESDAY("星期二"),
    WEDNESDAY("星期三"),
    THURSDAY("星期四"),
    FRIDAY("星期五"),
    SATURDAY("星期六");

    private final String value;

    private Week(String stringValue){
        this.value = stringValue;
    }

    public String value(){
        return value;
    }

}
