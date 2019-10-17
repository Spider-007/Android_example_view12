package com.htmitech.ztcustom.zt.chinarailway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.ztcustom.R;
import com.htmitech.ztcustom.zt.app.ZTCustomInit;
import com.htmitech.ztcustom.zt.domain.ZBBroadDown;
import com.htmitech.ztcustom.zt.domain.ZBBroadResult;
import com.htmitech.ztcustom.zt.interfaces.ObserverCallBack;
import com.htmitech.ztcustom.zt.pop.FunctionPopupWindow;
import com.htmitech.ztcustom.zt.thread.AnsynHttpRequest;
import com.htmitech.ztcustom.zt.unit.CHTTP;
import com.htmitech.ztcustom.zt.unit.ShareUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 伤损实况通知播报
 */
public class ZBRealTimeItemActivity extends Activity implements OnClickListener{
	private ListView listview;
	private List<Map<String, Object>> data;
	private ArrayList<ZBBroadDown> list;
	SimpleAdapter simpleAdapter;
	private ImageButton ibnBack;
	private String msgId;
	private ImageView function;
	private TextView tv_fn4_title_name;
	private boolean isShare;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zb_activity_broad_detal);
		listview = (ListView) findViewById(R.id.broad_detal_listview);
		function = (ImageView) findViewById(R.id.function);
		tv_fn4_title_name = (TextView) findViewById(R.id.tv_fn4_title_name);
		data = getData();
		menuWindow = new FunctionPopupWindow(this, this);
		function.setOnClickListener(this);
		int resource = R.layout.zb_broad_detal_item;
		// from表示数组中存储数据源中的Map中的各个Key
		String[] from = { "key", "value" };
		// to表示数据源中的Map中的各个Key对应的Value应该显示到哪些控件
		int[] to = { R.id.tv_key, R.id. tv_value };
		simpleAdapter = new SimpleAdapter(this, data, resource, from, to);
		listview.setAdapter(simpleAdapter);
		simpleAdapter.notifyDataSetChanged();
		
		ibnBack = (ImageButton) findViewById(R.id.ibn_fn5_back);
		ibnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		
		
		
	}
	
	private ZBBroadResult updateData() {
		Intent intent = getIntent();
		msgId = intent.getStringExtra("msgid");
		String userId = intent.getStringExtra("userId");
		isShare = getIntent().getBooleanExtra("flag_share", false);
		if(isShare){
			function.setVisibility(View.GONE);
		}
		ZBBroadResult br = new ZBBroadResult();
		br.setMsgid(msgId);
		br.setUserid((userId == null ||userId.equals(""))? ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId:userId);
		return br;
	}


	private List<Map<String, Object>> getData() {

		final List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		

		AnsynHttpRequest.request(this, updateData(),
				CHTTP.GETREALTIMEBROADDETAL, CHTTP.POST, new ObserverCallBack() {

					@Override
					public void success(String successMessage) {
						
						list = (ArrayList<ZBBroadDown>) JSON.parseArray(
								successMessage, ZBBroadDown.class);
						
						Map<String, Object> item;
						Log.e("tag", ""+successMessage);
						
						for (int i = 0; i < list.size(); i++) {
							item = new HashMap<String, Object>();
							item.put("key", list.get(i).getLabel());
							item.put("value", list.get(i).getValue());
							data.add(item);
						}
						simpleAdapter.notifyDataSetChanged();


					}

					@Override
					public void notNetwork() {
						// TODO Auto-generated method stub

					}

					@Override
					public void fail(String exceptionMessage) {
						// TODO Auto-generated method stub

					}
				});

		
		return data;

	}
	FunctionPopupWindow menuWindow;
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		if(arg0.getId() ==R.id.function ){
			if (!menuWindow.isShowing()) {
				menuWindow = new FunctionPopupWindow(this, this);
				menuWindow.setLinearVisibility();
				function.setBackgroundResource(R.drawable.zt_htmitech_shezhi);
				int popupWidth = menuWindow.mMenuView.getMeasuredWidth();
				int popupHeight = menuWindow.mMenuView.getMeasuredHeight();

				int[] location = new int[2];
				function.getLocationOnScreen(location);
				// 显示窗口
				menuWindow.showAtLocation(function, Gravity.NO_GRAVITY,
						(location[0] + function.getWidth() / 2) - popupWidth
								/ 2, location[1] - popupHeight); // 设置layout在PopupWindow中显示的位置
				menuWindow.update();
			} else {
				function.setBackgroundResource(R.drawable.zt_htmitech_shezhi_1);
				menuWindow.dismiss();
			}

		}else if(arg0.getId() ==R.id.function){
			ShareUnit
					.ShareListener(
							this,
							"分享" + tv_fn4_title_name.getText().toString(),
							"http://114.112.89.94:8081/ZTCloudAPI/MetroImage/sharereport.png",
							msgId+"|"+ ZTCustomInit.get().getmCache().getCisAccountDetail().cisAccountId,"EE");
		}
	}

}
