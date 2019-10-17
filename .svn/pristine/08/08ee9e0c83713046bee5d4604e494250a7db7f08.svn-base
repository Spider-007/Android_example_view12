package com.htmitech.emportal.ui.chart;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.emportal.entity.Item;
import com.htmitech.emportal.ui.chart.view.PieChart01View;
import com.htmitech.emportal.ui.widget.DialogCancelListener;

import java.util.ArrayList;

public class PieDetailDialog extends Dialog implements View.OnClickListener {

	private DialogCancelListener onCancelListener;

	private TextView title;

	private Button btn_cancel;

	private ListView listview;

	private DetailAdapter adapter;

	private String titleStr, cancelBtnStr = "取消";
	
	private PieChart01View pieChartView = null;

	@SuppressWarnings("unused")
	private int titleStrId = -1, cancelBtnStrId = -1;

	private ArrayList<Item> chartItems = new ArrayList<Item>();

	@SuppressWarnings("unused")
	private SpannableStringBuilder contentStyle;

	/*********************************************************************/
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chart_detail_cancel:
			/*if (onCancelListener != null) {
				onCancelListener.onCancel();
			}*/
			this.cancel();
			break;
		}
		dismiss();
	}

	/*********************************************************************/
	public void setViewValue(String titlestr, Item[] items) {
		if (title != null) {
			this.titleStr = titlestr;
			if (titleStr != null)
				title.setText(titleStr);
		}
		for (Item item : items) {
			if (!chartItems.contains(item)) {
				chartItems.add(item);
			}	
		}

		if (this.listview != null) {
			adapter = new DetailAdapter(chartItems, getContext());
			listview.setAdapter(adapter);
		}
	}
	
	private class DetailAdapter extends ArrayAdapter<Item> {
		Context mContext;
		public DetailAdapter(ArrayList<Item> detailItems, Context mContext) {
			super(mContext, 0, detailItems);
			this.mContext = mContext;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO 自动生成的方法存根
			if (null == convertView) {
				convertView = LayoutInflater.from(mContext)
	                    .inflate(R.layout.list_item_detail, null);
			}
			Item item = getItem(position);
			ImageView detailImageView =  (ImageView)convertView.findViewById(R.id.detail_image);
			detailImageView.setBackgroundColor(item.getChartColor());
			TextView detailTextView = (TextView)convertView.findViewById(R.id.detail_text);
			detailTextView.setText(item.getDes());
			
			return convertView;
		}
		
	}

	public void setViewText(String titleStr, String cancelBtnStr) {
		if (this.titleStr != null) {
			this.titleStr = titleStr;
		}
		if (cancelBtnStr != null) {
			this.cancelBtnStr = cancelBtnStr;
		}
	}

	/*********************************************************************/
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chart_detail_dialog);
		
		initView();
		setData();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.chart_detail_title);
		listview = (ListView) findViewById(R.id.chart_detail_listview);
		btn_cancel = (Button) findViewById(R.id.chart_detail_cancel);

		btn_cancel.setOnClickListener(this);

		btn_cancel.setText(R.string.cancel);
	}

	private void setData() {
		if (titleStr != null) {
			title.setText(titleStr);
		} 

		if (cancelBtnStr != null) {
			btn_cancel.setText(cancelBtnStr);
		} 
		
		if (this.listview != null) {
			adapter = new DetailAdapter(chartItems, getContext());
			listview.setAdapter(adapter);
			
			listview.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					pieChartView.eventPostMan(pieChartView.getEventData(arg2));
				}
			});
		}
		
	}
	
	
	public PieDetailDialog(Context context, int theme, PieChart01View pieChartView) {
		super(context, theme);
		this.pieChartView = pieChartView;
	}

	public PieDetailDialog(Context context, int theme, DialogCancelListener onCancelListener, PieChart01View pieChartView) {
		super(context, theme);
		this.onCancelListener = onCancelListener;
		this.pieChartView = pieChartView;
	}
}
