package com.htmitech.app;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.htmitech.addressbook.R;
import com.htmitech.domain.PeopleMessage;

/**
 * Created by htrf-pc on 2016/7/11.
 */
public class PhoneClickText extends ClickableSpan {
    private Context context;
    private View.OnClickListener onClick;
    private String phone = "";
    private String name;
    public PhoneClickText(Context context,String phone,String name,View.OnClickListener onClick) {
        this.context = context;
        this.phone = phone;
        this.name = name;
        this.onClick = onClick;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
//        //设置文本的颜色
        ds.setColor(context.getResources().getColor(R.color.ht_hred_title));
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
