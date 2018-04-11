package com.hencoder.hencoderpracticedraw3.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice11GetFontSpacingView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    String text = "Hello HenCoder";

    public Practice11GetFontSpacingView(Context context) {
        super(context);
    }

    public Practice11GetFontSpacingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice11GetFontSpacingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setTextSize(60);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 使用 Paint.getFontSpacing() 来获取推荐的行距
        float spacing = paint.getFontSpacing();

        canvas.drawText(text, 50, 100, paint);
        // 获取text的边界，并绘制出text的边界
        Rect rect = new Rect();
        paint.setStyle(Paint.Style.STROKE);
        paint.getTextBounds(text, 0, text.length(), rect);
        rect.set(rect.left + 50, rect.top + 100, rect.right + 50, rect.bottom + 100);
        canvas.drawRect(rect, paint);

        canvas.drawText(text, 50, 100 + spacing, paint);

        canvas.drawText(text, 50, 100 + spacing * 2, paint);
    }
}
