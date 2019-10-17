package com.htmitech.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.htmitech.addressbook.R;


public class LoadingPopupWindow extends PopupWindow {

	private View mMenuView;
	private TextView tips_loading_msg;
	private ProgressBar progress_1,progress_;
	public LoadingPopupWindow(final Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.dialog_loading, null);
		tips_loading_msg = (TextView) mMenuView.findViewById(R.id.tips_loading_msg);
		progress_1 = (ProgressBar) mMenuView.findViewById(R.id.progress_1);
		progress_ = (ProgressBar) mMenuView.findViewById(R.id.progress_);
		//设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		//设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
//		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});

	}

	public void show(View view){
		this.showAtLocation(view, Gravity.LEFT | Gravity.TOP, view.getWidth() / 2, (int)(view.getHeight() / 1.5));
	}

	public void shows(View view){
		this.showAtLocation(view, Gravity.TOP | Gravity.START, view.getWidth() / 2, (int)(view.getHeight() / 1.5));
	}

	public void setTextVisible(){
		progress_1.setVisibility(View.VISIBLE);
		progress_.setVisibility(View.GONE);
		tips_loading_msg.setVisibility(View.VISIBLE);
	}
}
