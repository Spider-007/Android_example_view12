package com.htmitech.ztcustom.zt.view.zgtw;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ChildViewPager extends ViewPager { /** 触摸时按下的点 **/  
    PointF downP = new PointF();  
    /** 触摸时当前的点 **/  
	   PointF curP = new PointF();   
	   OnSingleTouchListener onSingleTouchListener;  
	  
	    public ChildViewPager(Context context, AttributeSet attrs) {  
	        super(context, attrs);  
	        // TODO Auto-generated constructor stub  
	    }  
	 
	    public ChildViewPager(Context context) {  
	       super(context);  
	        // TODO Auto-generated constructor stub  
	    }  
	  
	    @Override  
	    public boolean onInterceptTouchEvent(MotionEvent arg0) {  
	       // TODO Auto-generated method stub  
        //当拦截触摸事件到达此位置的时候，返回true，          //说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent  
	        return false;  
	    }  
	  
	   @Override  
	    public boolean onTouchEvent(MotionEvent arg0) {  
	       // TODO Auto-generated method stub  
	        //每次进行onTouch事件都记录当前的按下的坐标  
	        curP.x = arg0.getX();  
	        curP.y = arg0.getY();  
	  
	        if(arg0.getAction() == MotionEvent.ACTION_DOWN){  
	            //记录按下时候的坐标  
	            //切记不可用 downP = curP ，这样在改变curP的时候，downP也会改变  
	           downP.x = arg0.getX();  
	            downP.y = arg0.getY();  
		       getParent().requestDisallowInterceptTouchEvent(false);  
		    	  
	            
	        }  
  
	       if(arg0.getAction() == MotionEvent.ACTION_MOVE){  
	           //此句代码是为了通知他的父ViewPager现在进行的是本控件的操作，不要对我的操作进行干扰  
	    	   if( Math.abs(curP.y-downP.y)>0&&  Math.abs(curP.y-downP.y)-Math.abs(curP.x-downP.x)>0){
	    		   getParent().requestDisallowInterceptTouchEvent(false);  
	    	   }else{
	    		   getParent().requestDisallowInterceptTouchEvent(true);
	    	   }
	        }  
	  
	        if(arg0.getAction() == MotionEvent.ACTION_UP){  
	            //在up时判断是否按下和松手的坐标为一个点  
	            //如果是一个点，将执行点击事件，这是我自己写的点击事件，而不是onclick  
	            if(downP.x==curP.x && downP.y==curP.y){  
	                onSingleTouch();  
	                return true;  
	            }  
	            if( Math.abs(curP.y)-Math.abs(downP.y)>5&&  Math.abs(curP.y-downP.y)-Math.abs(curP.x-downP.x)>0){
		    		   getParent().requestDisallowInterceptTouchEvent(false);  
		    	   }
	        }  
	
	       return super.onTouchEvent(arg0);  
	  }  

	        /** 
   * 单击 
	    */  
        public void onSingleTouch() {  
	      if (onSingleTouchListener!= null) {  
	
	           onSingleTouchListener.onSingleTouch();  
	       }  
	    }  
	 
	   /** 
	    * 创建点击事件接口 
	    * @author wanpg 
	   * 
	    */  
	   public interface OnSingleTouchListener {  
	       public void onSingleTouch();  
	   }  
  
	    public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {  
	       this.onSingleTouchListener = onSingleTouchListener;  
	    }  
	    
	    @Override
	    	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    	 int height = 0;
	         for(int i = 0; i < getChildCount(); i++) {
	             View child = getChildAt(i);
	             child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
	             int h = child.getMeasuredHeight();
	             if(h > height) height = h;
	         }

	         heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

	         super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	     }

	    	
	    
}
