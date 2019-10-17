package com.htmitech.proxy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.htmitech.api.BookInit;
import com.htmitech.commonx.base.BitmapUtils;
import com.htmitech.commonx.base.bitmap.BitmapDisplayConfig;
import com.htmitech.commonx.base.bitmap.callback.BitmapLoadCallBack;
import com.htmitech.commonx.base.bitmap.callback.BitmapLoadFrom;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.R;
import com.htmitech.emportal.common.lib.residemenu.ResideMenuItem;
import com.htmitech.myEnum.StylesEnum;
import com.htmitech.proxy.ApplicationCenter.ProxyDealApplicationPlugin;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.AppclientVersion;
import com.htmitech.proxy.exception.NotApplicationException;
import com.htmitech.proxy.interfaces.CallBackLeftJC;
import com.htmitech.proxy.managerApp.ClentAppUnit;
import com.htmitech.proxy.myenum.ApplicationAllEnum;

import java.util.Map;

;
;

/**
 * 获取左侧侧边栏的所有数据
 */
public class ResideMenunItemFactory {

    private   Context context;

    private  ApplicationAllEnum mLeftEnums;

    private  StylesEnum mColorEnum;

    private  Map<ApplicationAllEnum,LeftBar> leftMap ;

    private  String cjName ; //插件的名称

    private  String cjUrl; //插件的路径

    private  CallBackLeftJC mCallBackLeftJC;

    private static ResideMenunItemFactory mResideMenunItemFactory ;

    private ClentAppUnit mClentAppUnit;

    private ResideMenunItemFactory(){

    }

    public static ResideMenunItemFactory getInstance(){
        if(mResideMenunItemFactory == null){
            mResideMenunItemFactory = new ResideMenunItemFactory();
        }
        return mResideMenunItemFactory;
    }

    public  ResideMenuItem getResideMenuItem(ApplicationAllEnum mLeftEnum,Context contexts,CallBackLeftJC mCallBackLeftJCs){
        mLeftEnums = mLeftEnum;
        context = contexts;
        mClentAppUnit = ClentAppUnit.getInstance(context);
        mCallBackLeftJC = mCallBackLeftJCs;
        return requestResideMenumItem(mLeftEnum);
    }


    public  ResideMenuItem getResideMenuItem(ApplicationAllEnum mLeftEnum,Context contexts,String cjNames,String cjUrls){
        mLeftEnums = mLeftEnum;
        context = contexts;
        cjName = cjNames;
        cjUrl = cjUrls;
        mClentAppUnit = ClentAppUnit.getInstance(context);
        return requestResideMenumItem(mLeftEnum);
    }


    /**
     * 获取对应的ResideMenumItem
     * @param mLeftEnum
     * @return
     */
    private  ResideMenuItem requestResideMenumItem(ApplicationAllEnum mLeftEnum){
        leftMap = LeftColorFactory.getInstance(context).getLeftColorMap();
        ResideMenuItem mResideMenuItem = null;
        if(mLeftEnum == null){
            return mResideMenuItem;
        }
        switch (mLeftEnum){
            case TYBDCJ:
            case CJ:
                mResideMenuItem = CJResideMenumItem(mLeftEnum);
                break;
            default:
                mResideMenuItem = getResideMenuItem(mLeftEnum);
                break;
        }
        return mResideMenuItem;
    }


    public ResideMenuItem getResideMenuItem(ApplicationAllEnum mLeftEnum){
        String name;
        boolean isNewFalg = false;
        int color;
        switch (mLeftEnum){
            case BBXX:
                PackageInfo info = null;
                try {
                    info = context.getPackageManager().getPackageInfo(
                            context.getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                int versionCode = info.versionCode;
                AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(HtmitechApplication.instance().getApplicationContext());
                AppclientVersion appClientVersion = mAppliationCenterDao.getAppClientVersion(versionCode);
                if (appClientVersion != null && appClientVersion.getVersion_no() > versionCode){
                   isNewFalg = true;
                }
                    name = mLeftEnum.mAppInfo.getApp_shortname() + "("
                            + DeviceUtils.getAppVersionName(context)
                            + ")";


                break;
            default:
                name = mLeftEnum.mAppInfo.getApp_shortname();
                break;
        }
        ResideMenuItem mResideMenuItem_docBook;
        //如果url路径为空的话 那么将使用本地的图片
        if(mLeftEnum.url == null || mLeftEnum.url.equals("")){
            if(leftMap.get(mLeftEnum) == null){
                mResideMenuItem_docBook = new ResideMenuItem(
                        context, R.drawable.pictures_no, name);
            }else{
                mResideMenuItem_docBook = new ResideMenuItem(
                        context, leftMap.get(mLeftEnum).getColor(), name);
            }
        }else{
            //如果url不为空的话 那么将是用网络图片
            mResideMenuItem_docBook = new ResideMenuItem(
                    context,  name);
            mResideMenuItem_docBook.setIcon(R.drawable.pictures_no);

            Glide.with(context).load(mLeftEnum.url).placeholder(com.htmitech.addressbook.R.drawable.pictures_no).error(com.htmitech.addressbook.R.drawable.pictures_no).into(mResideMenuItem_docBook.getIcon());
//            BitmapUtils.instance().display(mResideMenuItem_docBook.getIcon(),
//                    mLeftEnum.url,
//                    new BitmapLoadCallBack<ImageView>() {
//                        @SuppressLint("NewApi")
//                        @Override
//                        public void onLoadCompleted(
//                                ImageView container, String uri,
//                                Bitmap bitmap,
//                                BitmapDisplayConfig config,
//                                BitmapLoadFrom from) {
//                            container.setImageBitmap(bitmap);
//                        }
//
//                        @Override
//                        public void onLoadFailed(ImageView container,
//                                                 String uri, Drawable drawable) {
//                            // TODO Auto-generated method stub
//                            container.setImageResource(R.drawable.pictures_no);
//                        }
//                    });
        }
        if(isNewFalg){
            mResideMenuItem_docBook.setNewFlag();
        }





        ((LinearLayout) mResideMenuItem_docBook)
                .setOnClickListener(new ResideOnClick(mLeftEnum,0));
        return mResideMenuItem_docBook;
    }

    public class ResideOnClick implements View.OnClickListener{
        ApplicationAllEnum mLeftEnum;
        public int postion;
        public ResideOnClick(ApplicationAllEnum mLeftEnum,int postion){
            this.mLeftEnum = mLeftEnum;
            this.postion = postion;
        }


        @Override
        public void onClick(View v) {

            if(postion == 0){
                switch (mLeftEnum) {
                    case BBXX:
                        mCallBackLeftJC.onClickLeft(mLeftEnum);
                        break;
                    default:
                        try {
                            mClentAppUnit.setActivity(mLeftEnum);
                        } catch (NotApplicationException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }else{
                ProxyDealApplicationPlugin mProxyDealApplication = new ProxyDealApplicationPlugin(context);
                try {
                    mProxyDealApplication.applicationCenterProxy(mLeftEnum.mAppInfo);
                } catch (NotApplicationException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 插件
     */
        private  ResideMenuItem CJResideMenumItem(final ApplicationAllEnum mLeftEnum){


        final ResideMenuItem cjResideMenumItem = new ResideMenuItem(
                context,  mLeftEnum.mAppInfo.getApp_shortname());
        cjResideMenumItem.setIcon(R.drawable.pictures_no);
        cjResideMenumItem.setmAppInfo(mLeftEnum.mAppInfo);
        BitmapUtils.instance().display(cjResideMenumItem.getIcon(),
                mLeftEnum.mAppInfo.getPicture_normal(),
                new BitmapLoadCallBack<ImageView>() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onLoadCompleted(
                            ImageView container, String uri,
                            Bitmap bitmap,
                            BitmapDisplayConfig config,
                            BitmapLoadFrom from) {
                        container.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onLoadFailed(ImageView container,
                                             String uri, Drawable drawable) {
                        // TODO Auto-generated method stub

                        container.setImageResource(R.drawable.pictures_no);
                    }
                });

        ((LinearLayout) cjResideMenumItem)
                .setOnClickListener(new View.OnClickListener() {
                    @SuppressWarnings("deprecation")
                    @Override
                    public void onClick(View v) {

                        ProxyDealApplicationPlugin mProxyDealApplication = new ProxyDealApplicationPlugin(context);
                        try {
                            mProxyDealApplication.applicationCenterProxy(cjResideMenumItem.getmAppInfo());
                        } catch (NotApplicationException e) {
                            e.printStackTrace();
                        }
                    }
                });
        return cjResideMenumItem;
    }

}
