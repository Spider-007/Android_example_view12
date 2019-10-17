package com.htmitech.htworkflowformpluginnew.weight;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.htmitech.api.BookInit;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.htworkflowformpluginnew.activity.WorkFlowFormDetalActivity;
import com.htmitech.proxy.dao.AppliationCenterDao;
//import com.minxing.kit.api.MXAPI;

/**
 * Created by htrf-pc on 2016/7/11.
 */
public class PhoneClickText extends ClickableSpan {
    private Context context;
    private View.OnClickListener onClick;
    private String phone = "";
    private String name;
    private String userid;
    public PhoneClickText(Context context, String phone, String name, View.OnClickListener onClick, String userid) {
        this.context = context;
        this.phone = phone;
        this.name = name;
        this.onClick = onClick;
        this.userid = userid;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
//        //设置文本的颜色
//        ds.setColor(context.getResources().getColor(R.color.ht_hred_title));
        String UserID =  new AppliationCenterDao(context).getLoginName(userid);
        if(((WorkFlowFormDetalActivity)context).com_workflow_mobileconfig_IM_enabled == 0
                ||!(new AppliationCenterDao(context).isEmiUser(userid))
                ||UserID.equalsIgnoreCase("admin")
                ||UserID.equalsIgnoreCase(OAConText.getInstance(context).login_name)){
            ds.setColor(context.getResources().getColor(com.htmitech.addressbook.R.color.buttom_color));
        }else {
            ds.setColor(context.getResources().getColor(com.htmitech.addressbook.R.color.ht_hred_title));
            if(BookInit.getInstance().getmApcUserdefinePortal().getUsing_color_style() == 3){//红色风格
                ds.setColor(Color.parseColor("#DC5656"));
            }
        }

//        //超链接形式的下划线，false 表示不显示下划线，true表示显示下划线
        ds.setUnderlineText(false);
    }



    @Override
    public void onClick(View widget) {
        if(phone != null && !phone.trim().equals("")){
            widget.setTag(name + ":" + phone);
//            widget.setPadding(0,5,0,5);
            if(widget != null)
                onClick.onClick(widget);
        }
    }
}
