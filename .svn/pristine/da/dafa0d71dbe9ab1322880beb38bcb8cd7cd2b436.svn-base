package com.htmitech.MyView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;


/**
 * 矩形下载自定义
 */
public class RectProgressView extends View{

    private static final String TAG = "CircleProgressBar";

    private float mMaxProgress = 100;

    private int mProgress = 0;

    private final int mCircleLineStrokeWidth = 8;

    private final int mTxtStrokeWidth = 2;

    // 画圆所在的距形区域
    private RectF mRectF;

    private final Paint mPaint;

    private final Context mContext;

    private String mTxtHint1;

    private String mTxtHint2;
    private RectF normalOval;

    public RectProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mRectF = new RectF(0, 0, getWidth(), getHeight());
        mPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRectF.left = (float) (getMeasuredWidth() * 0.1);
        mRectF.top = (float) (getMeasuredHeight() * 0.1);
        mRectF.right = (float) (getMeasuredWidth() * 0.9);
        mRectF.bottom = (float) (getMeasuredHeight() * 0.9);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = this.getWidth();
        int height = this.getHeight();

//        if (width != height) {
//            int min = Math.min(width, height);
//            width = min;
//            height = min;
//        }

        // 设置画笔相关属性
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setStrokeWidth(mCircleLineStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL);

        // 位置
//        mRectF.left = mCircleLineStrokeWidth / 2; // 左上角x
//        mRectF.top = mCircleLineStrokeWidth / 2; // 左上角y
//        mRectF.right = width - mCircleLineStrokeWidth / 2; // 左下角x
//        mRectF.bottom = height - mCircleLineStrokeWidth / 2; // 右下角y

        // 绘制圆圈，进度条背景
//        canvas.drawArc(mRectF, -90, 360, false, mPaint);
//        mPaint.setColor(Color.rgb(0xf8, 0x60, 0x30));
        mPaint.setAlpha(80);
//        canvas.drawArc(mRectF, 0, ((float) mProgress / mMaxProgress) * 360, true, mPaint);


        // 设置矩形,宽度是实际进度
        mRectF = new RectF(0, 0, (mProgress / mMaxProgress) * getWidth(),
                getHeight());
        // 画进度矩形
        canvas.drawRect(mRectF, mPaint);


        mPaint.setAlpha(255);
        mPaint.setColor(mContext.getResources().getColor(com.htmitech.addressbook.R.color.yellow_page_search_bg));
        // 绘制进度文案显示
        mPaint.setStrokeWidth(mTxtStrokeWidth);
        String text = mProgress + "%";
        if(mProgress == -1){
            text = "等待中...";
        }

        int textHeight = height / 4;
        mPaint.setTextSize(textHeight);
        int textWidth = (int) mPaint.measureText(text, 0, text.length());
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + textHeight / 2, mPaint);

        if (!TextUtils.isEmpty(mTxtHint1)) {
            mPaint.setStrokeWidth(mTxtStrokeWidth);
            text = mTxtHint1;
            textHeight = height / 8;
            mPaint.setTextSize(textHeight);
//            mPaint.setColor(Color.rgb(0x00, 0x00, 0x00));
            textWidth = (int) mPaint.measureText(text, 0, text.length());
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText(text, width / 2 - textWidth / 2, height / 4 + textHeight / 2, mPaint);
        }

        if (!TextUtils.isEmpty(mTxtHint2)) {
            mPaint.setStrokeWidth(mTxtStrokeWidth);
            text = mTxtHint2;
            textHeight = height / 8;
            mPaint.setTextSize(textHeight);
            textWidth = (int) mPaint.measureText(text, 0, text.length());
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawText(text, width / 2 - textWidth / 2, 3 * height / 4 + textHeight / 2, mPaint);
        }
    }

    public float getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(float maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        this.invalidate();
    }

    public void setProgressNotInUiThread(int progress) {
        this.mProgress = progress;
        this.postInvalidate();
    }

    public String getmTxtHint1() {
        return mTxtHint1;
    }

    public void setmTxtHint1(String mTxtHint1) {
        this.mTxtHint1 = mTxtHint1;
    }

    public String getmTxtHint2() {
        return mTxtHint2;
    }

    public void setmTxtHint2(String mTxtHint2) {
        this.mTxtHint2 = mTxtHint2;
    }
}