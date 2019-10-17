package com.minxing.client.tab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.unit.DensityUtil;

public class MenuTabItem {

	private Context context;
	private String tag;	//每个标签
	private Intent intent;
	private Drawable imageRes;
	private String name;
	private View view;
	private TextView unreadTV;
	private ImageView unreadIM;
	private OnReClickListener onReClickListener;
	private BeforeTabChangeListener beforeTabChangeListener;
	private OnClickListener onClickListener;
	private ApplicationAllEnum mButtomEnum;
	private TextView tv;
	public AppInfo mAppInfo;

	public MenuTabItem(Context context, String tag, Intent intent, Drawable imageRes, String name,AppInfo mAppInfo,ApplicationAllEnum mButtomEnum) {
		this.context = context;
		this.tag = tag;
		this.intent = intent;
		this.imageRes = imageRes;
		this.name = name;
		this.mButtomEnum = mButtomEnum;
		this.mAppInfo = mAppInfo;
		view = LayoutInflater.from(this.context).inflate(R.layout.menu_tab_view, null);
		tv = (TextView) view.findViewById(R.id.menu_tab_name);
		tv.setText(name);
		unreadTV = (TextView) view.findViewById(R.id.main_tab_unread_tv);
		unreadIM = (ImageView) view.findViewById(R.id.main_tab_unread_image);
	}

	public void setDefaultDrawable(){
		if (imageRes == null) {
			imageRes = context.getResources().getDrawable(R.drawable.tab_appcenter);
		}
		tv.setCompoundDrawablesWithIntrinsicBounds(null, imageRes, null, null);
	}


	public void setDrawable(final Drawable selected, final Drawable unSelected){
		((Activity)context).runOnUiThread(new Runnable() {
			@Override
			public void run() {
				StateListDrawable drawable = new StateListDrawable();
				drawable.addState(new int[]{android.R.attr.state_selected},selected );
				// View.EMPTY_STATE_SET
				drawable.addState(new int[]{-android.R.attr.state_selected},unSelected );
				drawable.addState(new int[]{}, selected);
				tv.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
				drawable.setBounds(1, 1, DensityUtil.dip2px(context, 35), DensityUtil.dip2px(context, 35));
				tv.setCompoundDrawables(null, drawable, null, null);
//				tv.setBackgroundDrawable(imageRes);
//				tv.setPadding(0, DensityUtil.dip2px(context, 6), 0, 0);
				FrameLayout.LayoutParams marginLayoutParams=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
				marginLayoutParams.setMargins(0, 0,0,DensityUtil.dip2px(context, 0));
				tv.setLayoutParams(marginLayoutParams);
				tv.setCompoundDrawablePadding(DensityUtil.dip2px(context,0));
			}
		});

	}

	public ApplicationAllEnum getButtomEnum(){
		return mButtomEnum;
	}
	public void setmButtomEnum(ApplicationAllEnum mApplicationAllEnum){
		this.mButtomEnum = mApplicationAllEnum;
	}

	public void showNumberMarker(String number) {
		if(unreadTV != null){
			unreadTV.setVisibility(View.VISIBLE);
			unreadTV.setText(number);
		}
	}
	public void showNumberMarker() {
		if(unreadIM != null){
			unreadIM.setVisibility(View.VISIBLE);
		}
	}

	public void hideNumberMarker() {
		if(unreadTV != null){
			unreadTV.setVisibility(View.INVISIBLE);
			unreadTV.setText("");
		}
	}

	public void showMarker() {
		if(unreadIM != null){
			unreadIM.setVisibility(View.VISIBLE);
		}
	}

	public void hideMarker() {
		if(unreadIM != null){
			unreadIM.setVisibility(View.INVISIBLE);
		}
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public Drawable getImageRes() {
		return imageRes;
	}

	public void setImageRes(Drawable imageRes) {
		this.imageRes = imageRes;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public View getView() {
		return view;
	}

	public TextView getUnreadTV() {
		return unreadTV;
	}

	public ImageView getUnreadIM() {
		return unreadIM;
	}

	public AppInfo getmAppInfo() {
		return mAppInfo;
	}

	public void setmAppInfo(AppInfo mAppInfo) {
		this.mAppInfo = mAppInfo;
	}

	public OnReClickListener getOnReClickListener() {
		return onReClickListener;
	}

	public void setOnReClickListener(OnReClickListener onReClickListener) {
		this.onReClickListener = onReClickListener;
	}

	public BeforeTabChangeListener getBeforeTabChangeListener() {
		return beforeTabChangeListener;
	}

	public void setBeforeTabChangeListener(BeforeTabChangeListener beforeTabChangeListener) {
		this.beforeTabChangeListener = beforeTabChangeListener;
	}


	public interface OnReClickListener {
		public void onReClick(MenuTabItem menuTabItem);
	}

	public interface BeforeTabChangeListener {
		public void beforeTabChange(MenuTabItem menuTabItem);
	}
	
	public OnClickListener getOnClickListener() {
		return onClickListener;
	} 
	
	public void setOnClickListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
	
	public interface OnClickListener {
		public void onClick();
	}
}
