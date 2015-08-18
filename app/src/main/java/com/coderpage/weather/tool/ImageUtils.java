package com.coderpage.weather.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.coderpage.weather.R;

/**
 * Created by abner-l on 15/8/14.
 */
public class ImageUtils {

    public static Bitmap getSmallIcon(Context ctx, int sourceID) {
        int iconSize = (int) ctx.getResources().getDimension(R.dimen.weather_icon_size);
        Bitmap bitmap = BitmapFactory.decodeResource(ctx.getResources(), sourceID);
        return Bitmap.createScaledBitmap(bitmap, iconSize, iconSize, true);
    }

}
