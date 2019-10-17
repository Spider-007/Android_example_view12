package com.htmitech.MyView.barline;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;


import com.htmitech.MyView.barline.bean.BarStyle;
import com.htmitech.MyView.barline.bean.LineStyle;
import com.htmitech.MyView.barline.bean.Root;
import com.htmitech.api.BookInit;
import com.htmitech.emportal.R;
import com.htmitech.unit.ImageUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义折线图
 * Created by xiaoyunfei on 16/11/29.
 */
public class ChartView extends View {
    Context context;
    //xy坐标轴颜色
    private int xylinecolor = 0xffe2e2e2;
    //xy坐标轴宽度
    private int xylinewidth = dpToPx(1);
    //xy坐标轴文字颜色
    private int xytextcolor = 0xff7e7e7e;
    //xy坐标轴文字大小
    private int xytextsize = spToPx(12);
    //折线图中折线的颜色
    private int linecolor = 0xff02bbb7;
    //x轴各个坐标点水平间距
    private int interval = dpToPx(50);
    //背景颜色
    private int bgcolor = 0xffffffff;
    //是否在ACTION_UP时，根据速度进行自滑动，没有要求，建议关闭，过于占用GPU
    private boolean isScroll = false;
    //绘制XY轴坐标对应的画笔
    private Paint xyPaint;
    //绘制XY轴的文本对应的画笔
    private Paint xyTextPaint;
    //画折线对应的画笔
    private Paint linePaint;
    private int width;
    private int height;
    //x轴的原点坐标
    private float xOri;
    //y轴的原点坐标
    private float yOri;
    //第一个点X的坐标
    private float xInit;
    //第一个点对应的最大Y坐标
    private float maxXInit;
    //第一个点对应的最小X坐标
    private float minXInit;
    //x轴坐标对应的数据
    private List<String> xValue = new ArrayList<String>();
    //y轴坐标对应的数据
    private List<Float> yValue = new ArrayList<Float>();
    //折线对应的数据
    private List<Map<String, Float>> value = new ArrayList<Map<String, Float>>();
    //柱图
    private List<Map<String, Map<String, Object>>> barInfoList = new ArrayList<Map<String, Map<String, Object>>>();
    //点击的点对应的X轴的第几个点，默认1
    private int selectIndex = 1;
    //X轴刻度文本对应的最大矩形，为了选中时，在x轴文本画的框框大小一致
    private Rect xValueRect;
    //速度检测器
    private VelocityTracker velocityTracker;
    //x轴的y坐标
    private float xStartPoint;
    //y轴坐标最小点的y坐标
    private float minYPoint;
    //y轴坐标最大点的y坐标
    private float maxYPoint;
    //选中的点的颜色
    private int selectPointColor = 0xff02bbb7;
    //是否显示被选中的点
    private boolean isShowSelectPoint = false;
    //被选中的点显示字体的颜色
    private int selectPointTextColor = Color.WHITE;
    //被选中的点显示字体的大小
    private int selectPointTextSize = spToPx(14);
    //节点的颜色
    private int linePointColor = Color.WHITE;
    //是否显示水印
    private boolean isShowWater = true;
    //柱状图的宽
    private float barWidht = dpToPx(20);
    //柱的画笔
    private Paint barPaint;
    //标题
    private String titleString = "标题";

    private Root root;
    private String showWaterText = "";
    private OnBarLineClickListener cliclListener;

    public ChartView(Context context) {
        this(context, null);
        this.context = context;
    }

    public ChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public ChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs, defStyleAttr);
        initPaint();
    }

    /**
     * 初始化畫筆
     */
    private void initPaint() {
        xyPaint = new Paint();
        xyPaint.setAntiAlias(true);
        xyPaint.setStrokeWidth(xylinewidth);
        xyPaint.setStrokeCap(Paint.Cap.ROUND);
        xyPaint.setColor(xylinecolor);

        xyTextPaint = new Paint();
        xyTextPaint.setAntiAlias(true);
        xyTextPaint.setTextSize(xytextsize);
        xyTextPaint.setStrokeCap(Paint.Cap.ROUND);
        xyTextPaint.setColor(xytextcolor);
        xyTextPaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(xylinewidth);
        linePaint.setStrokeCap(Paint.Cap.ROUND);
        linePaint.setColor(linecolor);
        linePaint.setStyle(Paint.Style.STROKE);

        barPaint = new Paint();
        barPaint.setAntiAlias(true);
        barPaint.setStyle(Paint.Style.STROKE);
//        barPaint.setStrokeWidth(xylinewidth);
//        barPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.chartView, defStyleAttr, 0);
        int count = array.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.chartView_xylinecolor://xy坐标轴颜色
                    xylinecolor = array.getColor(attr, xylinecolor);
                    break;
                case R.styleable.chartView_xylinewidth://xy坐标轴宽度
                    xylinewidth = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, xylinewidth, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.chartView_xytextcolor://xy坐标轴文字颜色
                    xytextcolor = array.getColor(attr, xytextcolor);
                    break;
                case R.styleable.chartView_xytextsize://xy坐标轴文字大小
                    xytextsize = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, xytextsize, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.chartView_linecolor://折线图中折线的颜色
                    linecolor = array.getColor(attr, linecolor);
                    break;
                case R.styleable.chartView_interval://x轴各个坐标点水平间距
                    interval = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, interval, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.chartView_bgcolor: //背景颜色
                    bgcolor = array.getColor(attr, bgcolor);
                    break;
                case R.styleable.chartView_isScroll://是否在ACTION_UP时，根据速度进行自滑动
                    isScroll = array.getBoolean(attr, isScroll);
                    break;
                case R.styleable.chartView_selectPointColor://选中的点的颜色
                    selectPointColor = array.getColor(attr, selectPointColor);
                    break;
                case R.styleable.chartView_isShowSelectPoint://是否显示被选中的点
                    isShowSelectPoint = array.getBoolean(attr, isShowSelectPoint);
                    break;
                case R.styleable.chartView_selectPointTextColor://被选中的点显示的字体的颜色
                    selectPointTextColor = array.getColor(attr, selectPointTextColor);
                    break;
                case R.styleable.chartView_selectPointTextSize://被选中的点显示的字体的大小
                    selectPointTextSize = (int) array.getDimension(attr, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, selectPointTextSize, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.chartView_linePointColor:
                    linePointColor = array.getColor(attr, linePointColor);
                    break;
            }
        }
        array.recycle();

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            //这里需要确定几个基本点，只有确定了xy轴原点坐标，第一个点的X坐标值及其最大最小值
            width = getWidth();
            height = getHeight();
            //x轴上各个格之间的间距
            interval = width / 6;
            //Y轴文本最大宽度
            float textYWdith = getTextBounds("000", xyTextPaint).width();
            for (int i = 0; i < yValue.size(); i++) {//求取y轴文本最大的宽度
                float temp = getTextBounds(yValue.get(i) + "", xyTextPaint).width();
                if (temp > textYWdith)
                    textYWdith = temp;
            }
            int dp2 = dpToPx(2);
            int dp3 = dpToPx(3);
            xOri = (int) (dp2 + textYWdith + dp2 + xylinewidth);//dp2是y轴文本距离左边，以及距离y轴的距离
//            //X轴文本最大高度
            xValueRect = getTextBounds("000", xyTextPaint);
            float textXHeight = xValueRect.height();
            for (int i = 0; i < xValue.size(); i++) {//求取x轴文本最大的高度
                Rect rect = getTextBounds(xValue.get(i) + "", xyTextPaint);
                if (rect.height() > textXHeight)
                    textXHeight = rect.height();
                if (rect.width() > xValueRect.width())
                    xValueRect = rect;
            }
            yOri = (int) (height - dp2 - textXHeight - dp3 - xylinewidth);//dp3是x轴文本距离底边，dp2是x轴文本距离x轴的距离
            xInit = interval + xOri;
            minXInit = width - (width - xOri) * 0.1f - interval * (xValue.size() - 1);//减去0.1f是因为最后一个X周刻度距离右边的长度为X轴可见长度的10%
            maxXInit = xInit;
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        canvas.drawColor(bgcolor);
        drawXY(canvas);
        drawBrokenLineAndPoint(canvas);
//        drawTitle(canvas);
        drawWater(canvas);
    }

    /**
     * 画水印
     *
     * @param canvas
     */
    private void drawWater(Canvas canvas) {
        if (isShowWater) {
            RelativeLayout mRelativeLayout = (RelativeLayout) getParent();

            mRelativeLayout.setBackground(ImageUtil.drawTextToBitmap(getContext(), showWaterText));
//            WaterMarkTextUtil.setWaterMarkTextBg(mRelativeLayout, getContext(), showWaterText, 1, null);
        }
    }

    /**
     * 绘制折线和折线交点处对应的点
     *
     * @param canvas
     */
    private void drawBrokenLineAndPoint(Canvas canvas) {
        if (xValue.size() <= 0)
            return;
        //重新开一个图层
        int layerId = canvas.saveLayer(0, 0, width, height, null, Canvas.ALL_SAVE_FLAG);
        drawBrokenPoint(canvas);
        drawBrokenLine(canvas);

        // 将折线超出x轴坐标的部分截取掉
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(bgcolor);
        linePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        RectF rectF = new RectF(0, 0, xOri, height);
        canvas.drawRect(rectF, linePaint);
        linePaint.setXfermode(null);
        //保存图层
        canvas.restoreToCount(layerId);
    }

    /**
     * 绘制折线对应的点
     *
     * @param canvas
     */
    private void drawBrokenPoint(Canvas canvas) {
        float dp2 = dpToPx(2);
        float dp4 = dpToPx(4);
        float dp7 = dpToPx(7);
        //绘制节点对应的原点
        List<BarStyle> barStyle = root.Result.barStyle;
        List<LineStyle> lineStyle = root.Result.lineStyle;
        int barCount = 0;//柱的个数
        for (int m = 0; m < value.size(); m++) {
            if (value.get(m).get("type") == 0) {
                barCount++;
            }
        }
        for (int j = 0; j < value.size(); j++) {
            Map<String, Float> value = this.value.get(j);
            //设置柱  和线的画笔样式
            if (value.get("type") == 0) {
                //设置画柱的画笔的样式
                float id = value.get("id");
                for (int bar = 0; bar < barStyle.size(); bar++) {
                    if (id == barStyle.get(bar).id) {
                        barPaint.setColor(Color.parseColor(barStyle.get(bar).color));
                        barPaint.setStyle(Paint.Style.FILL);
                        barPaint.setAlpha(200);
                        break;
                    }
                }
            } else if (value.get("type") == 1) {
                //设置画柱的画笔的样式
                float id = value.get("id");
                for (int line = 0; line < lineStyle.size(); line++) {
                    if (id == lineStyle.get(line).id) {
//                        linePaint.setColor(Color.parseColor(lineStyle.get(line).color));
                        selectPointColor = Color.parseColor(lineStyle.get(line).color);
                        linePointColor = Color.parseColor(lineStyle.get(line).color);
                        linePaint.setStyle(Paint.Style.FILL);
                        break;
                    }
                }
            }
            Map<String, Map<String, Object>> mapKey = new HashMap<String, Map<String, Object>>();
            //-----------------
            for (int i = 0; i < xValue.size(); i++) {
                //得到坐标
                float x = xInit + interval * i;
                float y = 0;
                if (xValue.size() > 0 && yValue.size() > 0 && value.get(xValue.get(i)) >= 0) {
                    y = xStartPoint - (xStartPoint - maxYPoint) * value.get(xValue.get(i)) / yValue.get(yValue.size() - 1);
                } else if (xValue.size() > 0 && yValue.size() > 0 && value.get(xValue.get(i)) < 0) {
                    y = xStartPoint + (minYPoint - xStartPoint) * value.get(xValue.get(i)) / yValue.get(0);
                }
                //-----------画柱--------------
                if (value.get("type") == 0) {
                    if (barCount == 1) {
                        barWidht = interval / 3f * 2f / 2f;//得到每个柱的宽度  用x轴上每个刻度之间的距离除以3  再乘2 得到所有柱的宽度 如果是一个住就除以2
                    } else if (barCount > 1) {
                        barWidht = interval / 3f * 2f / barCount;//得到每个柱的宽度  用x轴上每个刻度之间的距离除以3  再乘2 得到所有柱的宽度 如果是大于1个柱就除以柱的个数
                    }
                    float allBarWidth = barWidht * barCount;//所有柱的宽度和
                    float barstartwidht = x - allBarWidth / 2f;//当前这组数据柱的开始x
                    float left = 0, top = 0, right = 0, bottom = 0;
                    if (value.get(xValue.get(i)) >= 0) {
                        top = y;
                        bottom = xStartPoint;
                        left = barstartwidht + barWidht * j;
                        right = left + barWidht;
                    } else if (value.get(xValue.get(i)) < 0) {
                        top = xStartPoint;
                        bottom = y;
                        left = barstartwidht + barWidht * j;
                        right = left + barWidht;
                    }
                    Map<String, Object> oneBarMap = new HashMap<String, Object>();
                    oneBarMap.put("right", right);
                    oneBarMap.put("left", left);
                    oneBarMap.put("top", top);
                    oneBarMap.put("bottom", bottom);
                    oneBarMap.put("id", value.get("id"));
                    mapKey.put(xValue.get(i), oneBarMap);
                    canvas.drawRect(left, top, right, bottom, barPaint);
                    //----------------------以下是画折线的点-------------------------
                } else if (value.get("type") == 1) {

//                    linePointColor = Color.parseColor(root.Result.lineStyle.get(0).color);
//                    selectPointColor = Color.parseColor(root.Result.lineStyle.get(0).color);

                    //绘制选中的点
                    if (i == selectIndex - 1 && isShowSelectPoint) {
                        linePaint.setColor(selectPointColor);
                        canvas.drawCircle(x, y, dp7, linePaint);
                        linePaint.setColor(selectPointColor);
                        canvas.drawCircle(x, y, dp4, linePaint);
                        drawFloatTextBox(canvas, x, y - dp7, value.get(xValue.get(i)));
                    }
                    //绘制普通的节点
                    linePaint.setColor(linePointColor);
                    canvas.drawCircle(x, y, dp4, linePaint);
//            linePaint.setStyle(Paint.Style.STROKE);
//            linePaint.setColor(linecolor);
//            canvas.drawCircle(x, y, dp2, linePaint);
                }
            }
            if (value.get("type") == 0)
                barInfoList.add(mapKey);
        }
    }

    /**
     * 绘制显示Y值的浮动框
     *
     * @param canvas
     * @param x
     * @param y
     * @param text
     */

    private void drawFloatTextBox(Canvas canvas, float x, float y, float text) {
        int dp6 = dpToPx(6);
        int dp18 = dpToPx(18);
        //p1
        Path path = new Path();
        path.moveTo(x, y);
        //p2
        path.lineTo(x - dp6, y - dp6);
        //p3
        path.lineTo(x - dp18, y - dp6);
        //p4
        path.lineTo(x - dp18, y - dp6 - dp18);
        //p5
        path.lineTo(x + dp18, y - dp6 - dp18);
        //p6
        path.lineTo(x + dp18, y - dp6);
        //p7
        path.lineTo(x + dp6, y - dp6);
        //p1
        path.lineTo(x, y);
        canvas.drawPath(path, linePaint);
        linePaint.setColor(selectPointTextColor);
        linePaint.setTextSize(selectPointTextSize);
        Rect rect = getTextBounds(text + "", linePaint);
        canvas.drawText(text + "", x - rect.width() / 2, y - dp6 - (dp18 - rect.height()) / 2, linePaint);
    }

    /**
     * 绘制折线
     *
     * @param canvas
     */
    private void drawBrokenLine(Canvas canvas) {

        //绘制折线
        Path path = new Path();
        List<LineStyle> lineStyle = root.Result.lineStyle;
        for (int j = 0; j < value.size(); j++) {
            Map<String, Float> value = this.value.get(j);
            if (value.get("type") == 1) {
                float id = value.get("id");
                for (int line = 0; line < lineStyle.size(); line++) {
                    if (id == lineStyle.get(line).id) {
                        linecolor = Color.parseColor(lineStyle.get(line).color);
                        linePaint.setColor(linecolor);
                        linePaint.setStyle(Paint.Style.STROKE);
                        break;
                    }
                }
                //得到x y坐标
                float x = xInit + interval * 0;
                float y = 0;
                if (xValue.size() > 0 && yValue.size() > 0 && value.get(xValue.get(0)) >= 0) {
                    y = xStartPoint - (xStartPoint - maxYPoint) * value.get(xValue.get(0)) / yValue.get(yValue.size() - 1);
                } else if (xValue.size() > 0 && yValue.size() > 0 && value.get(xValue.get(0)) < 0) {
                    y = xStartPoint + (minYPoint - xStartPoint) * value.get(xValue.get(0)) / yValue.get(0);
                }
                path.moveTo(x, y);

                for (int i = 1; i < xValue.size(); i++) {
                    x = xInit + interval * i;
                    if (xValue.size() > 0 && yValue.size() > 0 && value.get(xValue.get(i)) >= 0) {
                        y = xStartPoint - (xStartPoint - maxYPoint) * value.get(xValue.get(i)) / yValue.get(yValue.size() - 1);
                    } else if (xValue.size() > 0 && yValue.size() > 0 && value.get(xValue.get(i)) < 0) {
                        y = xStartPoint + (minYPoint - xStartPoint) * value.get(xValue.get(i)) / yValue.get(0);
                    }
                    path.lineTo(x, y);
                }
                canvas.drawPath(path, linePaint);
            }
        }
    }

    /**
     * 绘制XY坐标
     *
     * @param canvas
     */
    private void drawXY(Canvas canvas) {
        xStartPoint = yOri + xylinewidth / 2;
        int length = dpToPx(4);//刻度的长度
        Path path = new Path();
        xyPaint.setColor(Color.parseColor(root.Result.yAxis.axisLine.color));
        //绘制Y坐标
        if (root.Result.yAxis.axisLine.show) {
            canvas.drawLine(xOri - xylinewidth / 2, 0, xOri - xylinewidth / 2, yOri, xyPaint);
            //绘制y轴箭头
            xyPaint.setStyle(Paint.Style.STROKE);
            path.moveTo(xOri - xylinewidth / 2 - dpToPx(5), dpToPx(12));
            path.lineTo(xOri - xylinewidth / 2, xylinewidth / 2);
            path.lineTo(xOri - xylinewidth / 2 + dpToPx(5), dpToPx(12));
            canvas.drawPath(path, xyPaint);
        }
        //绘制y轴刻度
        int yLength = (int) (yOri * (1 - 0.1f) / (yValue.size() - 1));//y轴上面空出10%,计算出y轴刻度间距
        xytextcolor = Color.parseColor(root.Result.yAxis.axisText.color);
        xyTextPaint.setTextSize(spToPx(root.Result.yAxis.axisText.fontSize));
        Paint gridPaint = new Paint();
        if (root.Result.grid.xshow) {
            gridPaint.setAntiAlias(true);
            gridPaint.setStrokeCap(Paint.Cap.ROUND);
            gridPaint.setColor(Color.parseColor(root.Result.grid.borderColor));
            gridPaint.setStyle(Paint.Style.STROKE);
            gridPaint.setStrokeWidth(root.Result.grid.borderWidth);
            if (root.Result.grid.borderStyle.equals("dotted")) {
                PathEffect effects = new DashPathEffect(new float[]{1, 1}, 1);//设置为虚线
                gridPaint.setPathEffect(effects);
            }
        }
        for (int i = 0; i < yValue.size(); i++) {
            //绘制Y轴刻度
            if (root.Result.yAxis.axisLine.show) {
                canvas.drawLine(xOri, yOri - yLength * i + xylinewidth / 2, xOri + length, yOri - yLength * i + xylinewidth / 2, xyPaint);
            }
            xyTextPaint.setColor(xytextcolor);
            //绘制Y轴文本
            String text = yValue.get(i) + "";
            Rect rect = getTextBounds(text, xyTextPaint);
            if (root.Result.yAxis.axisText.show)
                canvas.drawText(text, 0, text.length(), xOri - xylinewidth - dpToPx(2) - rect.width(), yOri - yLength * i + rect.height() / 2, xyTextPaint);
            //画实线 虚线
            if (root.Result.grid.xshow) {
                canvas.drawLine(xOri, xStartPoint, width, xStartPoint, gridPaint);
            }
            if (yValue.get(i) == 0) {
                xStartPoint = yOri - yLength * i + xylinewidth / 2;
            }
            if (i == 0) {
                minYPoint = yOri - yLength * i + xylinewidth / 2;
            } else if (i == yValue.size() - 1) {
                maxYPoint = yOri - yLength * i + xylinewidth / 2;
            }
        }

        //绘制X轴坐标
//        canvas.drawLine(xOri, yOri + xylinewidth / 2, width, yOri + xylinewidth / 2, xyPaint);
        if (root.Result.xAxis.axisLine.show) {
            xyPaint.setColor(Color.parseColor(root.Result.xAxis.axisLine.color));
            canvas.drawLine(xOri, xStartPoint, width, xStartPoint, xyPaint);
            //绘制x轴箭头
            xyPaint.setStyle(Paint.Style.STROKE);
            path = new Path();
            //整个X轴的长度
            float xLength = xInit + interval * (xValue.size() - 1) + (width - xOri) * 0.1f;
            if (xLength < width)
                xLength = width;
            path.moveTo(xLength - dpToPx(12), xStartPoint - dpToPx(5));
            path.lineTo(xLength - xylinewidth / 2, xStartPoint);
            path.lineTo(xLength - dpToPx(12), xStartPoint + dpToPx(5));
            canvas.drawPath(path, xyPaint);
        }
        xytextcolor = Color.parseColor(root.Result.xAxis.axisText.color);
        //绘制x轴刻度
        for (int i = 0; i < xValue.size(); i++) {
            float x = xInit + interval * i;
            if (x >= xOri) {//只绘制从原点开始的区域
                xyTextPaint.setColor(xylinecolor);
                if (root.Result.xAxis.axisLine.show)
                    canvas.drawLine(x, xStartPoint, x, xStartPoint - length, xyPaint);
                //绘制X轴文本
                String text = xValue.get(i);
                xyTextPaint.setTextSize(spToPx(root.Result.xAxis.axisText.fontSize));
                Rect rect = getTextBounds(text, xyTextPaint);
                if (root.Result.xAxis.axisText.show) {
                    xyTextPaint.setColor(xytextcolor);
//                    if (i == selectIndex - 1) {
//                        canvas.drawText(text, 0, text.length(), x - rect.width() / 2, xStartPoint + xylinewidth + dpToPx(2) + rect.height(), xyTextPaint);
////                        canvas.drawRoundRect(x - xValueRect.width() / 2 - dpToPx(3), xStartPoint + xylinewidth + dpToPx(1), x + xValueRect.width() / 2 + dpToPx(3), xStartPoint + xylinewidth + dpToPx(2) + xValueRect.height() + dpToPx(2), dpToPx(2), dpToPx(2), xyTextPaint);
//                    } else {
                    canvas.drawText(text, 0, text.length(), x - rect.width() / 2, xStartPoint + xylinewidth + dpToPx(2) + rect.height(), xyTextPaint);
//                    }
                }
            }
        }

    }

    private void drawTitle(Canvas canvas) {
        if (root.Result.title.show) {
            titleString = root.Result.dataSource.title;
            TextPaint paintTitle = new TextPaint();
            paintTitle.setColor(Color.parseColor(root.Result.title.color));
            paintTitle.setTextSize(spToPx(root.Result.title.fontSize));
            canvas.drawText(titleString, width / 2, 35, paintTitle);
        }
    }


//    private void drawTagging(){
//        LinearLayout taggingLayout=new LinearLayout(context);
//        taggingLayout.
//    }


    private float startX;
    private float downX;
    private float downY;
    private boolean moveFlag = true;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isScrolling)
            return super.onTouchEvent(event);
        this.getParent().requestDisallowInterceptTouchEvent(true);//当该view获得点击事件，就请求父控件不拦截事件
        obtainVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (interval * xValue.size() > width - xOri) {//当期的宽度不足以呈现全部数据
                    float dis = event.getX() - startX;
                    startX = event.getX();
                    if (xInit + dis < minXInit) {
                        xInit = minXInit;
                    } else if (xInit + dis > maxXInit) {
                        xInit = maxXInit;
                    } else {
                        xInit = xInit + dis;
                    }
                    if (Math.abs(event.getX() - downX) > 10 || Math.abs(event.getY() - downY) > 10) {
                        moveFlag = false;
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                clickAction(event);
                scrollAfterActionUp();
                this.getParent().requestDisallowInterceptTouchEvent(false);
                moveFlag = true;
                recycleVelocityTracker();
                break;
            case MotionEvent.ACTION_CANCEL:
                this.getParent().requestDisallowInterceptTouchEvent(false);
                recycleVelocityTracker();
                break;
        }
        return true;
    }

    //是否正在滑动
    private boolean isScrolling = false;

    /**
     * 手指抬起后的滑动处理
     */
    private void scrollAfterActionUp() {
        if (!isScroll)
            return;
        final float velocity = getVelocity();
        float scrollLength = maxXInit - minXInit;
        if (Math.abs(velocity) < 10000)//10000是一个速度临界值，如果速度达到10000，最大可以滑动(maxXInit - minXInit)
            scrollLength = (maxXInit - minXInit) * Math.abs(velocity) / 10000;
        ValueAnimator animator = ValueAnimator.ofFloat(0, scrollLength);
        animator.setDuration((long) (scrollLength / (maxXInit - minXInit) * 1000));//时间最大为1000毫秒，此处使用比例进行换算
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = Float.parseFloat(valueAnimator.getAnimatedValue().toString());
                if (velocity < 0 && xInit > minXInit) {//向左滑动
                    if (xInit - value <= minXInit)
                        xInit = minXInit;
                    else
                        xInit = xInit - value;
                } else if (velocity > 0 && xInit < maxXInit) {//向右滑动
                    if (xInit + value >= maxXInit)
                        xInit = maxXInit;
                    else
                        xInit = xInit + value;
                }
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isScrolling = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isScrolling = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                isScrolling = false;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();

    }

    /**
     * 获取速度
     *
     * @return
     */
    private float getVelocity() {
        if (velocityTracker != null) {
            velocityTracker.computeCurrentVelocity(1000);
            return velocityTracker.getXVelocity();
        }
        return 0;
    }

    /**
     * 点击X轴坐标或者折线节点
     *
     * @param event
     */
    private void clickAction(MotionEvent event) {
        int dp8 = dpToPx(8);
        int dp12 = dpToPx(12);
        float eventX = event.getX();
        float eventY = event.getY();
        if (!moveFlag) {
            return;
        }
        for (int j = 0; j < value.size(); j++) {
            Map<String, Float> valueMap = this.value.get(j);
            for (int i = 0; i < xValue.size(); i++) {
                //节点
                float x = xInit + interval * i;
                float y = 0;
                if (xValue.size() > 0 && yValue.size() > 0 && valueMap.get(xValue.get(i)) >= 0) {
                    y = xStartPoint - (xStartPoint - maxYPoint) * valueMap.get(xValue.get(i)) / yValue.get(yValue.size() - 1);
                } else if (xValue.size() > 0 && yValue.size() > 0 && valueMap.get(xValue.get(i)) < 0) {
                    y = xStartPoint + (minYPoint - xStartPoint) * valueMap.get(xValue.get(i)) / yValue.get(0);
                }
                if (valueMap.get("type") == 1) {//点击折线的点
                    if (eventX >= x - dp12 && eventX <= x + dp12 &&
                            eventY >= y - dp12 && eventY <= y + dp12) {//每个节点周围8dp都是可点击区域
                        selectIndex = i + 1;
                        invalidate();
                        if (cliclListener != null) {
                            cliclListener.onBarLineClickListener(88.99f);
                        }
                        return;
                    }
                }
//                //X轴刻度
//                String text = xValue.get(i);
//                Rect rect = getTextBounds(text, xyTextPaint);
//                x = xInit + interval * i;
//                y = xStartPoint + xylinewidth + dpToPx(2);
//                if (eventX >= x - rect.width() / 2 - dp8 && eventX <= x + rect.width() + dp8 / 2 &&
//                        eventY >= y - dp8 && eventY <= y + rect.height() + dp8 && selectIndex != i + 1) {
//                    selectIndex = i + 1;
//                    invalidate();
//                    return;
//                }
            }
        }

        for (int j = 0; j < value.size(); j++) {
            Map<String, Float> valueMap = this.value.get(j);
            for (int i = 0; i < xValue.size(); i++) {
                if (valueMap.get("type") == 0) {//点击的是柱
                    for (int bar = 0; bar < barInfoList.size(); bar++) {
                        Map<String, Map<String, Object>> barMap = barInfoList.get(bar);
                        Map<String, Object> barOneMap = barMap.get(xValue.get(i));
                        float right = Float.parseFloat(barOneMap.get("right").toString());
                        float left = Float.parseFloat(barOneMap.get("left").toString());
                        float top = Float.parseFloat(barOneMap.get("top").toString());
                        float bottom = Float.parseFloat(barOneMap.get("bottom").toString());
                        float id = Float.parseFloat(barOneMap.get("id").toString());
                        if (eventX >= left && eventX <= right && eventY >= top && eventY <= bottom) {
                            if (valueMap.get("id") == id) {
                                Log.e("CLick", "  left:" + left + " rihgt:" + right + " eventx:" + eventX + " id:" + id);
                                if (cliclListener != null) {
                                    cliclListener.onBarLineClickListener(valueMap.get(xValue.get(i)));
                                }
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取速度跟踪器
     *
     * @param event
     */
    private void obtainVelocityTracker(MotionEvent event) {
        if (!isScroll)
            return;
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    /**
     * 回收速度跟踪器
     */
    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
        invalidate();
    }

    public void setxValue(List<String> xValue) {
        this.xValue = xValue;
    }

    public void setyValue(List<Float> yValue) {
        this.yValue = yValue;
        invalidate();
    }

    public void setValue(List<Map<String, Float>> value) {
        this.value = value;
        invalidate();
    }

    public void setValue(List<Map<String, Float>> value, List<String> xValue, List<Float> yValue) {
        this.value = value;
        this.xValue = xValue;
        this.yValue = yValue;
        invalidate();
    }

    public List<String> getxValue() {
        return xValue;
    }

    public List<Float> getyValue() {
        return yValue;
    }

    public List<Map<String, Float>> getValue() {
        return value;
    }

    public void setRoot(Root root) {
        this.root = root;
        if (value != null) {
            value.clear();
        }
        if (xValue != null) {
            xValue.clear();
        }
        if (yValue != null) {
            yValue.clear();
        }
        List<Map<String, Float>> value = new ArrayList<Map<String, Float>>();
        for (int i = 0; i < root.Result.dataSource.yData.size(); i++) {
            Map<String, Float> map = new HashMap<String, Float>();
            for (int j = 0; j < root.Result.dataSource.yData.get(i).data.size(); j++) {
                map.put(root.Result.dataSource.xData.get(0).data.get(j), Float.parseFloat(root.Result.dataSource.yData.get(i).data.get(j)));
            }
            if (root.Result.dataSource.yData.get(i).belong.type.equals("line")) {
                map.put("type", 1.0f);
            } else {
                map.put("type", 0.0f);
            }
            map.put("id", (float) root.Result.dataSource.yData.get(i).belong.id);
            value.add(map);
        }
        List<String> xValue = new ArrayList<String>();
        for (int i = 0; i < root.Result.dataSource.xData.size(); i++) {
            xValue.addAll(root.Result.dataSource.xData.get(i).data);
        }
        List<Float> yValue = new ArrayList<Float>();
        List<Integer> listMax = new ArrayList<Integer>();
        List<Integer> listMin = new ArrayList<Integer>();
        int Min;
        int Max;
        for (int i = 0; i < root.Result.dataSource.yData.size(); i++) {
            List<String> ydata = root.Result.dataSource.yData.get(i).data;
            int max = 0;
            int min = 0;
            if (ydata.size() > 0) {
                max = (int) Math.ceil(Float.parseFloat(ydata.get(0)));
                min = (int) Math.ceil(Float.parseFloat(ydata.get(0)));
            }
            for (int j = 0; j < ydata.size(); j++) {
                if ((int) Math.ceil(Float.parseFloat(ydata.get(j))) > max) {
                    max = (int) Math.ceil(Float.parseFloat(ydata.get(j)));
                }
                if ((int) Math.ceil(Float.parseFloat(ydata.get(j))) < min) {
                    min = (int) Math.floor(Float.parseFloat(ydata.get(j)));
                }
            }
            listMax.add(max);
            listMin.add(min);
        }
        Min = listMin.get(0);
        Max = listMax.get(0);
        for (int minf = 0; minf < listMin.size(); minf++) {
            if (Min > listMin.get(minf)) {
                Min = listMin.get(minf);
            }
        }
        for (int maxf = 0; maxf < listMax.size(); maxf++) {
            if (Max < listMax.get(maxf)) {
                Max = listMax.get(maxf);
            }
        }
        float yMax;
        float yMin;
        if (Max > 0 && Min >= 0) {
            int maxtemp = Max;
            int maxtempbefor = maxtemp;
            int an = 1;
            while (true) {
                maxtempbefor = maxtemp;
                maxtemp = maxtemp / 10;
                if (maxtemp <= 0) {
                    break;
                }
                an = an * 10;
            }
            if (maxtempbefor * an >= Max) {
                yMax = maxtempbefor * an;
                yMin = 0;
            } else if (maxtempbefor * an + (an / 2f) > Max) {
                yMax = maxtempbefor * an + (an / 2f);
                yMin = 0;
            } else {
                yMax = maxtempbefor * an + an;
                yMin = 0;
            }

            float cell = yMax / 10f;
            for (int i = 0; i < 11; i++) {
                yValue.add(floatTo1(cell * i));
            }

        } else if (Max > 0 && Min < 0) {
            int maxtemp = Max;
            int maxtempbefor = maxtemp;
            int an = 1;
            while (true) {
                an = an * 10;
                maxtempbefor = maxtemp;
                maxtemp = maxtemp / 10;
                if (maxtemp <= 0) {
                    break;
                }
            }
            if (maxtempbefor * an >= Max) {
                yMax = maxtempbefor * an;
            } else if (maxtempbefor * an + (an / 2f) > Max) {
                yMax = maxtempbefor * an + (an / 2f);
//                yMin=0;
            } else {
                yMax = maxtempbefor * an + an;
//                yMin=0;
            }

            int mintemp = -Min;
            int mintempbefor = mintemp;
            int anmin = 1;
            while (true) {
                anmin = anmin * 10;
                mintempbefor = mintemp;
                mintemp = mintemp / 10;
                if (mintemp <= 0) {
                    break;
                }
            }
            if (mintempbefor * anmin + (anmin / 2f) > -Min) {
                yMin = -(mintempbefor * anmin + (anmin / 2f));
//                yMin=0;
            } else {
                yMin = -(mintempbefor * anmin + anmin);
//                yMin=0;
            }
            float yCell = (yMax - yMin) / 10f;
            int i = 0;
            while (yCell * i > -Min) {
                i++;
            }
            for (int m = -i; m < 10 + -i + 1; m++) {
                yValue.add(floatTo1(m * yCell));
            }
        } else if (Max < 0 && Min < 0) {
            int mintemp = -Min;
            int mintempbefor = mintemp;
            int anmin = 1;
            while (true) {
                anmin = anmin * 10;
                mintempbefor = mintemp;
                mintemp = mintemp / 10;
                if (mintemp <= 0) {
                    break;
                }
            }
            if (mintempbefor * anmin + (anmin / 2f) > -Min) {
                yMin = -(mintempbefor * anmin + (anmin / 2f));
                yMax = 0;
            } else {
                yMin = -(mintempbefor * anmin + anmin);
                yMax = 0;
            }
            float yCell = -yMin / 10f;
            int i = 0;
            while (yCell * i > -Min) {
                i++;
            }
            for (int m = -i; m < 10 + -i + 1; m++) {
                yValue.add(floatTo1(m * yCell));
            }

        }
        setValue(value, xValue, yValue);
    }

    public void setOnBarLineClickListener(OnBarLineClickListener cliclListener) {
        this.cliclListener = cliclListener;
    }


    /**
     * 获取丈量文本的矩形
     *
     * @param text
     * @param paint
     * @return
     */
    private Rect getTextBounds(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }

    /**
     * dp转化成为px
     *
     * @param dp
     * @return
     */
    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }

    /**
     * sp转化为px
     *
     * @param sp
     * @return
     */
    private int spToPx(int sp) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (scaledDensity * sp + 0.5f * (sp >= 0 ? 1 : -1));
    }

    public void setWaterMark(boolean isShowWater, String showWaterText) {
        this.isShowWater = isShowWater;
        this.showWaterText = showWaterText;
        invalidate();
    }

    public float floatTo1(float f) {
        BigDecimal bg = new BigDecimal(f);
        float f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        return f1;
    }


}

