package com.htmitech.ztcustom.zt.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.chinarailway.CardListActivity;
import com.htmitech.ztcustom.zt.domain.GetDefectStatByOrgList;
import com.htmitech.ztcustom.zt.interfaces.CallBackDefectStatOrg;
import com.htmitech.ztcustom.zt.util.DensityUtil;
import com.htmitech.ztcustom.zt.util.ZTActivityUnit;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 现存伤损动态排名-工务段 adapter
 * 
 * @author htrf-pc
 * 
 */
public class GetDefectStatOrgAdapter extends BaseAdapter {
	public Activity context;
	public ArrayList<GetDefectStatByOrgList> mGetDefectStatByOrgList;
	public LayoutInflater inflater;
	private int width;
	private int maxValue;
	private CallBackDefectStatOrg mCallBackDefectStatOrg;
	private String  beginTime;
	private String endTime;
	private String code;
	private String org_id;
	private String org_type;
	private boolean isShare;
	public GetDefectStatOrgAdapter(Activity context, ArrayList<GetDefectStatByOrgList> mGetDefectStatByOrgList, CallBackDefectStatOrg mCallBackDefectStatOrg, boolean isShare) {
		this.context = context;
		if(mGetDefectStatByOrgList == null){
			this.mGetDefectStatByOrgList = new ArrayList<GetDefectStatByOrgList> ();
		}else{
			this.mGetDefectStatByOrgList = mGetDefectStatByOrgList;
		}
		this.mCallBackDefectStatOrg = mCallBackDefectStatOrg;
		inflater = LayoutInflater.from(context);
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		width = wm.getDefaultDisplay().getWidth(); //1080
		int widthJa = 0;
		if(width == 720){
			widthJa = 330;
		}else if(width == 1080){
			widthJa = 390;
		}else{
			widthJa = DensityUtil.dip2px(context,122);
		}
		width = width - widthJa;
		this.isShare = isShare;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mGetDefectStatByOrgList == null){
			return 0;
		}
		return mGetDefectStatByOrgList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mGetDefectStatByOrgList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressWarnings("null")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHodler mViewHodler = null;
		GetDefectStatByOrgList mGetDefectStatByOrg = mGetDefectStatByOrgList.get(arg0);
		if (arg1 == null) {
			mViewHodler = new ViewHodler();
			arg1 = inflater.inflate(R.layout.zt_activity_dynamic_adapter, null);
			mViewHodler.tv_num = (TextView) arg1.findViewById(R.id.tv_num);
			mViewHodler.tv_name = (TextView) arg1.findViewById(R.id.tv_name);
			mViewHodler.tv_value = (TextView) arg1.findViewById(R.id.tv_value);
			mViewHodler.my_progress = (ProgressBar) arg1.findViewById(R.id.my_progress);
			arg1.setTag(mViewHodler);
		}else{
			mViewHodler = (ViewHodler) arg1.getTag();
		}
		int totalValue = mGetDefectStatByOrg.value;
		

		String shortname = mGetDefectStatByOrg.name;
		
		LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) mViewHodler.my_progress.getLayoutParams();
		
		linearParams.width = proportionWidth(totalValue);
		
		mViewHodler.my_progress.setLayoutParams(linearParams); 
		
		mViewHodler.tv_value.setText(""+totalValue);
		
		mViewHodler.tv_name.setText(shortname);
		mViewHodler.tv_name.setMovementMethod(ScrollingMovementMethod.getInstance()); 
		mViewHodler.tv_num.setText((arg0 + 1) + "");
		if(!isShare){
			arg1.setOnClickListener(new ViewOnClick(mGetDefectStatByOrg));
			mViewHodler.tv_name.setOnClickListener(new ViewOnClick(mGetDefectStatByOrg));
		}

		return arg1;
	}
	public class ViewOnClick implements OnClickListener{
		public GetDefectStatByOrgList mGetDefectStatByOrg;
		public ViewOnClick(GetDefectStatByOrgList mGetDefectStatByOrg){
			this.mGetDefectStatByOrg = mGetDefectStatByOrg;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub

			if(mGetDefectStatByOrg.value == 0){
				return;
			}

			if(arg0.getId() == R.id.tv_name){
				HashMap<String,Object> map = new HashMap<String,Object>();
				map.put("GetDefectStatByOrgList",mGetDefectStatByOrg);
				map.put("begTime",beginTime);
				map.put("endTime",endTime);
				map.put("org_id",org_id);
				map.put("org_type",org_type);
				ZTActivityUnit.switchTo(context, CardListActivity.class,
						map);
			}else{

				mCallBackDefectStatOrg.callBackDefect(mGetDefectStatByOrg.id,mGetDefectStatByOrg.org_type,code);
			}

		}
	}

	public class ViewHodler{
		public TextView tv_num ;
		public TextView tv_name;
		public TextView tv_value;
		public ProgressBar my_progress;
	}
	
	/**
	 * 算pro宽度
	 * @param value
	 * @return
	 */
	public int proportionWidth(int value){
		if(value == 0){
			return 0;
		}
		return (int)((value / Float.parseFloat((maxValue+""))) * width) < 10 ? 10 : (int)((value / Float.parseFloat((maxValue+""))) * width);
	}
	public void setBeginTime(String beginTime){
		this.beginTime = beginTime;
	}
	public void setCode(String code){
		this.code = code;
	}
	public void setEndTime(String endTime){
		this.endTime = endTime;
	}
	public void setOrgType(String org_type){
		this.org_type = org_type;
	}
	public void setOrg_id(String org_id){
		this.org_id = org_id;
	}
	public void setData(ArrayList<GetDefectStatByOrgList> mGetDefectStatByOrgList){
		if(mGetDefectStatByOrgList == null){
			this.mGetDefectStatByOrgList = new ArrayList<GetDefectStatByOrgList>();
		}else{
			this.mGetDefectStatByOrgList = mGetDefectStatByOrgList;
		}
		maxValue = 0;
//		for(GetDefectStatByOrgList getDectStatByOrgList : mGetDefectStatByOrgList){
//			maxValue += getDectStatByOrgList.value;
//		}
		maxValue = mGetDefectStatByOrgList.get(0).value;
		this.notifyDataSetChanged();
	}
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}
	
	@Override
	public boolean isEnabled(int position) {
		return false;
	}
	
}
