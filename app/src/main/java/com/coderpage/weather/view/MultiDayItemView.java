package com.coderpage.weather.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coderpage.weather.R;

/**
 * Created by abner-l on 15/8/12.
 */
public class MultiDayItemView extends FrameLayout {

    public int id;
    private TextView weekTV;
    private TextView dayConditionTV;
    private ImageView dayIcon;
    private ImageView nightIcon;
    private TextView nightConditionTV;
    private TextView dateTV;

    public MultiDayItemView(Context context) {
        super(context);
        initView(context);
    }

    public MultiDayItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    private void initView(Context context) {
        LinearLayout contentView = (LinearLayout) inflate(context, R.layout.multi_days_view_item, null);
        addView(contentView);
        setLayoutParams(new LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        weekTV = (TextView) contentView.findViewById(R.id.multi_days_week_tv);
        dayConditionTV = (TextView) contentView.findViewById(R.id.multi_days_day_cond_tv);
        dayIcon = (ImageView) contentView.findViewById(R.id.multi_days_day_iv);
        nightIcon = (ImageView) contentView.findViewById(R.id.multi_days_night_iv);
        nightConditionTV = (TextView) contentView.findViewById(R.id.multi_days_night_cond_tv);
        dateTV = (TextView) contentView.findViewById(R.id.multi_days_date_tv);

        id = contentView.getId();
    }


    public void setWeekTV(String week) {
        weekTV.setText(week);
    }


    public void setDayConditionTV(String dayCondtion) {
        dayConditionTV.setText(dayCondtion);
    }

    public void setDayIcon(Bitmap bm) {
        dayIcon.setImageBitmap(bm);
    }


    public void setNightIcon(Bitmap bm) {
        nightIcon.setImageBitmap(bm);
    }


    public void setNightConditionTV(String nightCondition) {
        nightConditionTV.setText(nightCondition);
    }


    public void setDateTV(String date) {
        dateTV.setText(date);
    }
}
