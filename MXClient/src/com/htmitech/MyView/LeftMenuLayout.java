package com.htmitech.MyView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.htmitech.api.BookInit;
import com.htmitech.app.widget.RoundImageView;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.common.lib.residemenu.ResideMenuItem;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.login.data.logindata.EmpApiLoginOutEntity;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.minxing.client.ClientTabActivity;
import com.minxing.client.util.FastJsonUtils;
import com.minxing.client.util.Utils;


import java.text.ParseException;

import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.db.DBCipherManager;
import htmitech.com.componentlibrary.dialog.LoadingDialog;
import htmitech.com.componentlibrary.listener.MXKitLogoutListener;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

/**
 * Created by htrf-pc on 2017/6/7.
 */
public class LeftMenuLayout extends LinearLayout implements ObserverCallBackType {
    private TextView exitlogin_btn;//退出登录
    private RelativeLayout slid_currentuser;//个人信息
    private RoundImageView imageview_residemenu_avatar; //头像
    private TextView default_tv;//默认头像
    private TextView textview_currentUserName;//当前用户名
    private LinearLayout layout_left_menu;//动态添加
    private Context mContext;
    private String LoginOutUrl = "";//退出登入接口url
    private LinearLayout sv_left_menu;
    private LoadingDialog mLoadingDialog;

    public LeftMenuLayout(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public LeftMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context);
    }

    public LeftMenuLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        initView(context);
    }


    public void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.activity_menu_left, this);

        exitlogin_btn = (TextView) findViewById(R.id.exitlogin_btn);
        slid_currentuser = (RelativeLayout) findViewById(R.id.slid_currentuser);
        imageview_residemenu_avatar = (RoundImageView) findViewById(R.id.imageview_residemenu_avatar);
        default_tv = (TextView) findViewById(R.id.default_tv);
        textview_currentUserName = (TextView) findViewById(R.id.textview_currentUserName);
        layout_left_menu = (LinearLayout) findViewById(R.id.layout_left_menu);
        sv_left_menu = (LinearLayout) findViewById(R.id.sv_left_menu);
        initData();
    }

    long startTime = 0;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void initData() {

        sv_left_menu.getBackground().setAlpha(220);
//        GradientDrawable normal = StateListDrawableUtil.getDrawable(10, getResources().getColor(R.color.ht_hred_title), 10, getResources().getColor(R.color.transparent));
//
//        GradientDrawable press = StateListDrawableUtil.getDrawable(10, getResources().getColor(R.color.lanse), 10, getResources().getColor(R.color.transparent));
//
//        StateListDrawable selector = StateListDrawableUtil.getSelector(normal, press);
//        exitlogin_btn.setBackground(selector);

        exitlogin_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Utils.showSystemDialog(mContext, getResources().getString(R.string.system_tip),
                        mContext.getResources().getString(R.string.warning_logout), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /**
                                 * 退出敏行成功后再退出EmpApi
                                 */
                                showDialog();
                                if ((startTime - System.currentTimeMillis()) / 1000 > 20 || startTime == 0) {
                                    startTime = System.currentTimeMillis();
                                    ConcreteLogin.getInstance().logout(mContext, new MXKitLogoutListener() {
                                        @Override
                                        public void onLogout() {
                                            //EmpApi退出
                                            EmpApiLoginOut();
//                                            DBCipherManager.getInstance(mContext).db.close();
                                        }
                                    });
//                                    MXKit.getInstance().logout(mContext, new MXKit.MXKitLogoutListener() {
//                                        @Override
//                                        public void onLogout() {
//
//
//                                        }
//
//
//                                    });
                                }
                            }
                        }, null, true);
            }
        });
        slid_currentuser.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ProxyDealApplicationPlugin mProxyDealApplication = new ProxyDealApplicationPlugin(mContext);
                AppInfo appInfo = new AppliationCenterDao(mContext).getAppInfoByAppCode("app_person_setting");
                if (ApplicationAllEnum.GRSZ.mAppInfo == null) {
                    ApplicationAllEnum.GRSZ.mAppInfo = appInfo;
                }

                int success = mProxyDealApplication.applicationCenterProxy(appInfo);
//                BookInit.getInstance().setPeopleMessageActivity(mContext);
            }
        });
    }

    /**
     * 添加一个MenuItem
     *
     * @param menuItem
     * @param direction
     */
    public void addMenuItem(ResideMenuItem menuItem, int direction) {
        layout_left_menu.addView(menuItem);
    }

    public void showDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing())
            mLoadingDialog.dismiss();
        mLoadingDialog = new LoadingDialog(getContext());
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setValue("正在退出...");
        mLoadingDialog.setCanceledOnTouchOutside(true);
        if (mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.setValue("正在退出...");
            mLoadingDialog.show();

        }
    }

    public void dismissDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {

            mLoadingDialog.dismiss();
        }
    }

    /**
     * 获取对应侧边栏整行信息
     *
     * @param appCode
     * @return
     */
    public ResideMenuItem getMenuItem(String appCode) {
        for (int i = 0; i < layout_left_menu.getChildCount(); i++) {
            ResideMenuItem mResideMenuItem = (ResideMenuItem) layout_left_menu.getChildAt(i);
            if (mResideMenuItem.getmAppInfo() != null && mResideMenuItem.getmAppInfo().getApp_code().equals(appCode)) {
                return mResideMenuItem;
            }
        }
        return null;
    }

    /**
     * 退出登入接口
     **/
    public void EmpApiLoginOut() {
        startTime = -1;     //当调退出接口时，重置时间
        LoginOutUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.USER_LOGINOUT_EMPAPI;
        AnsynHttpRequest.requestByPostWithToken(mContext, null, LoginOutUrl, CHTTP.POSTWITHTOKEN, this, "LoginOut", "0");
    }

    public void updateCurrentUserAvatar(String userName) {
        try {
            BookInit.getInstance().getPhotoBitMap(mContext, imageview_residemenu_avatar, default_tv);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        textview_currentUserName.setText(OAConText.getInstance(mContext).UserName);
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        //调用此方法防止token失效，返回的是token没有失效所生成的结果
        if (requestName.equals("LoginOut")) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(mContext, requestValue, type, LoginOutUrl, null, this, requestName, "");
            if (requestValue != null && !requestValue.equals("")) {
                EmpApiLoginOutEntity entity = FastJsonUtils.getPerson(requestValue, EmpApiLoginOutEntity.class);
                if (entity != null && entity.code == 200) {//EmpApi退出登入成功
                    HtmitechApplication.isAdvShow = true;
                    //清除Token缓存
                    PreferenceUtils.clearToken();
                    Intent finishIntent = new Intent(mContext, ClientTabActivity.class);
                    finishIntent.setAction("finish");
                    finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                /*finishIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                finishIntent.putExtra(AppConstants.SYSTEM_START_TYPE_APP2APP, false);
								finishIntent.putExtra(AppConstants.SYSTEM_APP2APP_TYPE, -1);
								finishIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
                    mContext.startActivity(finishIntent);
//                    PreferenceUtils.saveLastTime("1999-01-01 00:00:00");
                } else {
                    Toast.makeText(mContext, "退出EMPM失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "退出EMPM失败", Toast.LENGTH_SHORT).show();
            }
            dismissDialog();
            startTime = 0;
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (requestName.equals("LoginOut")) {
            if (exceptionMessage != null && (exceptionMessage.contains("超时") || exceptionMessage.contains("TIMEOUT"))) {
                Toast.makeText(mContext, "请求超时", Toast.LENGTH_SHORT).show();
//                PreferenceUtils.saveLastTime("1999-01-01 00:00:00");
            } else {
                Toast.makeText(mContext, "退出EMPM失败", Toast.LENGTH_SHORT).show();
            }
            dismissDialog();
            startTime = 0;
        }
    }

    @Override
    public void notNetwork() {
        dismissDialog();
        Toast.makeText(mContext, "请检查网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
