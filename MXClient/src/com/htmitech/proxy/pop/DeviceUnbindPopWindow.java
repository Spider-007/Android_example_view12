package com.htmitech.proxy.pop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.htmitech.emportal.R;
import com.htmitech.proxy.adapter.DeviceUserAdapter;
import com.htmitech.proxy.myenum.DeviceStateEnum;

/**
 * Created by yanxin on 2017-7-31.
 */

public class DeviceUnbindPopWindow extends PopupWindow {


    public View mMenuView;
    private ListView lv_choose;
    private ImageView iv_remove_choose;
    private Context context;
    public DeviceUnbindPopWindow(Context context, final DeviceUserAdapter mDeviceUserAdapter,View.OnClickListener mOnClickListener,int state) {

        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.dialog_device_unbind, null);
        RelativeLayout rl_dis = (RelativeLayout) mMenuView.findViewById(R.id.rl_dis);
        TextView tvSubmit = (TextView)mMenuView.findViewById(R.id.tv_submit);
        TextView tvContent = (TextView)mMenuView.findViewById(R.id.tv_content);
        if(state == DeviceStateEnum.UNBIND.appplyType){
            tvSubmit.setText("去解绑");
            tvContent.setText("您绑定的设备已达上限！\n" +
                    "通过解绑现有设备以添加新的设备。");
        }else if(state == DeviceStateEnum.PULLBLACK.appplyType){
            tvSubmit.setText("加入黑名单");
            tvContent.setText("拉黑后当前账号将无法登录该设备\n" +
                    "确定要进行此操作吗？");
        }else if(state == DeviceStateEnum.LOSS.appplyType){
            tvSubmit.setText("挂失");
            tvContent.setText("挂失后该设备将禁止任何账号登录\n" +
                    "确定要进行此操作吗？");
        }
        tvSubmit.setOnClickListener(mOnClickListener);
        rl_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceUnbindPopWindow.this.dismiss();
            }
        });
//		UserHasChooseAdapter mUserHasChooseAdapter = new UserHasChooseAdapter(context,userList,departmentsList);
//		lv_choose.setAdapter(mUserHasChooseAdapter);
        //设置按钮监听
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(com.htmitech.addressbook.R.style.AnimBottom);
//		//实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x90000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框


    }


    public void show(View view){
//        this.showAsDropDown(view);
        this.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}