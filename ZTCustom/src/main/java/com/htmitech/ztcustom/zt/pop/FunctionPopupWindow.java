package com.htmitech.ztcustom.zt.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.ztcustom.R;


public class FunctionPopupWindow extends PopupWindow {

	private static TextView tv_detail_data;
	private static TextView tv_share;
	public View mMenuView;
	private ImageView iv_detail_data;
	private ImageView iv_share;
	public static Context context;
	public LinearLayout layout_detail;
	public LinearLayout ll_tl;
	private static TextView tv_tl;
	public ImageView iv_tl;
	public FunctionPopupWindow(Context context, OnClickListener itemsOnClick) {
		super(context);
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.zt_layout_function1, null);
		tv_detail_data = (TextView) mMenuView.findViewById(R.id.tv_detail_data);
		tv_share = (TextView) mMenuView.findViewById(R.id.tv_share);
		iv_detail_data = (ImageView) mMenuView
				.findViewById(R.id.iv_fun_detail_data);
		ll_tl = (LinearLayout) mMenuView.findViewById(R.id.ll_tl);
		iv_share = (ImageView) mMenuView.findViewById(R.id.iv_share);
		iv_tl = (ImageView) mMenuView.findViewById(R.id.iv_tl);
		tv_tl = (TextView) mMenuView.findViewById(R.id.tv_tl);
		layout_detail = (LinearLayout) mMenuView.findViewById(R.id.layout_detail);
		iv_detail_data.setOnClickListener(itemsOnClick);
		iv_share.setOnClickListener(itemsOnClick);
		iv_tl.setOnClickListener(itemsOnClick);
		// 设置按钮监听
		// but_delete.setOnClickListener(itemsOnClick);
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		mMenuView.measure(View.MeasureSpec.UNSPECIFIED,
				View.MeasureSpec.UNSPECIFIED);
		// 设置SelectPicPopupWindow弹出窗体可点击
		// this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体动画效果
		// //实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		tv_share.setVisibility(View.INVISIBLE);
		tv_detail_data.setVisibility(View.INVISIBLE);
		tv_tl.setVisibility(View.INVISIBLE);
		iv_detail_data.startAnimation(moveToViewLocation());
		iv_share.startAnimation(moveToViewLocation());
		iv_tl.startAnimation(moveToViewLocation());

	}
	public void setLayoutTLVisibility(){
		ll_tl.setVisibility(View.VISIBLE);
	}
	public void setLayoutTLINVISIBLE(){
		ll_tl.setVisibility(View.INVISIBLE);
	}

	public void setLinearVisibility(){
		layout_detail.setVisibility(View.INVISIBLE);
	}
	// 暂时不实现退出动画
	public void moveButton() {
		iv_detail_data.startAnimation(moveToViewBottom());
		iv_share.startAnimation(moveToViewBottom());
	}

	/**
	 * 从控件所在位置移动到控件的底部
	 * 
	 * @return
	 */
	public static AnimationSet moveToViewBottom() {
		AnimationSet set = new AnimationSet(true);
		TranslateAnimation mHiddenAction = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
				0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 1.0f);
		set.addAnimation(mHiddenAction);
		AlphaAnimation alphaAnim = new AlphaAnimation(0.0f, 1.0f);
		set.addAnimation(alphaAnim);
		set.setDuration(500);
		mHiddenAction.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				tv_detail_data.setAnimation(AnimationUtils.makeInAnimation(
						context, false));
				tv_share.setAnimation(AnimationUtils.makeInAnimation(context,
						false));
				tv_tl.setAnimation(AnimationUtils.makeInAnimation(context,
						false));
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		return set;
	}

	/**
	 * 从控件的底部移动到控件所在位置
	 * 
	 * @return
	 */
	public static AnimationSet moveToViewLocation() {

		AnimationSet set = new AnimationSet(true);
//		TranslateAnimation mHiddenAction = new TranslateAnimation(
//				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//				0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
//				Animation.RELATIVE_TO_SELF, 0.0f);
		
//		TranslateAnimation mTranslateAnimation = new TranslateAnimation(
//				Animation.RELATIVE_TO_SELF, 0.0F,
//				Animation.RELATIVE_TO_SELF,0.0F, 
//				Animation.RELATIVE_TO_SELF, 0.0F,
//				Animation.RELATIVE_TO_SELF, -300F);// 当前位置移动到指定位置		
		Animation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// 设置尺寸变化动画
		Animation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		
		scaleAnimation.setDuration(1000); 
		alphaAnimation.setDuration(1000);
		set.addAnimation(scaleAnimation);
		set.addAnimation(alphaAnimation);
		set.setDuration(500);
		scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				tv_detail_data.setVisibility(View.VISIBLE);
				tv_share.setVisibility(View.VISIBLE);
				tv_tl.setVisibility(View.VISIBLE);
				tv_detail_data.setAnimation(AnimationUtils.makeInAnimation(
						context, false));
				tv_share.setAnimation(AnimationUtils.makeInAnimation(context,
						false));
				tv_tl.setAnimation(AnimationUtils.makeInAnimation(context,
						false));
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		// TranslateAnimation mHiddenAction = new
		// TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
		// Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
		// 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		// mHiddenAction.setDuration(500);
		// mHiddenAction.setAnimationListener(new Animation.AnimationListener()
		// {
		// @Override
		// public void onAnimationStart(Animation animation) {
		//
		// }
		//
		// @Override
		// public void onAnimationEnd(Animation animation) {
		// tv_detail_data.setVisibility(View.VISIBLE);
		// tv_share.setVisibility(View.VISIBLE);
		// tv_detail_data.setAnimation(AnimationUtils.makeInAnimation(context,
		// false));
		// tv_share.setAnimation(AnimationUtils.makeInAnimation(context,
		// false));
		// }
		//
		// @Override
		// public void onAnimationRepeat(Animation animation) {
		//
		// }
		// });
		return set;
	}

}
