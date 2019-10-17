package com.htmitech.ztcustom.zt.view.linechart;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.ztcustom.R;


public class LineChartView extends View {

	private Activity myAc;// 用于获取屏幕的信息

	private float[] pt_horizontallines;// 横线的起始点坐标

	private float[] pt_verticallines;// 纵线的起始点坐标

	private float[] pt_values;// 折线点的坐标
	private float[] pt_values_jh;// 计划的折线点的坐标

	private float width_temp, height_temp;// 临时的宽高

	private float[] values;// 折点的数值
	private float[] values_jh;// 计划折点的数值

	private int Y_value_num;// y轴的值的数量

	private float Ymax; // y轴的最大值

	private float Ymin; // y轴的最小值

	private String[] X_values; // 横坐标要显示

	private OnClickListener valueOnClickListener;// 折点点击事件的监听

	private int Y_value_span;// y轴的数值间隔

	private int valueIndex;// 选中的折点的index

	private float X_start;// 纵坐标为0 的y轴de值

	private float x_move;//整体向右移动

	public LineChartView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		myAc = (Activity) context;
		init();
	}

	public LineChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		myAc = (Activity) context;
		init();
	}

	/*
	 * @param values 各个折点的值组成的数组
	 * 
	 * @param X_values x轴坐标对应的内容
	 * 
	 * @param Ymax y轴的最大值
	 * 
	 * @param Ymin y轴的最小值
	 * 
	 * @param Y_value_span y轴要显示坐标的数值间隔
	 */
	public LineChartView(Context context, float[] values, String[] X_values,
			float Ymax, float Ymin, int Y_value_span, float[] values_jh) {
		super(context);
		myAc = (Activity) context;
		this.values = values;
		this.X_values = X_values;
		this.Ymax = Ymax;
		this.Ymin = Ymin;
		this.Y_value_span = Y_value_span;
		this.values_jh = values_jh;
		x_move=50;
		init();
	}

	/*
	 * 初始化用到的值
	 */
	private void init() {
		// TODO Auto-generated method stub
		// 获取屏幕的宽和高
		WindowManager wm = myAc.getWindowManager();
		int height = wm.getDefaultDisplay().getHeight();
		int width = wm.getDefaultDisplay().getWidth();
		height_temp = height / 1600f;
		width_temp = width / 650f;
		pt_horizontallines = getHorizontallinesPoint();
		pt_verticallines = getVerticallinesPoint();
		pt_values = getValuePoint();
		pt_values_jh = getValuePoint_JH();
	}

	/*
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas) 绘制折线图
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		// 画折线

		Paint paint_linechart = new Paint();
		paint_linechart.setColor(Color.GREEN);
		paint_linechart.setStrokeWidth(0.0f);
		for (int i = 0; i < pt_values.length / 2 - 1; i++) {
			canvas.drawLine(pt_values[2 * i]+x_move, pt_values[1 + 2 * i],
					pt_values[2 + 2 * i]+x_move, pt_values[3 + 2 * i], paint_linechart);
		}
		Paint paint_linechart_jh = new Paint();
		paint_linechart_jh.setColor(Color.parseColor("#0080FC"));
		paint_linechart_jh.setStrokeWidth(0.0f);
		for (int i = 0; i < pt_values_jh.length / 2 - 1; i++) {
			canvas.drawLine(pt_values_jh[2 * i]+x_move, pt_values_jh[1 + 2 * i],
					pt_values_jh[2 + 2 * i]+x_move, pt_values_jh[3 + 2 * i],
					paint_linechart_jh);
		}
		// 画折点和值
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.point);
		Bitmap bitmap_jh = BitmapFactory.decodeResource(getResources(),
				R.drawable.pointjh);
		Paint paint_value = new Paint();
		paint_value.setColor(Color.rgb(220, 86, 0));

		paint_value.setTextSize(20 * height_temp);
		for (int i = 0; i < pt_values.length / 2; i++) {
			if (i == pt_values.length / 2 - 1) {
				Bitmap bitmap_blue = BitmapFactory.decodeResource(
						getResources(), R.drawable.point); // 横坐标最后一个点的背景图
				canvas.drawBitmap(bitmap_blue, pt_values[2 * i] - 13
						* width_temp+x_move, pt_values[2 * i + 1] - 13 * height_temp,
						paint_value);

				Paint paint4 = new Paint();
				paint4.setColor(Color.rgb(83, 133, 212));
				paint4.setTextSize(15 * height_temp);
				// canvas.drawText(String.valueOf(values[i]),
				// pt_values[2*i]-20*width_temp,pt_values[2*i+1]-20*height_temp,
				// paint4);
			} else if (i == 0) {
				canvas.drawBitmap(bitmap, pt_values[2 * i] - 8 * width_temp+x_move,
						pt_values[2 * i + 1] - 13 * height_temp, paint_value);
			} else {
				canvas.drawBitmap(bitmap, pt_values[2 * i] - 13 * width_temp+x_move,
						pt_values[2 * i + 1] - 13 * height_temp, paint_value);

				if (values[i] < values[i + 1] && values[i] < values[i - 1]) {
					// canvas.drawText(String.valueOf(values[i]),
					// pt_values[2*i]-20*width_temp,pt_values[2*i+1]+30*height_temp,
					// paint_value);
				} else if (values[i] > values[i + 1]
						&& values[i] > values[i - 1]) {
					// canvas.drawText(String.valueOf(values[i]),
					// pt_values[2*i]-20*width_temp,pt_values[2*i+1]-20*height_temp,
					// paint_value);
				} else {
					// canvas.drawText(String.valueOf(values[i]),
					// pt_values[2*i]+20*width_temp,pt_values[2*i+1],
					// paint_value);
				}
			}

		}
		for (int i = 0; i < pt_values_jh.length / 2; i++) {
			if (i == pt_values_jh.length / 2 - 1) {
				Bitmap bitmap_blue = BitmapFactory.decodeResource(
						getResources(), R.drawable.pointjh); // 横坐标最后一个点的背景图
				canvas.drawBitmap(bitmap_blue, pt_values_jh[2 * i] - 13
						* width_temp+x_move, pt_values_jh[2 * i + 1] - 13
						* height_temp, paint_value);

				Paint paint4 = new Paint();
				paint4.setColor(Color.rgb(83, 133, 212));
				paint4.setTextSize(15 * height_temp);
				// canvas.drawText(String.valueOf(values[i]),
				// pt_values[2*i]-20*width_temp,pt_values[2*i+1]-20*height_temp,
				// paint4);
			} else if (i == 0) {
				canvas.drawBitmap(bitmap_jh, pt_values_jh[2 * i] - 8
						* width_temp+x_move, pt_values_jh[2 * i + 1] - 13
						* height_temp, paint_value);
				// 为避免折线和数值重叠，通过判断设定文字位置
				// canvas.drawText(String.valueOf(values[i]),
				// pt_values[2*i]+20*width_temp,pt_values[2*i+1]+20*height_temp,
				// paint_value);
			} else {
				canvas.drawBitmap(bitmap_jh, pt_values_jh[2 * i] - 13
						* width_temp+x_move, pt_values_jh[2 * i + 1] - 13
						* height_temp, paint_value);

				if (values_jh[i] < values_jh[i + 1]
						&& values_jh[i] < values_jh[i - 1]) {
					// canvas.drawText(String.valueOf(values[i]),
					// pt_values[2*i]-20*width_temp,pt_values[2*i+1]+30*height_temp,
					// paint_value);
				} else if (values_jh[i] > values_jh[i + 1]
						&& values_jh[i] > values_jh[i - 1]) {
					// canvas.drawText(String.valueOf(values[i]),
					// pt_values[2*i]-20*width_temp,pt_values[2*i+1]-20*height_temp,
					// paint_value);
				} else {
					// canvas.drawText(String.valueOf(values[i]),
					// pt_values[2*i]+20*width_temp,pt_values[2*i+1],
					// paint_value);
				}
			}

		}

		// 编写纵坐标的值

		paint_value.setColor(Color.rgb(214, 201, 187));

		if ((int) (Ymax - Ymin) % Y_value_span == 0) {// 刚好整除间隔
			Y_value_num = (int) ((Ymax - Ymin) / Y_value_span + 1);
		} else {// 除不尽
			Y_value_num = (int) ((Ymax - Ymin) / Y_value_span + 2);
		}

		float height_span = (float) (552.0f * Y_value_span * height_temp / (Ymax - Ymin));// 整个图片的高度为552
		for (int i = 0; i < Y_value_num; i++) {
			if (i == Y_value_num - 1) {
				canvas.drawText(String.valueOf((int) (Ymax)),
						pt_verticallines[0] - 68 * height_temp,
						pt_verticallines[1] + 5 * width_temp, paint_value);
			} else {
				canvas.drawText(
						String.valueOf((int) (Ymin + Y_value_span * i)),
						pt_verticallines[2] - 68 * height_temp,
						pt_verticallines[3] - i * (height_span - 1),
						paint_value);
				if ((int) (Ymin + Y_value_span * i) == 0) {
					X_start = pt_verticallines[3] - i * (height_span - 1);
				}
			}
		}

		// 编写横坐标的值
		for (int i = 0; i < X_values.length; i++) {
			canvas.drawText(X_values[i], pt_verticallines[4 * i + 2] - 20
					* width_temp+(x_move*1.1f), X_start + 24 * height_temp, paint_value);
		}

		int num = values.length - 1;
		int numjh = values_jh.length - 1;

		//画y轴
		Paint paint_lineH = new Paint();
		paint_lineH.setColor(Color.WHITE);
		paint_lineH.setStrokeWidth(4.0f);
		canvas.drawLine(pt_values[2 + 2 * (-1)], pt_verticallines[4 * 0 + 3],
				pt_values[2 + 2 * (-1)],
				pt_verticallines[1] - 11 * height_temp, paint_lineH);
		//画x轴
		Paint paint_lineV = new Paint();
		paint_lineV.setColor(Color.WHITE);
		paint_lineV.setStrokeWidth(4.0f);
		canvas.drawLine((pt_values_jh[2 * numjh])*(float)(1.05), X_start - 4 * height_temp,
				pt_values_jh[2 + 2 * (-1)], X_start - 4 * height_temp,
				paint_lineV);

	}

	/*
	 * 获取横线的起始点坐标
	 */
	private float[] getHorizontallinesPoint() {
		// TODO Auto-generated method stub
		int count = 18;
		float num = 552 / (float) count;// 比如要有15个间隔。552是y轴的总长度

		float[] pt1 = new float[4 * (count + 1)];

		for (int i = 0; i < count + 1; i++) {
			pt1[i * 4] = width_temp * 74;
			pt1[4 * i + 1] = height_temp * ((float) (44.0f + (float) i * num));
			pt1[4 * i + 2] = width_temp * 596;
			pt1[4 * i + 3] = height_temp * ((float) (44.0f + (float) i * num));

		}
		return pt1;
	}

	/*
	 * 得到安排和完成的各个点的坐标
	 */
	private float[] getValuePoint() {
		// TODO Auto-generated method stub

		float[] pt = new float[values.length * 2];
		for (int i = 0; i < values.length; i++) {
			pt[2 * i] = pt_verticallines[2 + 4 * i];
			pt[2 * i + 1] = (float) ((float) ((Ymax - values[i]) * 552 / (Ymax - Ymin)) + 44)
					* height_temp;
		}
		return pt;
	}

	/*
	 * 得到计划的各个点的坐标
	 */
	private float[] getValuePoint_JH() {
		// TODO Auto-generated method stub

		float[] pt = new float[values_jh.length * 2];
		for (int i = 0; i < values_jh.length; i++) {
			pt[2 * i] = pt_verticallines[2 + 4 * i];
			pt[2 * i + 1] = (float) ((float) ((Ymax - values_jh[i]) * 552 / (Ymax - Ymin)) + 44)
					* height_temp;
		}
		return pt;
	}

	/*
	 * 得到竖线的起始坐标
	 */
	private float[] getVerticallinesPoint() {
		// TODO Auto-generated method stub
		// float[] pts=new float[]{
		// width_temp*74,height_temp*44,width_temp*74,height_temp*596,
		// width_temp*161,height_temp*44,width_temp*161,height_temp*596,
		// width_temp*248,height_temp*44,width_temp*248,height_temp*596,
		// width_temp*335,height_temp*44,width_temp*335,height_temp*596,
		// width_temp*422,height_temp*44,width_temp*422,height_temp*596,
		// width_temp*509,height_temp*44,width_temp*509,height_temp*596,
		// width_temp*596,height_temp*44,width_temp*596,height_temp*596
		//
		// }; //数据

		float x_span = (float) (522.0f / (X_values.length - 1));
		float[] pts = new float[X_values.length * 4];
		for (int i = 0; i < X_values.length; i++) {
			pts[4 * i] = width_temp * (74 + x_span * i);
			pts[4 * i + 1] = height_temp * 44;
			pts[4 * i + 2] = width_temp * (74 + x_span * i);
			pts[4 * i + 3] = height_temp * 596;
		}

		return pts;
	}

	public void setValueOnClickListener(OnClickListener onClickListener) {
		// getParent().requestDisallowInterceptTouchEvent(true);
		this.valueOnClickListener = onClickListener;

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
//		getParent().requestDisallowInterceptTouchEvent(true);
		// System.out.println("x=="+x+"y=="+y);
		valueIndex = getTouchValueIndex(event);

		valueOnClickListener.onClick(this);
		Point lastEventPoint;
		Point eventPoint = new Point((int) event.getX(), (int) event.getY());
		// System.out.println("x=="+eventPoint.x+"y=="+eventPoint.y);
		float DownX = 0;
		float DownY = 0;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			DownX = event.getX();// float DownX
			DownY = event.getY();// float DownY
			// currentMS = System.currentTimeMillis();//long currentMS 获取系统时间
			break;
		case MotionEvent.ACTION_MOVE:
			float moveX = event.getX() - DownX;// X轴距离
			float moveY = event.getY() - DownY;// y轴距离
			// System.out.println("moveX=="+moveX+"moveY=="+moveY);
			// long moveTime = System.currentTimeMillis() - currentMS;//移动时间
			break;
		case MotionEvent.ACTION_UP:
			break;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	/*
	 * 得到点击的折点的index
	 */
	private int getTouchValueIndex(MotionEvent event) {
		// TODO Auto-generated method stub
		for (int i = 0; i < values.length; i++) {
			float x = event.getX();
			float y = event.getY();
			float diffX = x - pt_values[2 * i];
			float diffY = y - pt_values[2 * i + 1];
			double z = Math.hypot(Math.abs(diffX), Math.abs(diffY)); // 求直角三角形斜边的长度
			if (z <= 50) {
				return i;
			}
		}
		return -1;

	}

	public int getValueIndex() {
		return valueIndex;
	}

}
