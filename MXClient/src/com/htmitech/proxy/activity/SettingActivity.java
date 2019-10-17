package com.htmitech.proxy.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.htmitech.addressbook.ClassEvent;
import com.htmitech.api.BookInit;
import com.htmitech.base.BaseFragmentActivity;
import com.htmitech.emportal.R;
import com.htmitech.emportal.common.lib.residemenu.ResideMenuItem;
import com.htmitech.proxy.ProxyDealLeftButtom;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.interfaces.CallBackLeftJC;
import com.htmitech.proxy.myenum.ApplicationAllEnum;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import htmitech.com.componentlibrary.SwitchButton;
import htmitech.com.componentlibrary.unit.DensityUtil;

/**
 * Created by Administrator on 2018/5/8.
 */

public class SettingActivity extends BaseFragmentActivity {
    private LinearLayout ll_setting;
    private ArrayList<AppInfo> settingAppInfo;
    private AppliationCenterDao mAppliationCenterDao;
    private String app_id;
    private TextView tvName;
    private ImageButton title_left_button;
    private LinkedHashMap<SETTINGTYPE,ArrayList<AppInfo>> listHashMap;
    public enum SETTINGTYPE {
        RIGHT,//存在右侧箭头
        NOT_RIGHT,//不存在右侧箭头且不存在当前点击
        BUTTON;//不存在右侧箭头，且存在当前点击
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mAppliationCenterDao = new AppliationCenterDao(this);
        initView();
        initData();
    }

    private void initData(){
        EventBus.getDefault().register(this);
        app_id = getIntent().getStringExtra("app_id");
        String titleName = getIntent().getStringExtra("appName");
        tvName.setText(titleName);

        new Thread(new Runnable() {
            @Override
            public void run() {
                settingAppInfo = mAppliationCenterDao.getSettingAppInfo(app_id);
                listHashMap = new LinkedHashMap<SETTINGTYPE, ArrayList<AppInfo>>();
                listHashMap.put(SETTINGTYPE.RIGHT,new ArrayList<AppInfo>());

                listHashMap.put(SETTINGTYPE.BUTTON,new ArrayList<AppInfo>());

                listHashMap.put(SETTINGTYPE.NOT_RIGHT,new ArrayList<AppInfo>());

                for(AppInfo mAppInfo : settingAppInfo){
                    ApplicationAllEnum mApplicationAllEnum = ApplicationAllEnum.getAppIdToEnum(mAppInfo);
                    if(mApplicationAllEnum == ApplicationAllEnum.BBXX){
                        listHashMap.get(SETTINGTYPE.NOT_RIGHT).add(mAppInfo);
                    }else if(mApplicationAllEnum == ApplicationAllEnum.JWIFI){
                        listHashMap.get(SETTINGTYPE.BUTTON).add(mAppInfo);
                    }else{
                        listHashMap.get(SETTINGTYPE.RIGHT).add(mAppInfo);
                    }
                }
                AppInfo appInfo = new AppInfo();
                appInfo.setApp_code("wifi");
                appInfo.setApp_name("仅WIFI上传");
                listHashMap.get(SETTINGTYPE.BUTTON).add(appInfo);
                appInfo = new AppInfo();
                appInfo.setApp_code("wifi_down");
                appInfo.setApp_name("仅WIFI下载");
                listHashMap.get(SETTINGTYPE.BUTTON).add(appInfo);

                appInfo = new AppInfo();
                appInfo.setApp_code("app_versioninfo");
                appInfo.setApp_name("版本信息");
                listHashMap.get(SETTINGTYPE.NOT_RIGHT).add(appInfo);


                SettingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for(SETTINGTYPE mSETTINGTYPE : listHashMap.keySet()){
                            ArrayList<AppInfo> appInfos = listHashMap.get(mSETTINGTYPE);

                            LinearLayout.LayoutParams layoutParams = null;

                            int index = 0;
                            boolean isFlag = false;
                            for (AppInfo mAppInfo : appInfos) {
                                ApplicationAllEnum mApplicationAllEnum = ApplicationAllEnum.getAppIdToEnum(mAppInfo);
                                if (mApplicationAllEnum == null) {
                                    mApplicationAllEnum = ApplicationAllEnum.CJ;
                                    mApplicationAllEnum.compCode = mAppInfo.getComp_code();
                                }

                                mApplicationAllEnum.appId = mAppInfo.getApp_id() + "";
                                mApplicationAllEnum.name = mAppInfo.getApp_name();
                                mApplicationAllEnum.url = mAppInfo.getPicture_normal();
                                mApplicationAllEnum.url_disabled = mAppInfo.getPicture_normal();//2017-10-10 18:33:51 更改成normal
                                mApplicationAllEnum.tab_item_id = mAppInfo.getTab_item_id();
                                mApplicationAllEnum.appType = mAppInfo.getApp_type();
                                mApplicationAllEnum.mAppInfo = mAppInfo;
                                if (mApplicationAllEnum == ApplicationAllEnum.QCHC) {
                                    mApplicationAllEnum.name = mAppInfo.getApp_name();
                                }


                                ProxyDealLeftButtom mProxyDealLeftButtom = new ProxyDealLeftButtom(SettingActivity.this, new CallBackLeftJC() {

                                    @Override
                                    public void onClickLeft(ApplicationAllEnum leftEnum) {
                                        BookInit.getInstance().getmCallbackMX().upgrade();
                                    }
                                }, mApplicationAllEnum);
                                ResideMenuItem mResideMenuItems = mProxyDealLeftButtom.leftShowIntent();
                                if(mSETTINGTYPE == SETTINGTYPE.NOT_RIGHT){
                                    mResideMenuItems.setImageState(View.GONE);
                                    mResideMenuItems.setmSwitchButton(View.GONE);
                                }else if(mSETTINGTYPE == SETTINGTYPE.BUTTON){
                                    mResideMenuItems.setImageState(View.GONE);
                                    mResideMenuItems.setmOnStateChangedListener(new MyOnStateChangedListener(mApplicationAllEnum));
                                    mResideMenuItems.setmSwitchButton(View.VISIBLE);
                                }else{
                                    mResideMenuItems.setImageState(View.VISIBLE);
                                }
                                mResideMenuItems.setBackgroundColor(SettingActivity.this.getResources().getColor(R.color.white));
                                mResideMenuItems.setTitleColor();
                                mResideMenuItems.setLineBackColor(R.color.ht_ccc);
                                if(appInfos.size() > 1 && !isFlag){

                                    layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    layoutParams.setMargins(0, DensityUtil.dip2px(SettingActivity.this,10),0,0);
                                    isFlag = true;

                                }else if(appInfos.size() == 1){

                                    layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    layoutParams.setMargins(0, DensityUtil.dip2px(SettingActivity.this,10),0,DensityUtil.dip2px(SettingActivity.this,10));

                                }else if(appInfos.size() > 1 && appInfos.size() - 1 == index){

                                    layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    layoutParams.setMargins(0, 0,0,DensityUtil.dip2px(SettingActivity.this,10));

                                }else{

                                    layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                                    layoutParams.setMargins(0, 0,0,0);

                                }
                                if (mResideMenuItems != null)
                                    ll_setting.addView(mResideMenuItems,layoutParams);

                                index++;
                            }



                        }

                    }
                });
            }
        }).start();
        title_left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ChangeLogin mChangeLogin = new ChangeLogin();
//                mChangeLogin.setClent(SettingActivity.this);
                SettingActivity.this.finish();
            }
        });


    }

    private void initView(){
        tvName = (TextView) findViewById(R.id.title_name);
        ll_setting = (LinearLayout) findViewById(R.id.ll_setting);
        title_left_button = (ImageButton) findViewById(R.id.title_left_button);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onStringEvent(ClassEvent event) {
        if (event.msg.equals("TextViewSize")) {
            recreate();
        }
    }



    /**
     * 滑动按钮的点击事件
     */


    public class MyOnStateChangedListener implements SwitchButton.OnStateChangedListener {

        private ApplicationAllEnum mApplicationAllEnum;

        public MyOnStateChangedListener(ApplicationAllEnum mApplicationAllEnum){
            this.mApplicationAllEnum = mApplicationAllEnum;
        }

        @Override
        public void toggleToOn() {

        }

        @Override
        public void toggleToOff() {

        }
    }

    @Override
    protected void onDestroy() {
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
