package com.coderpage.weather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abner-l on 15/8/8.
 */
public class TrendView extends View {

    private Paint pointPaint;
    private Paint textPaint;
    private Paint topLinePaint;
    private Paint lowLinePaint;

    private int x[] = new int[7];
    private float radius = 8;
    private int h;
    private List<Integer> topTem;
    private List<Integer> lowTem;

    private Context c;

    public TrendView(Context context) {
        super(context);
        this.c = context;
        init();
    }
    public TrendView(Context context, AttributeSet attr) {
        super(context, attr);
        this.c = context;
        init();
    }
    private void init(){

        topTem = new ArrayList<Integer>();
        lowTem = new ArrayList<Integer>();

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setColor(Color.WHITE);

        topLinePaint = new Paint();
        topLinePaint.setColor(Color.YELLOW);
        topLinePaint.setAntiAlias(true);
        topLinePaint.setStrokeWidth(4);
        topLinePaint.setStyle(Paint.Style.FILL);

        lowLinePaint = new Paint();
        lowLinePaint.setColor(Color.BLUE);
        lowLinePaint.setAntiAlias(true);
        lowLinePaint.setStrokeWidth(4);
        lowLinePaint.setStyle(Paint.Style.FILL);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(25F);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public void setWidthHeight(int w, int h){
        x[0] = w/14;
        x[1] = w*3/14;
        x[2] = w*5/14;
        x[3] = w*7/14;
        x[4] = w*9/14;
        x[5] = w*11/14;
        x[6] = w*13/14;

        this.h = h;
    }

    public void setTemperature(List<Integer> top, List<Integer> low){
        this.topTem = top;
        this.lowTem = low;

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float space = 0f;
        float space1 = 0f;
        int temspace = 12;
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();

        float fontHeight = fontMetrics.bottom - fontMetrics.top;

        int h = this.h/2;
        int h2 = (int) (h - fontHeight/2);

        int h4 = (int) (h + fontHeight);
        for (int i = 0; i < topTem.size(); i++) {
            space = ( - topTem.get(i)) * temspace;
            if(topTem.get(i) != 100){
                if (i != topTem.size() - 1) {

                    space1 = ( - topTem.get(i+1)) * temspace;
                    canvas.drawLine(x[i], h + space, x[i+1], h + space1, topLinePaint);
                }
                canvas.drawText(topTem.get(i) + "°", x[i], h2 + space, textPaint);
                canvas.drawCircle(x[i], h + space, radius, pointPaint);
            }

        }
        int lowY = h4 ;
        for (int i = 0; i < lowTem.size(); i++) {
            space = (-lowTem.get(i)) * temspace;
            if (i != lowTem.size() - 1) {
                space1 = ( - lowTem.get(i+1)) * temspace;
                canvas.drawLine(x[i], h + space, x[i+1], h + space1, lowLinePaint);
            }
            canvas.drawText(lowTem.get(i) + "°", x[i], h4 + space, textPaint);
            canvas.drawCircle(x[i], h + space, radius, pointPaint);
            if(lowY < (int) (h4 + space + fontHeight)){
                lowY = (int) (h4 + space + fontHeight);
            }
        }
    }
}
