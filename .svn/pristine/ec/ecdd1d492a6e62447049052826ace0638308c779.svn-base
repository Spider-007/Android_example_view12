package com.htmitech.proxy.activity;

/**
 * App下载分享
 */
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;


public class ScanForDownLoadActivity extends BaseFragmentActivity implements OnClickListener{

	private TextView tv_title;
	private WebView webView;
	private ImageView ib_back;
	private ImageButton title_left_button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan_for_down_load);
		tv_title = (TextView) findViewById(R.id.title_name);
		webView = (WebView) findViewById(R.id.webView);
		ib_back = (ImageView)findViewById(R.id.title_left_button);
		title_left_button = (ImageButton) findViewById(R.id.title_left_button);
		title_left_button.setOnClickListener(this);
		tv_title.setText("扫描下载");
		webView.loadUrl("http://220.231.22.55:8081/m/");
		ib_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.title_left_button:
				this.finish();
				break;
		}
	}
}
