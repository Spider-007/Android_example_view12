package com.htmitech.emportal.ui.plugin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.htmitech.emportal.ui.document.DocumentMainActivity;
import com.htmitech.emportal.ui.plugin.oamodel.OAModelFragmentActivity;
import com.minxing.kit.api.bean.MXAppInfo;
import com.minxing.kit.plugin.android.MXPlugin;

public class otherplugin extends MXPlugin {

//	@Override
//	public void start(Context arg0, String arg1, String arg2) {
//		// TODO Auto-generated method stub
////		super.start(arg0, arg1, arg2);
//
//		
//		//跳转到首页待办列表，并完成数据筛选
//		Log.d("oaplugin", arg1);
//		
//		Intent intent = new Intent();
//		intent.setClass(arg0, OAModelFragmentActivity.class);
//		Bundle bundle = new Bundle();
//		bundle.putString("ModelName", arg1);
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

		if (arg2.equals("Other_Document"))
		{
			Intent intent = new Intent(arg0,
					DocumentMainActivity.class);
			arg0.startActivity(intent);
		}
//		else if (arg2 == "")
//		{
//
//		}



	}
	
}
