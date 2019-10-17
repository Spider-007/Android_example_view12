package com.htmitech.ztcustom.zt.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.domain.Dictitemlist;
import com.htmitech.ztcustom.zt.domain.damage.Table1;
import com.htmitech.ztcustom.zt.util.DensityUtil;

import java.util.ArrayList;

/**
 * 
 * ==================================================
 * @author Tony                                     |
 *													|
 *  圆形自定义										|
 *													|
 * ==================================================
 *
 */
public class CircleWaveView extends View implements Runnable {
	private float mWidth;
	private float mHeight;
	private float Width = 300;
	private float Height = 300;
	private float centerX; // 圆心X
	private float centerY; // 圆心Y
	private float floatRadius; // 变化的半径
	private float maxRadius = -1; // 圆半径
	private volatile boolean started = false;
	private Paint mLinePaint;
	private Paint mCPaint;
	private Paint mLPaint;
	private int waveInterval = 35; // 圆环的宽度
	private boolean centerAlign = true;// 居中
	private float bottomMargin = 0;// 底部margin
	private Context context;
	private float radius = 50;
	private RoundOnClick mRoundOnClick;
	public boolean isRestart = false;
	private Bitmap bitmap;
	private ArrayList<BitmapMxr> bitMapList;
	public String allValue;
	private Paint textPaint;
	private String allName;

	public boolean lineStart = false;
	private float lineStopW;
	private float lineStopH;
	private float lineStartW;
	private float lineStartH;
	private float oneProH = -20, twoProH = -20, threeProH = 12, fourProH = 30,
			fiveProH = 16;
	private float oneProW = -20, twoProW = 20, threeProW = -12, fourProW = -10,
			fiveProW = 16;
	private float oneLength;
	private float[][] proList = new float[][] { { oneProH, oneProW },
			{ twoProH, twoProW }, { threeProH, threeProW },
			{ fourProH, fourProW }, { fiveProH, fiveProW } };
	private float[] countFloat;
	ArrayList<Float> tempListW = new ArrayList<Float>();
	ArrayList<Float> tempListH = new ArrayList<Float>();

	
	public class BitmapMxr {
		public Bitmap bitmap;
		float bitMapW, bitMapH;  //半径
		float bitX,bitY;
		public Table1 mTable1;
	}

	public CircleWaveView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
	}

	public CircleWaveView(Context context) {
		this(context, null, 0);
		this.context = context;
	}

	public CircleWaveView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		initView();
	}

	private void initView() {
		mLinePaint = new Paint();
		mCPaint = new Paint();
		mLPaint = new Paint();
		textPaint = new Paint();
	}

	private void init() {
		mWidth = getWidth();// 调节扩散的范围
		mHeight = getHeight();

		mLPaint.setStrokeWidth(2.0F);
		mLPaint.setTextSize(DensityUtil.sp2px(context,15));
		mLPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mLPaint.setColor(getResources().getColor(R.color.white));
		mLPaint.setAntiAlias(true);
		
		textPaint.setStrokeWidth(1.0F);
		textPaint.setTextSize(DensityUtil.sp2px(context,17));
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setColor(getResources().getColor(R.color.white));
		textPaint.setAntiAlias(true);
		
		mCPaint.setStyle(Paint.Style.FILL);
		mCPaint.setStrokeWidth(waveInterval);
		mCPaint.setColor(getResources().getColor(R.color.shebei));
		mCPaint.setTextSize(DensityUtil.sp2px(context,15));
		mCPaint.setAntiAlias(true);
		mCPaint.setFilterBitmap(true);
		mLinePaint.setStrokeWidth(2.0F);
		mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mLinePaint.setColor(getResources().getColor(R.color.shebei));
		mLinePaint.setAntiAlias(true);
		centerX = mWidth / 2.0F;
		if (centerAlign) {
			centerY = (mHeight / 2.0F);
		} else {
			centerY = mHeight - bottomMargin;
		}

		if (mWidth >= mHeight) {
			maxRadius = Height / 2.0F;
		} else {
			maxRadius = Width / 2.0F;
		}
		lineStartW = centerX;
		lineStartH = centerY;
		lineStopH = lineStartH;
		lineStopW = lineStartW;
		floatRadius = (maxRadius % waveInterval);

		initTempList();
		oneLength = (float) (Math.sqrt(lineStartW * lineStartW + lineStartH
				* lineStartH) / 2.8);
		start();
	}

	float bitMapW, bitMapH;

	public void start() {
		if (!started) {
			lineStart = true;
			started = true;
			new Thread(this).start();
		}
	}

	public void stop() {
		started = false;
	}

	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		isRestart = true;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (maxRadius <= 0.0F) {
			return;
		}

		float radius = floatRadius % waveInterval;// waveInterval初值为50，floatRadius初值为
		while (true) {
			if (!lineStart) {
				canvas.drawLines(countFloat, mLinePaint);

				for (int i = 0; i < num; i++) {
					try {
						canvas.drawBitmap(bitMapList.get(i).bitmap,
                                tempListW.get(i) - bitMapList.get(i).bitMapW / 2,
                                tempListH.get(i) - bitMapList.get(i).bitMapH / 2,
                                mCPaint);
						bitMapList.get(i).bitX = tempListW.get(i) - bitMapList.get(i).bitMapW / 2;  //设置图片的X位置

						bitMapList.get(i).bitY = tempListH.get(i) - bitMapList.get(i).bitMapH / 2;  //设置图片的Y位置
						canvas.drawText(bitMapList.get(i).mTable1.name,
                                tempListW.get(i) - mCPaint.getTextSize(),
                                tempListH.get(i) + bitMapList.get(i).bitMapH + mCPaint.getTextSize(),
                                mCPaint);
						textPaint.setTextSize(DensityUtil.sp2px(context,15));
						canvas.drawText((int)bitMapList.get(i).mTable1.value+"  件",
                                tempListW.get(i) - mCPaint.getTextSize() ,
                                tempListH.get(i) + bitMapList.get(i).bitMapH + 20+mCPaint.getTextSize() * 2,
                                textPaint);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			} else {
				if (null != canvas && mLinePaint != null && countFloat != null) {
					canvas.drawLines(countFloat, mLinePaint);
				}
			}
			int alpha = (int) (255.0F * (1.0F - radius / maxRadius));
			if (alpha <= 0) {
				break;
			}
			mLinePaint.setAlpha(alpha);
			canvas.drawCircle(centerX, centerY, radius - 50f, mLinePaint);
			textPaint.setTextSize(DensityUtil.sp2px(context,17));
			canvas.drawText(allName+":", centerX
					+ (int) (maxRadius / 1.5), centerY, mCPaint);

			canvas.drawText( (int)Float.parseFloat((allValue == null || allValue.equals("")) ? 0 + "" : allValue)+"   件", centerX
					+ (int) (maxRadius / 1.5) + 20, centerY + mCPaint.getTextSize()* 2, textPaint);
			canvas.drawCircle(centerX, centerY, this.radius, mCPaint);
			radius += waveInterval;
		}
	}

	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
		if (!isRestart && hasWindowFocus) {
			init();
			isRestart = hasWindowFocus;
		} else {
//			stop();
		}
	}

	public void run() {
		runRound();
	}

	public void runRound() {
		countFloat = new float[num * 4];
		while (started) {
			floatRadius = 4.0F + floatRadius;
			if (floatRadius > maxRadius) {
				floatRadius = (maxRadius % waveInterval);
			}
			if (lineStart) {
				countFloat.clone();
				for (int i = 0; i < num; i++) {
					float Ws = tempListW.get(i) + proList[i][1];
					float Hs = tempListH.get(i) + proList[i][0];
					tempListW.set(i, Ws);
					tempListH.set(i, Hs);
					countFloat[i * 4 + 0] = lineStartW;
					countFloat[i * 4 + 1] = lineStartH;
					countFloat[i * 4 + 2] = tempListW.get(i);
					countFloat[i * 4 + 3] = tempListH.get(i);
					if (i == 0) {
						float H = Math.abs(Math.abs(Hs) - centerX);
						float W = Math.abs(Math.abs(Ws) - centerX);
						float length2 = (H * H + W * W);
						float temp = (float) (Math.sqrt(length2) - oneLength);
						if (temp > 0) {
							lineStart = false;
						}
					}
				}
			}
			postInvalidate();
			try {
				Thread.sleep(140);
			} catch (InterruptedException localInterruptedException) {
				localInterruptedException.printStackTrace();
			}

		}
	}

	private int num = 0;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// 获取点击屏幕时的点的坐标
		float x = event.getX();
		float y = event.getY();
		whichCircle(x, y);
		return super.onTouchEvent(event);
	}

	public void whichCircle(float x, float y) {
		float mx = x - centerX;
		float my = y - centerY;
		float result = mx * mx + my * my;
		if (result <= maxRadius * maxRadius) {
			if (mRoundOnClick != null) {
				mRoundOnClick.onClick();
			}
		}
		// 获取点击每一个的监听
		if(bitMapList != null){
			for(BitmapMxr mBitmapMxr :bitMapList){
				float bx = x - mBitmapMxr.bitX;
				float by = y - mBitmapMxr.bitY;
				float bresult = bx * bx + by * by;
				if(bresult <= mBitmapMxr.bitMapH * mBitmapMxr.bitMapW){
					if (mRoundOnClick != null && mBitmapMxr.mTable1 != null) {  //mBitmapMxr.mTable1 != null ----> 剩余 不提供点击事件
						mRoundOnClick.otherOnClick(mBitmapMxr.mTable1);
						break;
					}
				}
			}
		}
	}

	public void setMaxRadius(float maxRadius) {
		this.maxRadius = maxRadius;
	}


	public void setWaveInterval(int waveInterval) {
		this.waveInterval = waveInterval;
	}

	public void setCenterAlign(boolean centerAlign) {
		this.centerAlign = centerAlign;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public void setRoundOnClick(RoundOnClick mRoundOnClick) {
		this.mRoundOnClick = mRoundOnClick;
	}

	public interface RoundOnClick {
		void onClick();
		void otherOnClick(Table1 mTable1);
	}

	public void setNum(int num) {
		countFloat = new float[num * 4];
		lineStart = true;
		this.num = num;
	}

	public void setTotal() {

	}

	public void calculatePrce() {
		// radius
	}

	public void setAllValue(String allValue) {
		this.allValue = allValue;
	}

	public void initTempList(){
		tempListW.clear();
		tempListH.clear();
		tempListW.add(lineStopW);
		tempListW.add(lineStopW);
		tempListW.add(lineStopW);
		tempListW.add(lineStopW);
		tempListW.add(lineStopW);
		tempListH.add(lineStopH);
		tempListH.add(lineStopH);
		tempListH.add(lineStopH);
		tempListH.add(lineStopH);
		tempListH.add(lineStopH);
	}
	
	public void setTable(ArrayList<Table1> tableList,ArrayList<Dictitemlist> dictitemlist,boolean flag) {
		initTempList();
		
		bitMapList = new ArrayList<BitmapMxr>();
		int i = 0;
		for (Table1 mTable1 : tableList) {
			if(flag && i == tableList.size() - 1){
				break;
			}
			float matrx = (float) ((((radius / Float.parseFloat(allValue)) * mTable1.value) / radius) * 0.7);
			for(Dictitemlist mDictitemlist :dictitemlist){
				if(mDictitemlist.getCode().equals(""+mTable1.key)){
					mTable1.name = mDictitemlist.getName();
					break;
				}
			}
			setBitmapMxr(matrx, mTable1);
			i++;
		}
		if(flag){
			num = tableList.size() - 1;
		}else{
			num = tableList.size();
		}
		lineStart = true;
		countFloat = new float[num * 4];
	}
	/**
	 * 计算衍生小件的半径   
	 * 
	 * 0.7 相当于 1
	 * 
	 * 
	 * @param matrx
	 * @param mTable1
	 */
	public void setBitmapMxr(float matrx, Table1 mTable1) {
		if (matrx < 0.01f) {
			matrx = 0.01f;
		}
		BitmapMxr mBitmapMxr = new BitmapMxr();
		Bitmap b = BitmapFactory.decodeResource(getResources(),
				R.drawable.zt_china_point);
		Matrix matrix = new Matrix();
		matrix.postScale(matrx, matrx);
		bitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(),
				matrix, false);
		bitMapW = b.getWidth() * matrx;
		bitMapH = b.getHeight() * matrx;
		mBitmapMxr.bitmap = bitmap;
		mBitmapMxr.bitMapH = bitMapH;
		mBitmapMxr.bitMapW = bitMapW;
		mBitmapMxr.mTable1 = mTable1; 
		bitMapList.add(mBitmapMxr);
	}

	/***
	 * 
	 * 
	 * 设置总数的Name
	 * @param allName 
	 */
	public void setName(String allName){
		this.allName = allName;
	}
}
