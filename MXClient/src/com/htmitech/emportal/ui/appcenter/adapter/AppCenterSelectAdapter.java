package com.htmitech.emportal.ui.appcenter.adapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.commonx.base.BitmapUtils;
import com.htmitech.emportal.R;
import com.minxing.kit.api.bean.MXAppInfo;


/**
 * 快捷方式列表，用户移除或者增加
 * @author gulbel
 *
 */


public class AppCenterSelectAdapter extends BaseAdapter {

	private LayoutInflater lInflater;

	//键：ocuInfo   值：用户是否已经选择
	private List<HashMap<MXAppInfo, Boolean>> list;

	private List<MXAppInfo> selectList = new ArrayList<MXAppInfo>();
	private Context context;
	public AppCenterSelectAdapter(Context context,
			List<HashMap<MXAppInfo, Boolean>> list) {
		this.context=context;
		this.list = list;
		lInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(list==null || list.size()==0){
			return 0;
		}
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	//用户已经选择的列表
	public List<MXAppInfo> getSelectList(){
		return selectList;
	}
	public void setSelectList(List<MXAppInfo> selectList){
		this.selectList=selectList;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = lInflater.inflate(R.layout.center_gridview_item, null);
			holder.textView = (TextView) convertView
					.findViewById(R.id.textview_griditem);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.imageview_griditem);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.checkbox_griditem);
			holder.imageView.setTag(holder);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HashMap<MXAppInfo, Boolean> hashMap = (HashMap<MXAppInfo, Boolean>)list.get(position);
		final MXAppInfo ocuInfo=(MXAppInfo)hashMap.keySet().iterator().next();
				
		final boolean hasSelect=(Boolean)hashMap.values().iterator().next();
		if(hasSelect){
			holder.checkBox.setChecked(true);
		}
		String bitmapUrl = ocuInfo.getAvatarUrl();
		BitmapUtils.instance().display(holder.imageView, bitmapUrl);
		holder.textView.setText(ocuInfo.getName());
		holder.checkBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							selectList.add(ocuInfo);
						} else {
							int size=selectList.size();
							for(int i=0;i<size;i++){
								if(selectList.get(i).getAppID().equalsIgnoreCase(ocuInfo.getAppID())){
									selectList.remove(i);
									break;
								}
							}
						}
						
						
					}
				});
		
		holder.imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ViewHolder holder = (ViewHolder)v.getTag();
				holder.checkBox.setChecked(!holder.checkBox.isChecked());
			}
		});
		return convertView;
	}

	class ViewHolder {
		ImageView imageView;
		CheckBox checkBox;
		TextView textView;

	}
}
