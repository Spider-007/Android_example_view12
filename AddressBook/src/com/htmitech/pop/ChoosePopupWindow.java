package com.htmitech.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.htmitech.adapter.UserHasChooseAdapter;
import com.htmitech.addressbook.R;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;

import java.util.ArrayList;

public class ChoosePopupWindow extends PopupWindow {


	public View mMenuView;
	private ListView lv_choose;
	private ImageView iv_remove_choose;
	public ChoosePopupWindow(Context context, OnClickListener itemsOnClick,ArrayList<SYS_User> userList,ArrayList<SYS_Department> departmentsList) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.ht_fragment_choose_pop, null);
		lv_choose = (ListView) mMenuView.findViewById(R.id.lv_choose);
		iv_remove_choose = (ImageView) mMenuView.findViewById(R.id.iv_remove_choose);
//		UserHasChooseAdapter mUserHasChooseAdapter = new UserHasChooseAdapter(context,userList,departmentsList);
//		lv_choose.setAdapter(mUserHasChooseAdapter);
		iv_remove_choose.setOnClickListener(itemsOnClick);
		//设置按钮监听
		//设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		//设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		//设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		//设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		//设置SelectPicPopupWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.AnimBottom);
//		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x90000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		//mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}				
				return true;
			}
		});
	}
}
