package com.htmitech.ztcustom.zt.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by htmitech on 2018/8/16.
 */

public class SideCityBar extends View {

    private String[] letterStrings = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "S", "Y", "Z"};
    private Paint paint;
    private Paint mPaint;
    private Context mContext;
    private int heght;
    private int postion;
    private boolean isShow = false;
    private Rect rect = new Rect();

    public SideCityBar(Context context) {
        super(context);
        init(context);
    }

    public SideCityBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SideCityBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(30);
        mPaint.setAntiAlias(true);

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heght = getHeight() / letterStrings.length;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.BLACK);


        for (int i = 0; i < letterStrings.length; i++) {
            String s1 = letterStrings[i];
            mPaint.getTextBounds(s1, 0, s1.length(), rect);
            int h1 = rect.height();
            int w1 = rect.width();
            //  mPaint.measureText(s1)只需要文字宽度可用此方法。
            int h2 = heght / 2 + h1 / 2 + heght * i;
            int w2 = getWidth() / 2 - w1 / 2;
            canvas.drawText(s1, w2, h2, mPaint);
            if (isShow && (i == postion)) {
                canvas.drawRect((getWidth() - heght) / 2, (0 + i * heght), (getWidth() + heght) / 2, (heght + i * heght), paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y1 = event.getY();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            isShow = false;
            mTextView.setVisibility(GONE);
            mOnchangeString.setTextShow(false);
            invalidate();
            return true;
        } else {
            isShow = true;
            //计算框框的位置
            postion = (int) (y1 / heght);
            if (postion < letterStrings.length) {
                mTextView.setVisibility(VISIBLE);
                mTextView.setText(letterStrings[postion]);
                mOnchangeString.getChangeString(letterStrings[postion]);
                mOnchangeString.setTextShow(true);
                invalidate();
            }

            return true;
        }
    }

    TextView mTextView;

    public void setTextView(TextView textView) {
        mTextView = textView;
    }

    public void setOnChangeLis(OnchangeString onChangeLis) {
        mOnchangeString = onChangeLis;
    }

    OnchangeString mOnchangeString;

    public interface OnchangeString {
        void getChangeString(String s);

        void setTextShow(boolean b);
    }


}
