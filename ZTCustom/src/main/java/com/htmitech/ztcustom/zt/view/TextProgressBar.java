package com.htmitech.ztcustom.zt.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.htmitech.ztcustom.zt.util.DensityUtil;

public class TextProgressBar extends ProgressBar {
    String text = "";
    Paint mPaint;

    public TextProgressBar(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initText(context);
    }

    public TextProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        initText(context);
    }


    public TextProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        initText(context);
    }

    @Override
    public synchronized void setProgress(int progress) {
        // TODO Auto-generated method stub
        super.setProgress(progress);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //this.setText();
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        float x = 0.0f;
        if (getMax() != 0) {
            x = getWidth() - rect.centerX() * 2.5f;
        }
        float y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(this.text, x, y, this.mPaint);
    }

    //初始化，画笔
    private void initText(Context context) {
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.WHITE);
        this.mPaint.setTextSize(DensityUtil.dip2px(context, 12));

    }

    //设置文字内容
    public void setText(String progress) {
        this.text = progress;
    }
}
