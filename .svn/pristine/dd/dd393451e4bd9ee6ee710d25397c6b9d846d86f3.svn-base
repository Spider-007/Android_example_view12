/**
 * Copyright 2014  XCL-Charts
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 	
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @Copyright Copyright (c) 2014 XCL-Charts (www.xclcharts.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.0
 */
package com.htmitech.emportal.ui.chart.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.entity.EventDataResult;
import com.htmitech.emportal.entity.EventDataResultInfo;
import com.htmitech.emportal.entity.PieChartResult;
import com.htmitech.emportal.entity.TableChartResultInfo;
import com.htmitech.emportal.ui.chart.ListChartActivity;
import com.htmitech.emportal.ui.chart.MapActivity;
import com.htmitech.emportal.ui.chart.MapActivity.OneChartDetailPostEntity;
import com.htmitech.emportal.ui.chart.model.task.ChartListModel;
import com.htmitech.emportal.ui.chart.post.entity.EventDataPostEntity;

import org.xclcharts.chart.PieChart;
import org.xclcharts.chart.PieData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.event.click.ArcPosition;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.renderer.plot.PlotLegend;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * @ClassName PieChart01View
 * @Description 饼图
 * @author XiongChuanLiang<br/>
 *         (xcl_168@aliyun.com)
 */

public class CopyOfPieChartView extends DemoView implements Runnable,
		Serializable, IBaseCallback {
	/**
	 * 合计获取
	 *
	 */
	public double getCount() {
		return count;
	}


	private String TAG = "PieChart01View";
	private PieChart chart = new PieChart();
	private ArrayList<PieData> chartData = new ArrayList<PieData>();
	private int mSelectedID = -1;
	OneChartDetailPostEntity mPieChartDetailPostEntity;
	int currentClick;
	Context context;
	public double count = 0;
	ChartListModel chartModel = null;
	private IPieItemClickListener mListener;
	private PieChartResult pieData = null;
	private int decimalcount = 0;  //绘制数字时保留的位数
	
	private boolean hasdata = true;
	
	

	public int getDecimalcount() {
		return decimalcount;
	}

	public void setDecimalcount(int decimalcount) {
		this.decimalcount = decimalcount;
	}

	public boolean isHasdata() {
		return hasdata;
	}

	public void setHasdata(boolean hasdata) {
		this.hasdata = hasdata;
	}

	public CopyOfPieChartView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		initView(null);
	}

	public CopyOfPieChartView(Context context, PieChartResult pieData) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.pieData = pieData;
		initView(null);
	}

	public CopyOfPieChartView(Context context, PieChartResult pieData,
			Object mPieChartDetailPostEntity,String strChartTitle, int decimalCount) {
		super(context);
		// TODO Auto-generated constructor stub
		this.pieData = pieData;
		this.mPieChartDetailPostEntity = (OneChartDetailPostEntity) mPieChartDetailPostEntity;
		this.context = context;
		this.decimalcount = decimalCount;
		initView(strChartTitle);
	}

	public CopyOfPieChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(null);
	}

	public CopyOfPieChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(null);
	}

	private void initView(String strChartTitle) {
		if (pieData.getItems().length < 1) {
//			Toast.makeText(context, "当前无数据", Toast.LENGTH_SHORT).show();
			hasdata = false;
			return;
		}
		hasdata = true;
		chartDataSet();
		chartRender(strChartTitle);

		// 綁定手势滑动事件
//		this.bindTouch(this, chart);
		new Thread(this).start();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 图所占范围大小
		chart.setChartRange(w, h);
	}

	private void chartRender(String strChartTitle) {
		try {

			// 设置绘图区默认缩进px值
			int[] ltrb = getPieDefaultSpadding();
			ltrb[0] = DensityUtil.dip2px(getContext(), 40); //left
			ltrb[1] = DensityUtil.dip2px(getContext(), 20); //top
			ltrb[2] = DensityUtil.dip2px(getContext(), 40); //right
			ltrb[3] = DensityUtil.dip2px(getContext(), 60); //bottom
			chart.setPadding(ltrb[0],ltrb[1], ltrb[2] , ltrb[3]);

			// 设置起始偏移角度(即第一个扇区从哪个角度开始绘制)
			// chart.setInitialAngle(90);

			// 标签显示(隐藏，显示在中间，显示在扇区外面)
			chart.setLabelStyle(XEnum.SliceLabelStyle.INSIDE);
			chart.getLabelPaint().setColor(Color.WHITE); 

			// 标题
//			chart.setTitle(pieData.getReportName());
//			if (strChartTitle != null && strChartTitle.trim().length() > 0)
//				chart.setTitle("    " +strChartTitle);
			chart.setTitleVerticalAlign(XEnum.VerticalAlign.TOP);
			
			
			chart.getPlotTitle().getTitlePaint().setTextSize(DensityUtil.dip2px(getContext(),16));
			chart.getPlotTitle().getTitlePaint().setColor(Color.WHITE);
			chart.getPlotTitle().getSubtitlePaint().setColor(Color.WHITE);
			// chart.addSubtitle("(XCL-Charts Demo)");
//			chart.setTitleVerticalAlign(XEnum.VerticalAlign.BOTTOM);
			chart.setTitleVerticalAlign(XEnum.VerticalAlign.TOP);
			chart.setTitleAlign(XEnum.HorizontalAlign.LEFT);

			// chart.setDataSource(chartData);

			// 激活点击监听
			chart.ActiveListenItemClick();
			chart.showClikedFocus();

			// 设置允许的平移模式
			// chart.enablePanMode();
			// chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);

//			// 显示图例
//						PlotLegend legend = chart.getPlotLegend();
//						legend.show();
//						legend.setType(XEnum.LegendType.COLUMN);
//						legend.setHorizontalAlign(XEnum.HorizontalAlign.RIGHT);
//						legend.setVerticalAlign(XEnum.VerticalAlign.MIDDLE);
//						legend.showBox();
//						legend.getPaint().setTextSize(22);
						
			// 显示图例
			PlotLegend legend = chart.getPlotLegend();
//			legend.show();

//			if (pieData.getItems().length >2){

			
			legend.setType(XEnum.LegendType.ROW);
			if (pieData.getItems().length > 8){

//				legend.hide();
//				legend.getBox().setBorderRectType(XEnum.RectType.RECT);
//				legend.setType(XEnum.LegendType.COLUMN);
			}
			legend.hide();
			
			
			
			
			legend.setHorizontalAlign(XEnum.HorizontalAlign.LEFT);
			legend.setVerticalAlign(XEnum.VerticalAlign.BOTTOM);
			legend.showBox();
			legend.getBox().setBorderLineColor(Color.BLUE);
			legend.getBox().getBackgroundPaint().setColor(Color.TRANSPARENT);//#E9F3FF
			legend.getPaint().setColor(Color.GREEN);  
			legend.getPaint().setTextSize(DensityUtil.dip2px(getContext(),16));
			chart.getLabelPaint().setTextSize(DensityUtil.dip2px(getContext(),16));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}

	public  static  double  add(String  v1,String  v2){ 
			BigDecimal b1 = new BigDecimal(v1);
			BigDecimal b2 = new BigDecimal(v2); 
			return b1.add(b2).doubleValue();
			} 
			
	private void chartDataSet() {
		/*
		 * //设置图表数据源 chartData.add(new PieData("HP","20%",20,(int)Color.rgb(155,
		 * 187, 90))); chartData.add(new
		 * PieData("IBM","30%",30,(int)Color.rgb(191, 79, 75),false));
		 * chartData.add(new PieData("DELL","10%",10,(int)Color.rgb(242, 167,
		 * 69))); //将此比例块突出显示 chartData.add(new
		 * PieData("EMC","40%",40,(int)Color.rgb(60, 173, 213),false));
		 */

		/*
		 * chartData.add(new PieData("closed","9%" , 9,Color.rgb(155, 187,
		 * 90))); chartData.add(new PieData("inspect","3%" , 3,Color.rgb(191,
		 * 79, 75))); chartData.add(new PieData("open","76%" ,
		 * 76f,Color.rgb(242, 167, 69))); chartData.add(new
		 * PieData("workdone","6%" , 6,Color.rgb(60, 173, 213)));
		 * chartData.add(new PieData("dispute","6%" , 6,Color.rgb(90, 79, 88)));
		 */
		
		
		DecimalFormat df = new DecimalFormat("##0.000");
		df.setRoundingMode(RoundingMode.HALF_UP);  
		if (decimalcount==0)
		{
			df = new DecimalFormat("###");
			df.setMaximumFractionDigits(0);//显示小时位的最大字数
		}
		
		for (int i = 0; i < pieData.getItems().length; i++) {
			
			//负数不加入计算
			if (pieData.getItems()[i].getItemValue() > 0)
			{
				String s = df.format(pieData.getItems()[i].getItemValue());
				Double newv = Double.parseDouble(s);
				pieData.getItems()[i].setItemValue(newv);
				count = add( df.format(count),s);
			}
		}
		
		
		for (int i = 0; i < pieData.getItems().length; i++) {
			//负数时归0
			double v = 0;
			if (pieData.getItems()[i].getItemValue() > 0)
				v = pieData.getItems()[i].getItemValue();
			
			double ratio = (double) (v)
					/ count * 100d;
			// chartData.add(new PieData(pieData.getItems()[i].getDes() ,
			// df.format(ratio) + "% (" +
			// df.format(pieData.getItems()[i].getItemValue()) + ")", ratio ,
			// pieData.getItems()[i].getChartColor()));
			// chartData.add(new PieData( ratio + "", ratio , Color.rgb(191, 79,
			// 75)));
			
			if (pieData.getItems()[i].getDes()== null || pieData.getItems()[i].getDes().length() ==0)
				pieData.getItems()[i].setDes(" ");
			chartData.add(new PieData(pieData.getItems()[i].getDes(), ""
					+ df.format(v), ratio,
					pieData.getItems()[i].getChartColor()));
		}
	}

	@Override
	public void render(Canvas canvas) {
		try {
			chart.render(canvas);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			chartAnimation();
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}
	}

	private void chartAnimation() {
		try {

			chart.setDataSource(chartData);
			int count = 360 / 10;

			for (int i = 1; i < count; i++) {
				Thread.sleep(40);

				chart.setTotalAngle(10 * i);

				// 激活点击监听
				if (count - 1 == i) {
					chart.setTotalAngle(360);

					chart.ActiveListenItemClick();
					// 显示边框线，并设置其颜色
					// chart.getArcBorderPaint().setColor(Color.YELLOW);
					// chart.getArcBorderPaint().setStrokeWidth(3);
				}

				postInvalidate();
			}

		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}

	}

	/*
	 * 另一种动画 private void chartAnimation() { try {
	 * 
	 * float sum = 0.0f; int count = chartData.size(); for(int i=0;i< count
	 * ;i++) { Thread.sleep(150);
	 * 
	 * ArrayList<PieData> animationData = new ArrayList<PieData>();
	 * 
	 * sum = 0.0f;
	 * 
	 * for(int j=0;j<=i;j++) { animationData.add(chartData.get(j)); sum =
	 * (float) MathHelper.getInstance().add( sum ,
	 * chartData.get(j).getPercentage()); }
	 * 
	 * animationData.add(new PieData("","", MathHelper.getInstance().sub(100.0f
	 * , sum), Color.argb(1, 0, 0, 0))); chart.setDataSource(animationData);
	 * 
	 * //激活点击监听 if(count - 1 == i) { chart.ActiveListenItemClick();
	 * //显示边框线，并设置其颜色 chart.getArcBorderPaint().setColor(Color.YELLOW);
	 * chart.getArcBorderPaint().setStrokeWidth(3); }
	 * 
	 * postInvalidate(); }
	 * 
	 * } catch(Exception e) { Thread.currentThread().interrupt(); }
	 * 
	 * }
	 */

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		super.onTouchEvent(event);
		if (event.getAction() == MotionEvent.ACTION_UP) {
			if (chart.isPlotClickArea(event.getX(), event.getY())) {
				triggerClick(event.getX(), event.getY());
			}
		}
		return true;
	}

	public void setOnPieitemClickListener(IPieItemClickListener iPieItemClickListener) {
		
			mListener = iPieItemClickListener;
			
	}

	// 触发监听
	private void triggerClick(float x, float y) {
		if (!chart.getListenItemClickStatus())
			return;

		ArcPosition record = chart.getPositionRecord(x, y);
		if (null == record)
			return;
		/*
		 * PieData pData = chartData.get(record.getDataID());
		 * Toast.makeText(this.getContext(), " key:" + pData.getKey() +
		 * " Label:" + pData.getLabel() , Toast.LENGTH_SHORT).show();
		 */

		// 用于处理点击时弹开，再点时弹回的效果
		PieData pData = chartData.get(record.getDataID());
		if (record.getDataID() == mSelectedID) {
			boolean bStatus = chartData.get(mSelectedID).getSelected();
			chartData.get(mSelectedID).setSelected(!bStatus);
		} else {
			if (mSelectedID >= 0)
				chartData.get(mSelectedID).setSelected(false);
			pData.setSelected(true);
		}
		mSelectedID = record.getDataID();
		this.refreshChart();

		// 单击后打开另外一张table报表存住单击下表 pos, 请求查询key
		currentClick = record.getDataID();
		
		if (mListener != null) {
			mListener.OnItemClick(currentClick);
		}
//		eventPostMan(getEventData(record.getDataID()));
	}

	// 返回事件请求数据集
	public EventDataPostEntity getEventData(int currentClick) {
		EventDataPostEntity eventData = new EventDataPostEntity();
		eventData.setDataItemFieldValue(pieData.getItems()[currentClick]
				.getDataValue());
		eventData.setDataItemIndex(currentClick);
		eventData.setDataItemName(pieData.getItems()[currentClick].getDes());
		// eventData.setValueItemFieldValue("");
		eventData.setValueItemIndex(0);
		eventData
				.setValue(pieData.getItems()[currentClick].getItemValue() + "");
		eventData.setReportGuid(mPieChartDetailPostEntity.reportGuid);
		eventData.setReportParameters(mPieChartDetailPostEntity.parameterList);
		return eventData;
	}

	// 请求服务器 获取 报表参数
	public void eventPostMan(EventDataPostEntity eventData) {
		if (chartModel == null)
			chartModel = new ChartListModel(this);
		chartModel.getDataFromServerByType(
				ChartListModel.TYPE_GETEVENTParamters, eventData);
	}

	private OneChartDetailPostEntity updataPostEntity(EventDataResult result) {
		OneChartDetailPostEntity oneChart = new MapActivity.OneChartDetailPostEntity();
		oneChart.reportGuid = result.getReportID();
		oneChart.parameterList = result.getReportParamers();

		return oneChart;
	}

	@Override
	public void onSuccess(int requestTypeId, Object result) {
		if (requestTypeId == ChartListModel.TYPE_GETEVENTParamters) {
			if (((EventDataResultInfo) result).getResult().getReportType() == 3) { // 等于3请求报表数据
				OneChartDetailPostEntity oneChart = updataPostEntity(((EventDataResultInfo) result)
						.getResult());
				if (oneChart != null)
					chartModel.getDataFromServerByType(
							ChartListModel.TYPE_CHART_LIST, oneChart);
			}
		} else {
			Intent i = new Intent(context, ListChartActivity.class);
			i.putExtra("Type_Id", requestTypeId);
			if (requestTypeId == ChartListModel.TYPE_CHART_LIST) {
				i.putExtra("Table_Data",
						((TableChartResultInfo) result).getResult());
			}
			context.startActivity(i);
			// startActivity(i);
		}
	}

	// 列表数据适配返回失败
	@Override
	public void onFail(int requestTypeId, int statusCode, String errorMsg,
			Object result) {
		Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT).show();
	}

}
