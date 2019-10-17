package com.htmitech.emportal.ui.helppage;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.htmitech.emportal.R;

import cn.feng.skin.manager.base.BaseActivity;
import htmitech.com.componentlibrary.unit.PreferenceUtils;

public class HelpActivity extends BaseActivity {
	public static final String FORM_FLOW_HELPPAGE = "form_flow";
	public static final String DAIYIBAN_HELPPAGE = "daiyiban";
	public static final String CALL_HELPPAGE = "call";
	public static final String CIRCLE_HELPPAGE = "circle";
	public static final String CENTER_HELPPAGE = "center";

	public static final String CURRENT_HELPPAGE = "current_helppage";

	private int currentImg = 0;
	ImageView imgHelpPage = null;
	Button btnHelpPage = null;

	private int[] currentPic = null;
	String current = null;

	Bitmap mBgBitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.help_page);

		current = getIntent().getStringExtra(CURRENT_HELPPAGE);
		if (current == null)
			return;
		HelpActivity.this.finish();//根据需求暂时调整@ todo 2017年12月27日15:49:43
		imgHelpPage = (ImageView) findViewById(R.id.image_helppage);
		btnHelpPage = (Button) findViewById(R.id.btn_helppage);
		btnHelpPage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (current.equals(CENTER_HELPPAGE) && currentImg == 0) {
					return;
				}
				HelpActivity.this.finish();
			}
		});

		if (current.equals(FORM_FLOW_HELPPAGE)){
			currentPic = new int[] { R.drawable.img_oa_matterform01, R.drawable.img_oa_matterform02,
					R.drawable.img_oa_matterform03};

		}else if (current.equals(DAIYIBAN_HELPPAGE)) {

			if (PreferenceUtils.isLoginCall(HelpActivity.this)) {
				PreferenceUtils.setLoginCall(HelpActivity.this, false);
				currentPic = new int[] { R.drawable.img_oa_todo_first01,
						R.drawable.img_oa_todo_first02, R.drawable.img_oa_todo_first03,
						R.drawable.img_oa_todo_first04};

			}

		}
		else if (current.equals(CALL_HELPPAGE))
			currentPic = new int[] { R.drawable.call };
		// else if (current.equals(CIRCLE_HELPPAGE))
		// currentPic = new int[] {R.drawable.circle_1, R.drawable.circle_2};
		// else if (current.equals(CENTER_HELPPAGE)) {
		// currentPic = new int[] {R.drawable.center_1, R.drawable.center_2,
		// R.drawable.center_3};
		// }
		imgHelpPage.setBackgroundResource(currentPic[0]);

		imgHelpPage.setOnClickListener(new OnClickListener() {
			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			@Override
			public void onClick(View v) {
				if (currentImg == (currentPic.length - 1)) {
					HelpActivity.this.finish();
				}else{
					// imgHelpPage.setBackgroundResource(currentPic[++currentImg]);
					if (mBgBitmap != null && !mBgBitmap.isRecycled()) {
						mBgBitmap.recycle();
						mBgBitmap = null;
						System.gc();
					}
					mBgBitmap = BitmapFactory.decodeResource(
							HelpActivity.this.getResources(),
							currentPic[++currentImg]);
					imgHelpPage.setBackground(new BitmapDrawable(
							HelpActivity.this.getResources(), mBgBitmap));
				}

			}
		});
	}

}
