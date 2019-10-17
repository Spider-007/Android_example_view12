package com.htmitech.proxy.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.homepage.HomeGridSysle;

import java.text.DecimalFormat;
import java.util.ArrayList;

import mobilereport.com.chatkit.domain.DetectionValue;


public class PicReportButtomAdapter extends BaseAdapter{
	public Activity context;
	private ArrayList<DetectionValue> mDetectionValueList;
	public LayoutInflater inflater;
	/** 控制的postion */
	private int holdPosition;
	/** 是否显示底部的ITEM */
	private boolean isItemShow = false;
	public boolean isChanged = false;
	/** 是否可见 */
	boolean isVisible = true;
	public double total;
	/** 控制的postion */
	public PicReportButtomAdapter(Activity context, ArrayList<DetectionValue> mDetectionValueList,double total){
		this.context = context;
		this.mDetectionValueList = mDetectionValueList;
		this.total = total;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDetectionValueList.size();
	}

	@Override
	public DetectionValue getItem(int arg0) {
		// TODO Auto-generated method stub
		return mDetectionValueList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		DetectionValue mDetectionValue = mDetectionValueList.get(arg0);
		ViewHolder mViewHolder = null;
		if(arg1 == null){
			mViewHolder = new ViewHolder();
			arg1 = inflater.inflate(R.layout.activity_picreport_buttom, null);

			mViewHolder.tv_tanshang_color = (TextView)arg1.findViewById(R.id.tv_tanshang_color);
			mViewHolder.tv_tanshang_value = (TextView) arg1.findViewById(R.id.tv_tanshang_value);
			arg1.setTag(mViewHolder);
		}else{
			mViewHolder = (ViewHolder) arg1.getTag();
		}
		mViewHolder.tv_tanshang_color.setBackgroundColor(Color.parseColor(mDetectionValue.color));
		DecimalFormat df   =   new   DecimalFormat("#####0.0");
		mViewHolder.tv_tanshang_value.setText(mDetectionValue.name+" ("+df.format((mDetectionValue.value/total) * 100)+"%"+")");
		mViewHolder.tv_tanshang_value.setTextColor(Color.parseColor(mDetectionValue.color));
		return arg1;
	}

	class ViewHolder {
		private TextView tv_tanshang_color;
		private TextView tv_tanshang_value;
	}

	/** 显示放下的ITEM */
	public void setShowDropItem(boolean show) {
		isItemShow = show;
	}
}
