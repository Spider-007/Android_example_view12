package com.htmitech.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.htmitech.addressbook.R;
import com.htmitech.app.Constant;
import com.htmitech.app.PhoneClickText;
import com.htmitech.app.widget.UserMessageScrollView;
import com.htmitech.unit.DensityUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CallPhonePopupWindow extends PopupWindow {


    private LinearLayout ll_add_call_phone;
    private UserMessageScrollView mUserMessageScrollView;
    private View mMenuView;
    private Context context;
    public CallPhonePopupWindow(Context context, OnClickListener itemsOnClick, String phone, String landline, String homePhone) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.ht_frament_call_phone, null);
        ll_add_call_phone = (LinearLayout) mMenuView.findViewById(R.id.ll_add_call_phone);
        mUserMessageScrollView = (UserMessageScrollView) mMenuView.findViewById(R.id.set_scrollview);
        String[] phoneList = null;
        String[] landlineList = null;
        String[] homePhoneList = null;

        LinkedHashMap<String,String> phoneArrayList = new LinkedHashMap<String,String>();

        if (phone.contains("，")) {
            phoneList = phone.split("，");
        }if (phone.contains(",")) {
            phoneList = phone.split(",");
        } else if (phone.contains(";")) {
            phoneList = phone.split(";");
        } else if (phone.contains(" ")) {
            phoneList = phone.split(" ");
        } else {
            phoneList = new String[1];
            phoneList[0] = phone;
        }

        if (landline.contains("，")) {
            landlineList = landline.split("，");
        }if (landline.contains(",")) {
            landlineList = landline.split(",");
        } else if (landline.contains(";")) {
            landlineList = landline.split(";");
        } else if (landline.contains(" ")) {
            landlineList = landline.split(" ");
        } else {
            landlineList = new String[1];
            landlineList[0] = landline;
        }

        if (homePhone.contains("，")) {
            homePhoneList = homePhone.split("，");
        }if (homePhone.contains(",")) {
            homePhoneList = homePhone.split(",");
        } else if (homePhone.contains(";")) {
            homePhoneList = homePhone.split(";");
        } else if (homePhone.contains(" ")) {
            homePhoneList = homePhone.split(" ");
        } else {
            homePhoneList = new String[1];
            homePhoneList[0] = homePhone;
        }

        for (String s : phoneList) {
            if (!s.equals("") && !s.contains("*")){
                if(Constant.com_addressbook_mobileconfig_mobile_phone_secrecy.equals("2")){
                    phoneArrayList.put(DensityUtil.cellPhone(s, "mobile_phone"), DensityUtil.cellPhone(s, "mobile_phone"));
                }else{
                    phoneArrayList.put(DensityUtil.cellPhone(s, "mobile_phone"), s);
                }
            }

        }
        for (String s : landlineList) {
            if (!s.equals("") && !s.contains("*")){
                if(Constant.com_addressbook_mobileconfig_office_phone_secrecy.equals("2")){
                    phoneArrayList.put(DensityUtil.cellPhone(s, "office_phone"), DensityUtil.cellPhone(s, "office_phone"));
                }else{
                    phoneArrayList.put(DensityUtil.cellPhone(s, "office_phone"), s);
                }
            }

        }

        for (String s : homePhoneList) {
            if (!s.equals("") && !s.contains("*")){
                if(Constant.com_addressbook_mobileconfig_home_phone_secrecy.equals("2")){
                    phoneArrayList.put(DensityUtil.cellPhone(s, "home_phone"), DensityUtil.cellPhone(s, "home_phone"));
                }else{
                    phoneArrayList.put(DensityUtil.cellPhone(s, "home_phone"), s);
                }
            }

        }

        if(phoneArrayList.size() > 6){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,DensityUtil.dip2px(context,360));
            params.setMargins(DensityUtil.dip2px(context, 5), 0, DensityUtil.dip2px(context, 5), 0);
            mUserMessageScrollView.setLayoutParams(params);
        }
        for (String p : phoneArrayList.keySet()) {
            Button button = (Button) inflater.inflate(R.layout.ht_adapter_call_phone_item, null);
            button.setText(p+"");
            button.setTag("" + phoneArrayList.get(p));
            button.setOnClickListener(itemsOnClick);
            if(ll_add_call_phone.getChildCount() == 0){
                button.setBackgroundColor(context.getResources().getColor(R.color.white));
            }else{
                button.setBackgroundResource(R.drawable.activity_text_buttom_back);
            }
            ll_add_call_phone.addView(button);

        }


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

        this.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = ((Activity) CallPhonePopupWindow.this.context) .getWindow().getAttributes();
                lp.alpha = 1f;
                ((Activity) CallPhonePopupWindow.this.context) .getWindow().setAttributes(lp);
            }
        });
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = 0.7f;
        ((Activity)context).getWindow().setAttributes(lp);
    }

}
