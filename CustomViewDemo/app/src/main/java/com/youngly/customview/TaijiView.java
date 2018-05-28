package com.youngly.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by youngly on 2018/4/16.
 * E-mail: youngly.yang@gometech.com.cn
 */

public class TaijiView extends View {
    Paint blackPaint = new Paint();
    Paint whitePaint = new Paint();
    int pointX, pointY;

    {
        whitePaint.setColor(Color.WHITE);
    }

    public TaijiView(Context context) {
        super(context);
    }

    public TaijiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pointX = getWidth() / 2;
        pointY = getHeight() / 2;
        canvas.drawArc(0, 0, getWidth(), getHeight(), 90, 180, true, blackPaint);
        canvas.drawArc(0, 0, getWidth(), getHeight(), 270, 180, true, whitePaint);
        canvas.drawCircle(pointX, pointY / 2, pointX / 2, blackPaint);
        canvas.drawCircle(pointX, pointY * 3 / 2, pointX / 2 , whitePaint);
        canvas.translate(200, 200);
        canvas.drawText("hell", 0, 10, whitePaint);

    }
}
