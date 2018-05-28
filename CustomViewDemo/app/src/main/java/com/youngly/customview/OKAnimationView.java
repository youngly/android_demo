package com.youngly.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by youngly on 2018/4/16.
 * E-mail: youngly.yang@gometech.com.cn
 */

public class OKAnimationView extends View {

    private Bitmap bitmap;
    private int progress;

    public OKAnimationView(Context context) {
        super(context);
    }

    public OKAnimationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.maps);

        new CountDownTimer(100000, 20) {

            @Override
            public void onTick(long millisUntilFinished) {
                setProgress();
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

    public void setProgress() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();

        if (progress > 100) {
            progress = 0;
        } else {
            progress++;
        }

        Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight() / 100 * progress);
        Rect dest = new Rect(0, 0,  bitmap.getWidth(), bitmap.getHeight() / 100 * progress);
        canvas.drawBitmap(bitmap, src, dest, paint);
    }
}
