package com.htmitech.emportal.ui.chart.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import com.htmitech.emportal.ui.chart.listener.ScrollViewListenerX;

public class ObservableScrollViewX extends HorizontalScrollView {
	private ScrollViewListenerX scrollViewListener = null;  
	  
    public ObservableScrollViewX(Context context) {  
        super(context);  
    }  
  
    public ObservableScrollViewX(Context context, AttributeSet attrs,  
            int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    public ObservableScrollViewX(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public void setScrollViewListener(ScrollViewListenerX scrollViewListener) {  
        this.scrollViewListener = scrollViewListener;  
    }  
  
    @Override  
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {  
        super.onScrollChanged(x, y, oldx, oldy);  
        if (scrollViewListener != null) {  
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);  
        }  
    }
}
