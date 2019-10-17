package com.htmitech.ztcustom.zt.adapter;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.interfaces.CallBackDynamicAdapter;
import com.htmitech.ztcustom.zt.util.DensityUtil;

/**
 * 现存伤损动态排名-工务段 adapter
 * 
 * @author htrf-pc
 * 
 */
public class DynamicGridAdapter extends BaseAdapter {
	public Context context;
	public JSONArray array;
	public LayoutInflater inflater;
	private int width;
	private int maxValue;
	private CallBackDynamicAdapter mCallBackDynamicAdapter;
	private String key;
	private boolean isShare;
	public DynamicGridAdapter(Context context, JSONArray array,CallBackDynamicAdapter mCallBackDynamicAdapter,String key,boolean isShare) {
		this.context = context;
		if(key.equals("sum")){
			key = "totalvalue";
		}
		this.key = key;
		if(array == null){
			array = new JSONArray();
		}else{
			this.array = array;
		}
		this.mCallBackDynamicAdapter = mCallBackDynamicAdapter;
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
			widthJa  = DensityUtil.dip2px(context, 122);
		}
		width = width - widthJa;
		this.isShare = isShare;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(array == null){
			return 0;
		}
		return array.size() - 1;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return array.get(arg0);
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
		JSONObject mJSONObject = array.getJSONObject(arg0);
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
		int totalValue = mJSONObject.getIntValue(key);
		
		String order = mJSONObject.getString("order");

		Log.e("YJH", "getView->order: "+ order);
		String shortname = mJSONObject.getString("shortname");
		Log.e("YJH", "getView->shortname: "+ shortname);
		LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) mViewHodler.my_progress.getLayoutParams();
		
		linearParams.width = proportionWidth(totalValue);
		
		mViewHodler.my_progress.setLayoutParams(linearParams); 
		
		mViewHodler.tv_value.setText(""+totalValue);
		
		mViewHodler.tv_name.setText(shortname);
		mViewHodler.tv_name.setMovementMethod(ScrollingMovementMethod.getInstance()); 
		mViewHodler.tv_num.setText(order);
		if(!isShare)
			arg1.setOnClickListener(new ViewOnClick(mJSONObject.getString("orgid"),mJSONObject.getString("ranktype")));
		return arg1;
	}
	public class ViewOnClick implements OnClickListener{
		public String orgId;
		public String ranktype;
		public ViewOnClick(String orgId,String ranktype){
			this.orgId = orgId;
			this.ranktype = ranktype;
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			mCallBackDynamicAdapter.callOrgId(orgId,ranktype);
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
		return (int)((value / Float.parseFloat((maxValue+""))) * width);
	}
	
	public void setData(JSONArray array,String key){
		if(array == null){
			array = new JSONArray();
		}else{
			this.array = array;
		}
		if(key.equals("sum")){
			key = "totalvalue";
		}
		this.key = key;
		maxValue = array.getJSONObject(0).getIntValue(key);
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
