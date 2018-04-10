package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class Practice8DrawArcView extends View {

    Paint paint = new Paint();

    public Practice8DrawArcView(Context context) {
        super(context);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawArc() 方法画弧形和扇形
        paint.setStyle(Paint.Style.FILL); // 填充模式
        paint.setAntiAlias(true);
        canvas.drawArc(200, 100, 800, 500, -90, 90, true, paint); // 绘制扇形
        canvas.drawArc(200, 100, 800, 500, 20, 140, true, paint); // 绘制弧形

        Paint yellowPaint = new Paint();
        yellowPaint.setAntiAlias(true);
        yellowPaint.setColor(Color.YELLOW);
        canvas.drawArc(200, 100, 800, 500, 20, 140, false, yellowPaint); // 绘制弧形

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(200, 100, 800, 500, 180, 20, false, paint);
        canvas.drawArc(200, 100, 800, 500, 210, 30, true, paint);
    }
}
