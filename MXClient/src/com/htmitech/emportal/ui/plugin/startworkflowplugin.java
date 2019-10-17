package com.htmitech.emportal.ui.plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.htmitech.emportal.ui.detail.StartDetailActivity;
import com.minxing.kit.api.bean.MXAppInfo;
import com.minxing.kit.plugin.android.MXPlugin;

public class startworkflowplugin extends MXPlugin {
//
//	@Override
//	public void start(Context arg0, String arg1, String arg2) {
//		// TODO Auto-generated method stub
////		super.start(arg0, arg1, arg2);
//		
//		//跳转到首页待办列表，并完成数据筛选
//		Log.d("oaplugin", arg1);
//		
//		Intent intent = new Intent();
//		intent.setClass(arg0, StartDetailActivity.class);
//		Bundle bundle = new Bundle();
//		bundle.putString("flowid", arg1);
//		bundle.putString("DocType", "");
//		
//		
//		
//		if (arg1.equalsIgnoreCase("qj"))
//			bundle.putString("otherparameter", "请假管理");
//		else if (arg1.equalsIgnoreCase("cc"))
//			bundle.putString("otherparameter", "出差管理");
//		intent.putExtras(bundle);  
//		
//		arg0.startActivity(intent);
//		
//		
//	}
	@Override
	public void start(Context arg0, MXAppInfo arg1, String arg2, String arg3) {
		// TODO Auto-generated method stub
		super.start(arg0, arg1, arg2, arg3);
		Intent intent = new Intent();
		intent.setClass(arg0, StartDetailActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("flowid", arg2);
		bundle.putString("DocType", "");
		
		
		
		if (arg2.equalsIgnoreCase("qj"))
			bundle.putString("otherparameter", "请假管理");
		else if (arg2.equalsIgnoreCase("cc"))
			bundle.putString("otherparameter", "出差管理");
		intent.putExtras(bundle);  
		
		arg0.startActivity(intent);
	}
	
}
