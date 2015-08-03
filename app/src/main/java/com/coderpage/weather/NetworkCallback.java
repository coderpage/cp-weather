package com.coderpage.weather;

/**
 * Created by abner-l on 15/6/30.
 */
public interface NetworkCallback {
    public void onSuccess(String weather);

    public void onError(String message);
}
