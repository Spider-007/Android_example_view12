package com.htmitech.ztcustom.zt.view.circledemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import java.text.DecimalFormat;

public class CircleBar extends View {

	private RectF mColorWheelRectangle = new RectF();
	private Paint mDefaultWheelPaint;
	private Paint mColorWheelPaint;
	private Paint mColorWheelPaintCentre;
	private Paint mTextP, mTextch;
	private float circleStrokeWidth;
	private double mSweepAnglePer;
	private float mPercent;
	private double stepnumber, stepnumbernow;
	private float pressExtraStrokeWidth;
	private BarAnimation anim;
	private double stepnumbermax = 6000;// 默认最大步数
	private float stepnumber_y, Text_y, Month_y;
	private DecimalFormat fnum = new DecimalFormat("#.000");// 格式为保留小数点后3位
	private String topString;// 月度计划安排率
	private String bottomString;// 月度计划工作量
	private double bottomNum;
	private String danwei;
	private final DecimalFormat fnum_end = new DecimalFormat(
			"##0.0");
	// 梯度渐变的填充颜色
	private int[] arcColors = new int[] { 0xFF979FF5, 0xFF81B4E4, 0xFF6CC5D3,
			0xFF6CC5D3, 0xFF6CC5D3, 0xFF6CC5D3, 0xFF6CC5D3, 0xFF6CC5D3 };

	public CircleBar(Context context) {
		super(context);
		init(null, 0);
	}

	public CircleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public CircleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	SweepGradient sweepGradient = new SweepGradient(getMeasuredWidth() / 2,
			getMeasuredHeight() / 2, arcColors, null);

	private void init(AttributeSet attrs, int defStyle) {
		mColorWheelPaint = new Paint();
		// 环形颜色填充
		mColorWheelPaint.setShader(sweepGradient);
		// 设置画笔为白色
		// mColorWheelPaint.setColor(Color.rgb(249, 135, 49));
		mColorWheelPaint.setStyle(Paint.Style.STROKE);// 空心
		mColorWheelPaint.setStrokeCap(Paint.Cap.ROUND);// 圆角画笔
		mColorWheelPaint.setAntiAlias(true);// 去锯齿

		mColorWheelPaintCentre = new Paint();
		mColorWheelPaintCentre.setColor(Color.rgb(250, 250, 250));
		mColorWheelPaintCentre.setStyle(Paint.Style.STROKE);
		mColorWheelPaintCentre.setStrokeCap(Paint.Cap.ROUND);
		mColorWheelPaintCentre.setAntiAlias(true);

		mDefaultWheelPaint = new Paint();
		mDefaultWheelPaint.setColor(Color.rgb(127, 127, 127));
		mDefaultWheelPaint.setStyle(Paint.Style.STROKE);
		mDefaultWheelPaint.setStrokeCap(Paint.Cap.ROUND);
		mDefaultWheelPaint.setAntiAlias(true);

		mTextP = new Paint();
		mTextP.setAntiAlias(true);
		mTextP.setColor(Color.WHITE); // 百分比颜色修改

		// mTextnum = new Paint();
		// mTextnum.setAntiAlias(true);
		// mTextnum.setColor(Color.BLACK);

		mTextch = new Paint();
		mTextch.setAntiAlias(true);
		mTextch.setColor(Color.WHITE); // 设置单位属性颜色

		anim = new BarAnimation();
	}

	@Override
	protected void onDraw(Canvas canvas) {

		canvas.drawArc(mColorWheelRectangle, 135, 269, false,
				mDefaultWheelPaint);

		canvas.drawArc(mColorWheelRectangle, 135, 269, false,
				mColorWheelPaintCentre);
		if (mSweepAnglePer > 269) {
			mSweepAnglePer = 269;
		}
		canvas.drawArc(mColorWheelRectangle, 135, (float) mSweepAnglePer,
				false, mColorWheelPaint);
		canvas.drawText(
				topString,
				mColorWheelRectangle.centerX()
						- (mTextch.measureText(String.valueOf(topString)) / 2),
				Month_y, mTextch);
		Float f = Float.valueOf(mPercent);
		canvas.drawText(fnum_end.format(f) + "%", mColorWheelRectangle.centerX()
				- (mTextP.measureText(String.valueOf(f.intValue()) + "%") / 2),
				stepnumber_y, mTextP);
		// canvas.drawText(stepnumbernow + "", mColorWheelRectangle.centerX()
		// - (mTextnum.measureText(String.valueOf(stepnumbernow)) / 2),
		// stepnumber_y, mTextnum);
		canvas.drawText(
				bottomString,
				mColorWheelRectangle.centerX()
						- (mTextch.measureText(String.valueOf(bottomString)) / 2),
				Text_y, mTextch);
		canvas.drawText(
				fnum.format(bottomNum) + danwei,
				mColorWheelRectangle.centerX()
						- (mTextch.measureText(String.valueOf(bottomNum
								+ danwei)) / 2), Text_y + 100, mTextch);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height = getDefaultSize(getSuggestedMinimumHeight(),
				heightMeasureSpec);
		int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
		int min = Math.min(width, height);// 获取View最短边的长度
		setMeasuredDimension(min, min);// 强制改View为以最短边为长度的正方形
		circleStrokeWidth = Textscale(26, min);// 圆弧的宽度

		pressExtraStrokeWidth = Textscale(2, min);// 圆弧离矩形的距离
		mColorWheelRectangle.set(circleStrokeWidth + pressExtraStrokeWidth
				+ 100, circleStrokeWidth + pressExtraStrokeWidth + 100, min
				- circleStrokeWidth - pressExtraStrokeWidth - 100, min
				- circleStrokeWidth - pressExtraStrokeWidth - 100);// 设置矩形
																	// 圆环半径修改参数
		mTextP.setTextSize(Textscale(60, min)); // 百分比字体大小
		// mTextnum.setTextSize(Textscale(160, min));
		mTextch.setTextSize(Textscale(20, min));
		// mPercent_y = Textscale(300, min);
		stepnumber_y = Textscale(250, min); // 修改百分比数字纵坐标的显示位置
		Text_y = Textscale(320, min);
		Month_y = Textscale(180, min);
		mColorWheelPaint.setStrokeWidth(circleStrokeWidth);
		mColorWheelPaintCentre.setStrokeWidth(circleStrokeWidth);
		mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth - Textscale(2, min));
		mDefaultWheelPaint.setShadowLayer(Textscale(10, min), 0, 0,
				Color.rgb(127, 127, 127));// 设置阴影
	}

	/**
	 * 进度条动画
	 * 
	 * @author Administrator
	 * 
	 */
	public class BarAnimation extends Animation {
		public BarAnimation() {

		}

		/**
		 * 每次系统调用这个方法时， 改变mSweepAnglePer，mPercent，stepnumbernow的值，
		 * 然后调用postInvalidate()不停的绘制view。
		 */
		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f) {
				if(stepnumbermax == 0){
					mPercent = 0;
					mSweepAnglePer = 0;
				}else{
					mPercent = Float.parseFloat(fnum.format(interpolatedTime
							* stepnumber * 100f / stepnumbermax));// 将浮点值四舍五入保留一位小数
					mSweepAnglePer = interpolatedTime * stepnumber * 270
							/ stepnumbermax;
				}

				stepnumbernow = (int) (interpolatedTime * stepnumber);
			} else {
				if(stepnumbermax == 0){
					mPercent = 0;
					mSweepAnglePer = 0;
				}else{
					mPercent = Float.parseFloat(fnum.format(stepnumber * 100f
							/ stepnumbermax));// 将浮点值四舍五入保留一位小数
					mSweepAnglePer = stepnumber * 270 / stepnumbermax;
				}
				stepnumbernow = stepnumber;
			}
			postInvalidate();
		}
	}

	/**
	 * 根据控件的大小改变绝对位置的比例
	 * 
	 * @param n
	 * @param m
	 * @return
	 */
	public float Textscale(float n, float m) {
		return n / 500 * m;
	}

	/**
	 * 更新步数和设置一圈动画时间
	 * 
	 * @param stepnumber
	 * @param time
	 */
	public void update(double stepnumber, long time) {
		this.stepnumber = stepnumber;
		anim.setDuration(time);

		// setAnimationTime(time);
		this.startAnimation(anim);
	}

	/**
	 * 设置每天的最大步数
	 * 
	 * @param Maxstepnumber
	 */
	public void setMaxstepnumber(double Maxstepnumber) {
		stepnumbermax = Maxstepnumber;
	}

	/**
	 * 设置进度条颜色
	 * 
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void setColor(int red, int green, int blue) {
		mColorWheelPaint.setColor(Color.rgb(red, green, blue));
	}

	/**
	 * 设置动画时间
	 * 
	 * @param time
	 */
	public void setAnimationTime(int time) {
		anim.setDuration((long) (time * stepnumber / stepnumbermax));// 按照比例设置动画执行时间
	}

	public void setString(String topString, String bottomString) {
		this.topString = topString;
		this.bottomString = bottomString;
	}

	public void setBottomNum(double bottomNum) {
		this.bottomNum = bottomNum;
	}

	public void setDanWei(String danWei) {
		this.danwei = danWei;
	}
}
