package widget;

/**
 * Created by lenovo on 2016/5/18.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

import htmitech.com.componentlibrary.listener.ScrollViewListener;


public class ObservableScrollView extends ScrollView {

    private ScrollViewListener scrollViewListener = null;
    private double nLenStart;
    private Context context;
    private int textSize = 0;
    private int[] textSizes = new int[]{17, 19, 21};
    private int nCnt = 1;

    public ObservableScrollView(Context context) {
        super(context);
        this.context = context;
    }

    public ObservableScrollView(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
//        if (scrollViewListener != null) {
//            scrollViewListener.onScrollChanged();
//        }
        //onTouchEvent
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)

    {
        /*
        必须重写这个方法 否则手势不太好用
         */

        if (event.getPointerCount() == 2) {
            onTouchEvent(event);
            return true;
        } else {
            return super.onInterceptTouchEvent(event);
        }

    }

    /**
     * 手势的放大与缩小
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        nCnt = event.getPointerCount();
        int n = event.getAction();
        if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_DOWN && 2 == nCnt)//2表示两个手指</span>
        {
            int xlen = Math.abs((int) event.getX(0) - (int) event.getX(1));
            int ylen = Math.abs((int) event.getY(0) - (int) event.getY(1));
            nLenStart = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);
        } else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_POINTER_UP && 2 == nCnt) {
            int xlen = Math.abs((int) event.getX(0) - (int) event.getX(1));
            int ylen = Math.abs((int) event.getY(0) - (int) event.getY(1));
            double nLenEnd = Math.sqrt((double) xlen * xlen + (double) ylen * ylen);
            DisplayMetrics dm = getResources().getDisplayMetrics();
            float value = dm.scaledDensity;
            if (nLenEnd > nLenStart)//通过两个手指开始距离和结束距离，来判断放大缩小
            {

                textSize++;
                if (textSize >= textSizes.length) {
                    textSize = textSizes.length - 1;
                } else {
                    if(textSizes != null)
                        scrollViewListener.onZoomText(textSizes[textSize]);
                }
                Log.d("ScrollView", "放大" + textSize);
            } else {
                textSize--;
                if (textSize < 0) {
                    textSize = 0;
                } else {
                    scrollViewListener.onZoomText(textSizes[textSize]);
                }

                Log.d("ScrollView", "缩小" + textSize);
            }
        } else {
            if (scrollViewListener != null)
                scrollViewListener.onRequfouch();
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        Log.d("onLayout", "onLayout -------------------->" );
    }
}