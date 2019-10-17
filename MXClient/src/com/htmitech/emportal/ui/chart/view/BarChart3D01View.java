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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.htmitech.emportal.entity.BarLineChartResult;

import org.xclcharts.chart.BarChart3D;
import org.xclcharts.chart.BarData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.event.click.BarPosition;
import org.xclcharts.renderer.XEnum;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName Bar3DChart01View
 * @Description  3D柱形图例子
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 */
public class BarChart3D01View extends DemoView {
	
	private String TAG = "Bar3DChart01View";	
	private BarChart3D chart = new BarChart3D();
	
	private BarLineChartResult barData;
	
	Context mContext;
	
	//标签轴
	private List<String> chartLabels = new LinkedList<String>();
	//数据轴
	private List<BarData> BarDataset = new LinkedList<BarData>();
	
	Paint mPaintToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);
	
	public BarChart3D01View(Context context) {
		super(context);
		// TODO Auto-generated constructor stub				
		initView();
	}
	
	public BarChart3D01View(Context context, BarLineChartResult barData) {
		super(context);
		// TODO Auto-generated constructor stub
		this.barData = barData;
		mContext = context;
		initView();
	}
		
	
	public BarChart3D01View(Context context, AttributeSet attrs){   
        super(context, attrs);   
        initView();
	 }
	 
	 public BarChart3D01View(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			initView();
	 }
	 
	 private void initView()
	 {
		 if (barData.getXobj().getTextArray().length < 1) {
			 Toast.makeText(mContext, "数据项为空", Toast.LENGTH_SHORT).show();
			 return;
		 }
		 	chartLabels();
			chartDataSet();	
			chartRender();
			
			//綁定手势滑动事件
			this.bindTouch(this,chart);
	 }
	 
	@Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(w, h, oldw, oldh);  
       //图所占范围大小
        chart.setChartRange(w,h);
    }  
	
	
	private void chartRender()
	{
		try {						
			
			//设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....	
			int [] ltrb = getBarLnDefaultSpadding();
			chart.setPadding( DensityUtil.dip2px(getContext(), 50),ltrb[1],
							ltrb[2], ltrb[3] + DensityUtil.dip2px(getContext(), 25) );
			
			//显示边框
			chart.showRoundBorder();
			
			
			//数据源			
			chart.setDataSource(BarDataset);
			chart.setCategories(chartLabels);	
			chart.getCategoryAxis().getTickLabelPaint().setColor(Color.WHITE);
			//Paint.Style a = chart.getCategoryAxis().getTickLabelPaint().getStyle();
			//chart.getPlotTitle().getTitlePaint().setTextSize(22);	
			//chart.getCategoryAxis().getTickLabelPaint().setTextAlign(Align.CENTER);
			
			
			//坐标系
			chart.getDataAxis().setAxisMax(barData.getYobj().getMaxValue());
			chart.getDataAxis().setAxisMin(barData.getYobj().getMinValue());
			chart.getDataAxis().setAxisSteps(barData.getYobj().getMaxValue() / barData.getYobj().getNumbers());
			//chart.getCategoryAxis().setAxisTickLabelsRotateAngle(-45f);
			
			//隐藏轴线和tick
			chart.getDataAxis().hideAxisLine();
			//chart.getDataAxis().setTickMarksVisible(false);
			
			//标题
			chart.setTitle(barData.getReportName());
			chart.getPlotTitle().getTitlePaint().setColor(Color.WHITE);
			chart.getPlotTitle().getSubtitlePaint().setColor(Color.WHITE);
			chart.getAxisTitle().getRightTitlePaint().setColor(Color.WHITE);
			chart.getAxisTitle().getLeftTitlePaint().setColor(Color.WHITE);
			chart.getAxisTitle().getLowerTitlePaint().setColor(Color.WHITE);
			
			//chart.addSubtitle("(XCL-Charts Demo)");		
			chart.setTitleAlign(XEnum.HorizontalAlign.RIGHT);
		
			
			//背景网格
			chart.getPlotGrid().showHorizontalLines();
			chart.getPlotGrid().showVerticalLines();
			chart.getPlotGrid().showEvenRowBgColor();
			chart.getPlotGrid().showOddRowBgColor();
			
			
			//定义数据轴标签显示格式		
			chart.getDataAxis().setTickLabelRotateAngle(-45);
			chart.getDataAxis().getTickMarksPaint().
					setColor(Color.rgb(186, 20, 26));
			chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub							
					Double tmp = Double.parseDouble(value);
					DecimalFormat df=new DecimalFormat("#0");
					String label = df.format(tmp).toString();				
					//return (label +"公斤");
					return (label);
				}
				
			});
			
			//设置标签轴标签交错换行显示
			chart.getCategoryAxis().setLabelLineFeed(XEnum.LabelLineFeed.EVEN_ODD);
		
			//定义标签轴标签显示格式
			chart.getCategoryAxis().setLabelFormatter(new IFormatterTextCallBack(){
	
				@Override
				public String textFormatter(String value) {
					// TODO Auto-generated method stub									
					return value;
				}
				
			});
			//定义柱形上标签显示格式
			chart.getBar().setItemLabelVisible(true);
			chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
				@Override
				public String doubleFormatter(Double value) {
					// TODO Auto-generated method stub
					DecimalFormat df=new DecimalFormat("#0.00");					 
					String label = df.format(value).toString();
					return label;
				}});	        
			//激活点击监听
			chart.ActiveListenItemClick();
			
			//仅能横向移动
			chart.setPlotPanMode(XEnum.PanMode.HORIZONTAL);
			
			
			//扩展横向显示范围
		//	chart.getPlotArea().extWidth(200f);
			
			//标签文字与轴间距
			chart.getCategoryAxis().setTickLabelMargin(5);
					
			//不使用精确计算，忽略Java计算误差
			chart.disableHighPrecision();
			
			// 设置轴标签字体大小
			chart.getDataAxis().getTickLabelPaint().setTextSize(26);
			chart.getDataAxis().getTickLabelPaint().setColor(Color.WHITE);
			
			chart.getBar().getItemLabelPaint().setTextSize(22);
			//chart.getDataAxis().getAxisPaint().setTextSize(26);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void chartDataSet()
	{
		List<Double> dataSeriesA = null;
		for (int i = 0; i < barData.getDataItems().length; i++) {
			dataSeriesA = new LinkedList<Double>();
			for (int y = 0; y < barData.getDataItems()[i].getValueItems().length; y++) 
				dataSeriesA.add((double)barData.getDataItems()[i].getValueItems()[y].getValue());
			BarDataset.add(new BarData(barData.getDataItems()[i].getDes(), dataSeriesA, barData.getDataItems()[i].getChartColor()));	//
		}
		
		//标签对应的柱形数据集
		/*	
		dataSeriesA.add(200d);
		dataSeriesA.add(250d);
		dataSeriesA.add(400d); 
		dataSeriesA.add(450d); 
		dataSeriesA.add(150d);*/
/*
		List<Double> dataSeriesB= new LinkedList<Double>();	
		dataSeriesB.add(300d);
		dataSeriesB.add(150d); 
		dataSeriesB.add(450d); 
		dataSeriesB.add(480d); 
		dataSeriesB.add(200d);
		
		BarDataset.add(new BarData("左边店",dataSeriesA,Color.rgb(252, 210, 9)));
		BarDataset.add(new BarData("右边店",dataSeriesB,Color.rgb(55, 144, 206)));
		
		
		List<Double> dataSeriesC= new LinkedList<Double>();	
		dataSeriesC.add(270d);
		dataSeriesC.add(180d); 
		//dataSeriesC.add(450d); 
		//dataSeriesC.add(380d); 
		//dataSeriesC.add(230d);
		BarDataset.add(new BarData("右边店2",dataSeriesC,Color.rgb(155, 144, 206)));*/
	}
	
	//底部label
	private void chartLabels()
	{
		if (barData.getXobj().getStyle() == 0) {
			for (int i = 0; i < barData.getXobj().getTextArray().length; i++)
				chartLabels.add(barData.getXobj().getTextArray()[i]); 
		} else if (barData.getXobj().getStyle() == 1) {
			for (int i = 0; i < barData.getXobj().getTextArray().length; i++)
				chartLabels.add(barData.getXobj().getTextArray()[i]);
		}
		
	}


	@Override
    public void render(Canvas canvas) {
        try{
            chart.render(canvas);
        } catch (Exception e){
        	Log.e(TAG, e.toString());
        }
    }


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub		
		super.onTouchEvent(event);		
		if(event.getAction() == MotionEvent.ACTION_UP) 
		{			
			triggerClick(event.getX(),event.getY());	
		}
		return true;
	}
	
	
	//触发监听
	private void triggerClick(float x,float y)
	{
		if(!chart.getListenItemClickStatus()) return ;
		
		BarPosition record = chart.getPositionRecord(x,y);
		if( null == record) return;
		
		BarData bData = BarDataset.get(record.getDataID());
		Double bValue = bData.getDataSet().get(record.getDataChildID());			
		
		//在点击处显示tooltip
		mPaintToolTip.setColor(Color.WHITE);	
		chart.getToolTip().getBackgroundPaint().setColor(Color.rgb(75, 202, 255));	
		chart.getToolTip().getBorderPaint().setColor(Color.RED);
		chart.getToolTip().setCurrentXY(x,y);	
		chart.getToolTip().addToolTip(
					" Current Value:" +Double.toString(bValue),mPaintToolTip);
		chart.getToolTip().getBackgroundPaint().setAlpha(100);
		this.invalidate();
	}
	
}
