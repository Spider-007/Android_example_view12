package com.htmitech.ztcustom.zt.view.zgtw;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ProgressBar;

/**
 * 带加载进度条的ImageView
 * 
 * @author LiangJunYing email:junying.hao@163.com
 * 
 */
public class LoadingImageView extends ProgressBar {
	/*** 背景图片 */
	private Drawable bgDrawable;
	/** 前景图片 */
	private Drawable fgDrawable;
	/** 是否显示加载进度条 */
	private boolean isShowProgress;

	private Resources rsc;
	private double progress;
	private int progressHeight;
	private int progressWidth;
	private int progressLeft;
	private int progressTop;
	private int progressRight;
	private int progressBottom;
	private double allprogress;
	private int paddLeft;

	public LoadingImageView(Context context) {
		this(context, null);
	}

	public LoadingImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LoadingImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		rsc = getResources();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (bgDrawable == null) {
			return;
		}
		// progressLeft =
		// getMeasuredWidth()/2-(fgDrawable.getIntrinsicWidth()/2);
		progressLeft = 0;
		// progressTop =
		// getMeasuredHeight()/2-(fgDrawable.getIntrinsicHeight()/2);
		progressTop = 0;
		// progressRight =
		// getMeasuredWidth()/2+(fgDrawable.getIntrinsicWidth()/2);
		progressRight = getMeasuredWidth();
		// progressBottom =
		// getMeasuredHeight()/2+(fgDrawable.getIntrinsicHeight()/2);
		progressBottom = getMeasuredHeight();
		paddLeft = getPaddingLeft();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	/**
	 * 设置背景图片
	 * 
	 * @param drawableRes
	 */
	public void setBgDrawableRes(int drawableRes) {
		bgDrawable = rsc.getDrawable(drawableRes);
		invalidate();
	}

	public void setFgDrawableRes(int drawableRes) {
		fgDrawable = rsc.getDrawable(drawableRes);
		invalidate();
	}

	public void setProgress(double progress, boolean flag) {
		isShowProgress = flag;
		this.progress = progress;
		invalidate();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (bgDrawable != null) {
			bgDrawable.setBounds(progressLeft, progressTop, progressRight,
					progressBottom);
			bgDrawable.draw(canvas);
		}
		super.onDraw(canvas);
		if (bgDrawable != null && isShowProgress) {
			bgDrawable.setBounds(progressLeft, progressTop, progressRight,
					progressBottom);
			bgDrawable.draw(canvas);
		}
		if (fgDrawable != null && isShowProgress) {
			// 根据进度计算图片显示的高的比
			// progressHeight = fgDrawable.getIntrinsicHeight()*progress/100;
			// 关键代码，设置图片的显示区域
			// canvas.clipRect(progressLeft,progressBottom-progressHeight,progressRight,progressBottom);
			// fgDrawable.setBounds(progressLeft, progressTop, progressRight,
			// progressBottom);
			// fgDrawable.draw(canvas);
			// 根据进度计算图片显示的宽的比
			// progressWidth = fgDrawable.getIntrinsicWidth()*progress/100;
			// //getIntrinsicWidth()返回固定宽度 也就是视图实际上表现出来的宽度...不是图片实际大小的宽度
			// progressWidth = (int)
			// (fgDrawable.getIntrinsicWidth()*progress/allprogress);
			// //getIntrinsicWidth()返回固定宽度 也就是视图实际上表现出来的宽度...不是图片实际大小的宽度
			progressWidth = (int) ((progressRight - progressLeft + paddLeft)
					* progress / allprogress); // getIntrinsicWidth()返回固定宽度
												// 也就是视图实际上表现出来的宽度...不是图片实际大小的宽度
			canvas.clipRect(progressWidth - progressLeft, progressTop,
					progressLeft, progressBottom);
			fgDrawable.setBounds(progressLeft, progressTop, progressRight,
					progressBottom);
			fgDrawable.draw(canvas);
		}
	}

	public double getAllprogress() {
		return allprogress;
	}

	public void setAllprogress(float allprogress) {
		this.allprogress = allprogress;
	}

}
