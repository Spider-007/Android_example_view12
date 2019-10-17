package com.htmitech_updown.updownloadmanagement.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.updownloadmanagement.R;

import htmitech.com.componentlibrary.unit.PreferenceUtils;


/**
 * Created by Administrator on 2018/7/13.
 */
public class NetPopupWindow extends PopupWindow {

    private Context mContext;
    private final View view;

    public NetPopupWindow(Context context, final NetClickListener netClickListener) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.net_popupwindow_layout, null);
        this.setContentView(view);
        //自定义基础，设置我们显示控件的宽，高，焦点，点击外部关闭PopupWindow操作
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //更新试图
        this.update();
        //设置背景
        ColorDrawable colorDrawable = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(colorDrawable);
        final TextView tvAlwaysUpload = (TextView) view.findViewById(R.id.tv_net_popwindow_always_upload);
        tvAlwaysUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.saveUploadBigFileNet(mContext, 1);//总是允许
                netClickListener.onNetClick("always");
                dismiss();
            }
        });
        TextView tvOnceUpload = (TextView) view.findViewById(R.id.tv_net_popwindow_once_upload);
        tvOnceUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.saveUploadBigFileNet(mContext, 2);//一次允许
                netClickListener.onNetClick("once");
                dismiss();
            }
        });
        TextView tvWifiUpload = (TextView) view.findViewById(R.id.tv_net_popwindow_wifi_upload);
        tvWifiUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceUtils.saveUploadBigFileNet(mContext, 3);//仅wifi
                netClickListener.onNetClick("wifi");
                dismiss();

            }
        });
    }
}
