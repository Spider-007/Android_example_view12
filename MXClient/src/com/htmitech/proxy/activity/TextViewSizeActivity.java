package com.htmitech.proxy.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.htmitech.addressbook.ClassEvent;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.app.widget.FontSliderBar;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.dao.ParamDao;
import com.htmitech.unit.ConfigStyleUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 设置字体大小
 */
public class TextViewSizeActivity extends BaseFragmentActivity implements View.OnClickListener {
    private FontSliderBar mFontSliderBar;
    private TextView tv_one;
    private TextView tv_two;
    private TextView tv_three;
    private TextView daibantopTabIndicator_bbslist;
    private ImageView btn_daiban_person;
    private final static float DEFAULT_TEXT_SIZE = 14; //默认进入的初始化字体大小
    private float textSize = 1;
    private float one = DEFAULT_TEXT_SIZE;
    private float two = DEFAULT_TEXT_SIZE;
    private float three = DEFAULT_TEXT_SIZE;
    private float titleSize = DEFAULT_TEXT_SIZE;
    private TextView default_tv;
    private ImageView iv_and_avatar;
    private AppliationCenterDao mAppliationCenterDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textSize = textSize + BookInit.getInstance().getmApcUserdefinePortal().getUsing_font_style() * 0.1f;
        Constant.TEXTVIEWSIXE = textSize;
        mAppliationCenterDao = new AppliationCenterDao(this);
        setContentView(com.htmitech.addressbook.R.layout.ht_textview_size);
        mFontSliderBar = (FontSliderBar) findViewById(com.htmitech.addressbook.R.id.fdb);
        tv_one = (TextView) findViewById(com.htmitech.addressbook.R.id.tv_one);
        tv_two = (TextView) findViewById(com.htmitech.addressbook.R.id.tv_two);
        tv_three = (TextView) findViewById(com.htmitech.addressbook.R.id.tv_three);
        default_tv = (TextView) findViewById(com.htmitech.addressbook.R.id.default_tv);
        daibantopTabIndicator_bbslist = (TextView) findViewById(com.htmitech.addressbook.R.id.daibantopTabIndicator_bbslist);
        btn_daiban_person = (ImageView) findViewById(com.htmitech.addressbook.R.id.btn_daiban_person);
        iv_and_avatar = (ImageView) findViewById(com.htmitech.addressbook.R.id.iv_and_avatar);
        btn_daiban_person.setOnClickListener(this);
        titleSize = daibantopTabIndicator_bbslist.getTextSize();
        ParamDao mParamDao = new ParamDao(this);
        try {
            HashMap<String, String> stringStringHashMap = mParamDao.getParamHashMap();
            if (!TextUtils.isEmpty(stringStringHashMap.get(Constant.POSITION_MINUTES)))
                Constant.minutes = Integer.parseInt(stringStringHashMap.get(Constant.POSITION_MINUTES));

        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        setTextSize();
        try {
            BookInit.getInstance().getPhotoBitMap(this, iv_and_avatar, default_tv);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mFontSliderBar.setResponseOnTouch(new FontSliderBar.ResponseOnTouch() {

            @Override
            public void onTouchResponse(int cur_sections) {
                float textSize = 1;
                textSize = textSize + (cur_sections - 1) * 0.1f;
                Constant.TEXTVIEWSIXE = textSize;
//                PreferenceUtils.saveTextSize(cur_sections);
                BookInit.getInstance().getmApcUserdefinePortal().using_font_style = cur_sections - 1;
                mAppliationCenterDao.saveUserDefinePortal(BookInit.getInstance().getmApcUserdefinePortal());
                mAppliationCenterDao.saveCurrentPortalMessage(BookInit.getInstance().getmApcUserdefinePortal().using_font_style + "", BookInit.getInstance().getmApcUserdefinePortal().using_home_style + "", BookInit.getInstance().getmApcUserdefinePortal().portal_id);
                ConfigStyleUtil.changeTextSize(TextViewSizeActivity.this);
                setTextSize();
            }

        });
        ArrayList<String> volume_sections = new ArrayList<String>();
        volume_sections.add("小");
        volume_sections.add("标准");
        volume_sections.add("");
        volume_sections.add("");
        volume_sections.add("大");
        mFontSliderBar.initData(volume_sections);
        int index = BookInit.getInstance().getmApcUserdefinePortal().using_font_style + 1;
        if (index == -1) {
            index = 0;
        }
        mFontSliderBar.setProgress(index);
    }

    public void setTextSize() {
//        tv_one.setTextSize(DensityUtil.px2dip(this, one) * (Constant.TEXTVIEWSIXE));
//        tv_two.setTextSize(DensityUtil.px2dip(this, two) * (Constant.TEXTVIEWSIXE));
//        tv_three.setTextSize(DensityUtil.px2dip(this, three) * (Constant.TEXTVIEWSIXE));

        tv_one.setTextSize(TypedValue.COMPLEX_UNIT_SP, one * (Constant.TEXTVIEWSIXE));
        tv_two.setTextSize(TypedValue.COMPLEX_UNIT_SP, two * (Constant.TEXTVIEWSIXE));
        tv_three.setTextSize(TypedValue.COMPLEX_UNIT_SP, three * (Constant.TEXTVIEWSIXE));
//        daibantopTabIndicator_bbslist.setTextSize(DensityUtil.px2sp(this,titleSize )* (Constant.TEXTVIEWSIXE));
    }


    @Override
    public void onClick(View v) {
        showDialog();
        setDialogValue("更换字体大小...");
        BookInit.getInstance().getmCallbackMX().updatePortalSizeStyle(BookInit.getInstance().getmApcUserdefinePortal().using_home_style + "", BookInit.getInstance().getmApcUserdefinePortal().using_color_style + "", BookInit.getInstance().getmApcUserdefinePortal().getUsing_font_style() + "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                ClassEvent mClassEvent = new ClassEvent();
                mClassEvent.msg = "TextViewSize";
                EventBus.getDefault().post(mClassEvent);
                dismissDialog();
                TextViewSizeActivity.this.finish();
            }
        }).start();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        showDialog();
        setDialogValue("更换字体大小...");
        BookInit.getInstance().getmCallbackMX().updatePortalSizeStyle(BookInit.getInstance().getmApcUserdefinePortal().using_home_style + "", BookInit.getInstance().getmApcUserdefinePortal().using_color_style + "", BookInit.getInstance().getmApcUserdefinePortal().getUsing_font_style() + "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                ClassEvent mClassEvent = new ClassEvent();
                mClassEvent.msg = "TextViewSize";
                EventBus.getDefault().post(mClassEvent);
                dismissDialog();
                TextViewSizeActivity.this.finish();
            }
        }).start();
        return super.onKeyDown(keyCode, event);

    }
}