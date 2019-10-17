package com.htmitech.proxy.pop;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.htmitech.MyView.GlideRoundTransform;
import com.htmitech.commonx.base.BitmapUtils;
import com.htmitech.commonx.base.bitmap.BitmapDisplayConfig;
import com.htmitech.commonx.base.bitmap.callback.BitmapLoadCallBack;
import com.htmitech.commonx.base.bitmap.callback.BitmapLoadFrom;
import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.applicationcenter.ApplicationDown;
import com.htmitech.pop.AlertDialog;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.LeftBar;
import com.htmitech.proxy.LeftColorFactory;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.managerApp.ClentAppUnit;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.doman.PortalRightTopMenu;
import com.htmitech.proxy.myenum.ApplicationAllEnum;

import java.util.ArrayList;
import java.util.Map;

/**
 * 右上角的pop
 *
 */
public class ToRightPopMenum extends PopupWindow {

    private Context mContext;
    private View contentView = null;
    private LinearLayout btn_container;
    private View myView;
    private Map<ApplicationAllEnum,LeftBar> rightMap;
    private ClentAppUnit mClentAppUnit;
    private ProxyDealApplicationPlugin mProxyDealApplication;
    @SuppressWarnings("deprecation")
    public ToRightPopMenum(Context context) {
        super(context);
        mContext = context;

        contentView = LayoutInflater.from(mContext).inflate(R.layout.app_system_top_right_menu, null);



        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());

        btn_container = (LinearLayout) contentView.findViewById(R.id.btn_container);


        rightMap = LeftColorFactory.getInstance(context).getLeftColorMap();

        mClentAppUnit = ClentAppUnit.getInstance(context);
        mProxyDealApplication = new ProxyDealApplicationPlugin(mContext);
    }

    /**
     * 传入一个父控件的appId  通过数据库查询子控件右上角
     *
     *
     * @param tab_item_id
     */
    public void setView(long tab_item_id,ImageView functionButton){
        ArrayList<PortalRightTopMenu> mPortalRightTopMenuList = new ArrayList<PortalRightTopMenu>();


        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(mContext);
        ArrayList<AppInfo> mAppInfoList = mAppliationCenterDao.getToRightInfo(tab_item_id + "");
        if(mAppInfoList == null || mAppInfoList.size() == 0){
            functionButton.setVisibility(View.GONE);
        }else{
            functionButton.setVisibility(View.VISIBLE);
        }
        for(AppInfo mAppInfo:mAppInfoList){
            PortalRightTopMenu mPortalRightTopMenus = new PortalRightTopMenu();
            mPortalRightTopMenus.setAppCode(mAppInfo.getApp_code());
            mPortalRightTopMenus.setComp_code(mAppInfo.getComp_code());
//            mPortalRightTopMenus.setDisplay_title(ApplicationAllEnum.getName(mPortalRightTopMenus.getAppCode()));
            mPortalRightTopMenus.setDisplay_title(mAppInfo.getApp_shortname());
            mPortalRightTopMenus.setPicture_disabled(mAppInfo.getPicture_disabled());
            mPortalRightTopMenus.setPicture_selected(mAppInfo.getPicture_selected());
            mPortalRightTopMenus.setPicture_normal(mAppInfo.getPicture_normal());
            mPortalRightTopMenus.setApp_loge(mAppInfo.getApp_logo());
            mPortalRightTopMenus.setApp_id(mAppInfo.getApp_id());
            mPortalRightTopMenus.setmAppInfo(mAppInfo);
            mPortalRightTopMenuList.add(mPortalRightTopMenus);

        }



        int index = 0;
        for(PortalRightTopMenu mPortalRightTopMenu:mPortalRightTopMenuList){
            myView = LayoutInflater.from(mContext).inflate(R.layout.app_system_top_right_menu_child,null);
            ImageView line_create_task = (ImageView) myView.findViewById(R.id.line_create_task);
            ImageView iv_app_center = (ImageView) myView.findViewById(R.id.iv_app_center);
            LinearLayout popbtn_create_task = (LinearLayout) myView.findViewById(R.id.popbtn_create_task);
            TextView tv_app_center = (TextView) myView.findViewById(R.id.tv_app_center);
            if(index == 0){
                line_create_task.setVisibility(View.GONE);
            }else{
                line_create_task.setVisibility(View.VISIBLE);
            }
            ApplicationAllEnum mApplicationAllEnum = ApplicationAllEnum.getAppIdToEnum(mPortalRightTopMenu.getmAppInfo());
            //特殊处理 如果在右上角出现应用中心的话  那么将替换枚举值
            if(mApplicationAllEnum == ApplicationAllEnum.YYZX){
                mApplicationAllEnum = ApplicationAllEnum.ZYYYZX;
            }
            mApplicationAllEnum.mAppInfo = mPortalRightTopMenu.getmAppInfo();
            LeftBar mLeftBar = rightMap.get(mApplicationAllEnum);

            if(mLeftBar != null){
                //基础应用

                if(mPortalRightTopMenu.getPicture_normal() == null || mPortalRightTopMenu.getPicture_normal().equals("")){
                    iv_app_center.setImageResource(mLeftBar.getColor());
                }else{
                    //插件
//                    BitmapUtils.instance().display(iv_app_center,
//                            mPortalRightTopMenu.getPicture_normal(),
//                            new BitmapLoadCallBack<ImageView>() {
//                                @SuppressLint("NewApi")
//                                @Override
//                                public void onLoadCompleted(
//                                        ImageView container, String uri,
//                                        Bitmap bitmap,
//                                        BitmapDisplayConfig config,
//                                        BitmapLoadFrom from) {
//                                    container.setImageBitmap(bitmap);
//                                }
//
//                                @Override
//                                public void onLoadFailed(ImageView container,
//                                                         String uri, Drawable drawable) {
//                                    // TODO Auto-generated method stub
//                                }
//                            });


                    Glide.with(mContext).load(mPortalRightTopMenu.getPicture_normal()).placeholder(R.drawable.pictures_no).error(R.drawable.pictures_no).into(iv_app_center);
                }
            }else{
                if(mPortalRightTopMenu.getmApplicationAllEnum() != null){
                    //插件

                    Glide.with(mContext).load(mPortalRightTopMenu.getmApplicationAllEnum().url).placeholder(R.drawable.pictures_no).error(R.drawable.pictures_no).into(iv_app_center);

//                    BitmapUtils.instance().display(iv_app_center,
//                            mPortalRightTopMenu.getmApplicationAllEnum().url,
//                            new BitmapLoadCallBack<ImageView>() {
//                                @SuppressLint("NewApi")
//                                @Override
//                                public void onLoadCompleted(
//                                        ImageView container, String uri,
//                                        Bitmap bitmap,
//                                        BitmapDisplayConfig config,
//                                        BitmapLoadFrom from) {
//                                    container.setImageBitmap(bitmap);
//                                }
//
//                                @Override
//                                public void onLoadFailed(ImageView container,
//                                                         String uri, Drawable drawable) {
//                                    // TODO Auto-generated method stub
//                                }
//                            });
                }

            }
            popbtn_create_task.setOnClickListener(new OnclickListener(mApplicationAllEnum));
            tv_app_center.setText(mPortalRightTopMenu.getDisplay_title());
            index++;
            btn_container.addView(myView);
        }
    }

    /**
     * 点击跳转事件
     */
    public class OnclickListener implements View.OnClickListener{
        private ApplicationAllEnum mApplicationAllEnum;
        public OnclickListener(ApplicationAllEnum mApplicationAllEnum){
            this.mApplicationAllEnum = mApplicationAllEnum;
        }
        @Override
        public void onClick(View v) {
//            try {
//                ToRightPopMenum.this.dismiss();
////                mClentAppUnit.setActivity(mApplicationAllEnum);
//
//
//            } catch (NotApplicationException e) {
//                e.printStackTrace();
//            }


            try {

                ToRightPopMenum.this.dismiss();
                int success = mProxyDealApplication.applicationCenterProxy(mApplicationAllEnum.mAppInfo);

                switch (success) {
                    case 1: //强制升级以及下载
                    case 2://可暂时不升级
                        mClentAppUnit.setActivity(ApplicationAllEnum.ZYYYZX);
                        break;
                }

            } catch (NotApplicationException e) {
                e.printStackTrace();
            }
        }
    }

}