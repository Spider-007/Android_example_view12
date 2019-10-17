package com.htmitech.ztcustom.zt.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.domain.DetectionValue;
import com.htmitech.ztcustom.zt.interfaces.CallBackDetectionOnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * ==================================================
 *
 * @author Tony                                     |
 *         |
 *         扇形自定义			加动画						|
 *         |
 *         ==================================================
 */
public class CircleChartView extends View implements Runnable {

    private List<DetectionValue> numbers;
    private List<Point> points;
    private double total;
    private RectF normalOval;
    private RectF selectOval;
    private Paint paint;
    private Context context;
    private //画笔初始化
            Paint PaintArc;
    private static final int DIMETER = 440;
    public int height;
    public float offer = 3.7f;
    private static int SLEEPTIME = 1;
    private int select = -1; //是否弹出
    private int index = 0;
    private ArrayList<Float> startList;
    private ArrayList<Float> sweeList;
    private java.text.DecimalFormat myformat;
    private volatile boolean isStop = false;
    private CallBackDetectionOnClick mCallBackDetectionOnClick;

    /**
     * 表示表示是否是第一进入
     */
    private boolean isFrist = false;

    float startAngle = 0;
    float sweepAngle = 0;

    public CircleChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        numbers = new ArrayList<DetectionValue>();

        normalOval = new RectF();
        selectOval = new RectF();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);

        myformat = new java.text.DecimalFormat("#.00");

        PaintArc = new Paint();
        PaintArc.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST
                || heightMode == MeasureSpec.AT_MOST) {
            width = DIMETER;
            height = DIMETER;
        } else if (widthMode == MeasureSpec.EXACTLY) {//精准尺寸
            if (height > width) {
                height = width;
                this.height = height;
            } else {
                width = height;
                this.height = height;
            }
        }
        setMeasuredDimension(width, height);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        normalOval.left = (float) (getMeasuredWidth() * 0.1);
        normalOval.top = (float) (getMeasuredHeight() * 0.1);
        normalOval.right = (float) (getMeasuredWidth() * 0.9);
        normalOval.bottom = (float) (getMeasuredHeight() * 0.9);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float cirX = getMeasuredWidth() / 2;
        float cirY = getMeasuredHeight() / 2;

        if (isFrist) {  //  初始化进入 动画效果

            try {
                for (int i = 0; i < index; i++) {
                    paint.setColor(Color.parseColor("#" + numbers.get(i).color));
                    canvas.drawArc(normalOval, startList.get(i), sweeList.get(i), true,
                            paint);

                }
                /**
                 * 顺序很重要
                 */
                paint.setColor(Color.parseColor("#" + numbers.get(index).color));
                canvas.drawArc(normalOval, startAngle, sweepAngle, true,
                        paint);
                //画圆心
                PaintArc.setColor(context.getResources().getColor(R.color.railway_back));
                canvas.drawCircle(cirX, cirY, height / offer, PaintArc);

                return;
            } catch (Exception e) {

            }

        }

        /**
         * 一下是点击的时候操作走的变化
         */
        if (!numbers.isEmpty()) {
            startAngle = 0;
            sweepAngle = 0;
            for (int i = 0; i < numbers.size(); i++) {
                if (i == numbers.size() - 1) {
                    sweepAngle = 360 - startAngle;
                } else {
                    sweepAngle = (float) (numbers.get(i).value * 1.0f / total * 360);
                }
                if (select >= 0 && i == select) {
                    selectOval.left = (float) (getMeasuredWidth() * 0.1);
                    selectOval.top = (float) (getMeasuredHeight() * 0.1);
                    selectOval.right = (float) (getMeasuredWidth() * 0.9);
                    selectOval.bottom = (float) (getMeasuredHeight() * 0.9);
                    Point point = points.get(select);
                    int middle = (point.x + point.y) / 2;
                    if (middle <= 90) {
                        int top = (int) (Math.sin(Math.toRadians(middle)) * 15);
                        int left = (int) (Math.cos(Math.toRadians(middle)) * 15);
                        selectOval.left += left;
                        selectOval.right += left;
                        selectOval.top += top;
                        selectOval.bottom += top;
                    }
                    if (middle > 90 && middle <= 180) {
                        middle = 180 - middle;
                        int top = (int) (Math.sin(Math.toRadians(middle)) * 15);
                        int left = (int) (Math.cos(Math.toRadians(middle)) * 15);
                        selectOval.left -= left;
                        selectOval.right -= left;
                        selectOval.top += top;
                        selectOval.bottom += top;
                    }
                    if (middle > 180 && middle <= 270) {
                        middle = 270 - middle;
                        int left = (int) (Math.sin(Math.toRadians(middle)) * 15);
                        int top = (int) (Math.cos(Math.toRadians(middle)) * 15);
                        selectOval.left -= left;
                        selectOval.right -= left;
                        selectOval.top -= top;
                        selectOval.bottom -= top;
                    }
                    if (middle > 270 && middle <= 360) {
                        middle = 360 - middle;
                        int top = (int) (Math.sin(Math.toRadians(middle)) * 15);
                        int left = (int) (Math.cos(Math.toRadians(middle)) * 15);
                        selectOval.left += left;
                        selectOval.right += left;
                        selectOval.top -= top;
                        selectOval.bottom -= top;
                    }
                    paint.setColor(Color.parseColor("#" + numbers.get(i).color));
                    canvas.drawArc(selectOval, startAngle, sweepAngle, true,
                            paint);
                } else {
                    paint.setColor(Color.parseColor("#" + numbers.get(i).color));
                    canvas.drawArc(normalOval, startAngle, sweepAngle, true,
                            paint);
                }
                points.get(i).x = (int) startAngle;
                points.get(i).y = (int) (startAngle + sweepAngle);
                startAngle += sweepAngle;
            }

            //画圆心
            PaintArc.setColor(context.getResources().getColor(R.color.railway_back));
            canvas.drawCircle(cirX, cirY, height / offer, PaintArc);
        }


    }


    public void setNumbers(List<DetectionValue> numbers) {
        total = 0;
        index = 0;
        isFrist = true;
        select = -1;
        this.numbers = new ArrayList<DetectionValue>();
        this.numbers.clear();
        startAngle = 0;
        sweepAngle = 0;
        this.numbers.addAll(numbers);
        points = new ArrayList<Point>();
        for (DetectionValue item : numbers) {
            total += item.value;
            Point point = new Point();
            points.add(point);
        }
        if (mCallBackDetectionOnClick != null)
            mCallBackDetectionOnClick.onTotal(total);
        new Thread(this).start();

    }


    /**
     * 点击回调请求
     *
     * @param mCallBackDetectionOnClick
     */
    public void setCallBackDetectionOnClick(CallBackDetectionOnClick mCallBackDetectionOnClick) {
        this.mCallBackDetectionOnClick = mCallBackDetectionOnClick;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            int radius = 0;
            // 第一象限
            if (x >= getMeasuredWidth() / 2 && y >= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((y - getMeasuredHeight() / 2) * 1.0f
                        / (x - getMeasuredWidth() / 2)) * 180 / Math.PI);
            }
            // 第二象限
            if (x <= getMeasuredWidth() / 2 && y >= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((getMeasuredWidth() / 2 - x)
                        / (y - getMeasuredHeight() / 2))
                        * 180 / Math.PI + 90);
            }
            // 第三象限
            if (x <= getMeasuredWidth() / 2 && y <= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((getMeasuredHeight() / 2 - y)
                        / (getMeasuredWidth() / 2 - x))
                        * 180 / Math.PI + 180);
            }
            // 第四象限
            if (x >= getMeasuredWidth() / 2 && y <= getMeasuredHeight() / 2) {
                radius = (int) (Math.atan((x - getMeasuredWidth() / 2)
                        / (getMeasuredHeight() / 2 - y))
                        * 180 / Math.PI + 270);
            }
            if (points != null) {
                for (int i = 0; i < points.size(); i++) {
                    Point point = points.get(i);
                    if (point.x <= radius && point.y >= radius) {
                        if (select == i) {
                            select = -1;
                        } else {
                            select = i;
                        }
                        DetectionValue mDetectionValue = null;
                        if (select != -1) {
                            mDetectionValue = this.numbers.get(select);
                        }
                        mCallBackDetectionOnClick.onClick(select == -1 ? false : true, mDetectionValue);
                        invalidate();
                        return true;
                    }
                }
            }
            return true;
        }
        return super.onTouchEvent(event);
    }


    @Override
    public void run() {
        runRound();
    }

    /**
     * 添加动画
     */
    public void runRound() {

        startList = new ArrayList<Float>();
        sweeList = new ArrayList<Float>();
        boolean jStop;
        for (int i = 0; i < numbers.size(); i++) {
            if (isStop) {
                return;
            }
            {
                sweepAngle = 0;
                float count = (float) (numbers.get(i).value / 100f);
                String str = myformat.format(count);
                count = Float.parseFloat(str);
                jStop = true;
                for (float j = 1; j <= numbers.get(i).value; ) {

                    if (isStop) {
                        return;
                    }

                    sweepAngle = (float) (j * 1.0f / total * 360);
                    this.index = i;

                    postInvalidate();

                    try {
                        Thread.sleep(SLEEPTIME);
                    } catch (InterruptedException localInterruptedException) {
                        localInterruptedException.printStackTrace();
                    }
                    /**
                     * 控制速度
                     */


//					if(numbers.get(i).value < 100){
//						count = 1;
//					}


                    j = j + count;
                    /**
                     * 防止超出
                     */
                    if (j > numbers.get(i).value && jStop) {
                        j = (float) numbers.get(i).value;
                        jStop = false;
                    }
                }

            }

            /**
             * startList 必须得放在上面 因为这个是开始
             */

            startList.add(startAngle);
            points.get(i).x = (int) startAngle;
            if (numbers.get(i).value != 0) {
                points.get(i).y = (int) (startAngle + sweepAngle);
                startAngle += sweepAngle;
            } else {
                points.get(i).y = (int) startAngle;
            }

            /**
             * sweeList 必须得放在下面 因为这个是结束
             */
            sweeList.add(sweepAngle);
        }


        isFrist = false;

    }

    /**
     * 外部点击将当前处于展示状态
     *
     * @param code
     */
    public void setOntouch(String code) {

        for (int i = 0; i < numbers.size(); i++) {

            if (numbers.get(i).code.equals(code)) {

                if (select == i) {
                    select = -1;
                } else {
                    select = i;
                }
                DetectionValue mDetectionValue = null;
                if (select != -1) {
                    mDetectionValue = this.numbers.get(select);
                }
                mCallBackDetectionOnClick.onClick(select == -1 ? false : true, mDetectionValue);
                invalidate();
                return;
            }

        }
    }

    /**
     * 获取环形是否点击展开
     *
     * @return
     */
    public boolean getCircleShow() {
        return select != -1;
    }

    /**
     * 获取当前展示的ID
     *
     * @return
     */
    public String getCircleShowId() {
        return select == -1 ? select + "" : numbers.get(select).code;
    }

    /***
     * 动画停止
     */
    public void stop() {
        this.isStop = true;
    }

    /**
     * 动画开始
     */
    public void start() {
        this.isStop = false;
    }

}
