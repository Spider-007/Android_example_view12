package com.htmitech.proxy.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.minxing.kit.api.MXAPI;
import com.minxing.kit.api.bean.MXError;
import com.minxing.kit.api.callback.MXApiCallback;

import cn.feng.skin.manager.base.BaseActivity;

public class ConnectionActivity extends BaseActivity{

	private ImageView iv_kfxj;
	private ImageView iv_kfzsy;
	private ImageView iv_htzf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection);
		TextView phone=(TextView) findViewById(R.id.tv_phone);
		TextView title = (TextView) findViewById(R.id.title_name);
		iv_kfxj = (ImageView) findViewById(R.id.iv_kfxj);
		iv_kfzsy = (ImageView) findViewById(R.id.iv_kfzsy);
		iv_htzf = (ImageView) findViewById(R.id.iv_htzf);
		iv_kfxj.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] loginNames = new String[]{"kfxj"};
				// TODO Auto-generated method stub
				MXAPI.getInstance(ConnectionActivity.this).chat(loginNames,
						new MXApiCallback() {
							@Override
							public void onLoading() {
								// TODO Auto-generated method stub

							}

							@Override
							public void onFail(MXError arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub

							}
						});
			}
		});

		iv_kfzsy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] loginNames = new String[]{"kfzsy"};
				// TODO Auto-generated method stub
				MXAPI.getInstance(ConnectionActivity.this).chat(loginNames,
						new MXApiCallback() {
							@Override
							public void onLoading() {
								// TODO Auto-generated method stub

							}

							@Override
							public void onFail(MXError arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub

							}
						});
			}
		});

		iv_htzf.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String[] loginNames = new String[]{"kfzf"};
				// TODO Auto-generated method stub
				MXAPI.getInstance(ConnectionActivity.this).chat(loginNames,
						new MXApiCallback() {
							@Override
							public void onLoading() {
								// TODO Auto-generated method stub

							}

							@Override
							public void onFail(MXError arg0) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess() {
								// TODO Auto-generated method stub

							}
						});
			}
		});
		title.setText("联系客服");
		phone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();

				//系统默认的action，用来打开默认的电话界面
				intent.setAction("android.intent.action.DIAL");

				//需要拨打的号码

				intent.setData(Uri.parse("tel:"+"010-88607800-826"));//4007004500
				startActivity(intent);
				
			}
		});
		findViewById(R.id.title_left_button).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finishWithAnimation();
				
			}
		});
		
		findViewById(R.id.system_email).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@htmitech.com"});
				intent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
				intent.putExtra(Intent.EXTRA_TEXT, "TEXT");
				intent.setType("text/plain");
				startActivity(Intent.createChooser(intent, "选择客户端程序"));
				
				
			}
		});
		
		
		
		
	}
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_phone:
//			System.out.println("111111111111111111111111111111111111111111");
//			
//			break;
//		case R.id.title_left_button:
//			finishWithAnimation();;
//			break;
//		default:
//			break;
//		}
//		
//	}
	protected void finishWithAnimation(){
		super.finish();
		overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
	}
	

}
