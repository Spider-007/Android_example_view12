package com.minxing.client.util;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.htmitech.addressbook.ClassEvent;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.EmpPortal;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cn.feng.skin.manager.loader.SkinManager;

/**
 * 设置颜色样式  以及字体大小配置
 */
public class ConfigStyleUtil {


    public static void changeTextSize(Context activity, FinishPortalSwitch mFinishPortalSwitch ) {
        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(activity);
        EmpPortal empPortal = mAppliationCenterDao.getPortalId();
        if(empPortal == null)
            empPortal = mAppliationCenterDao.getPortalId();
        Configuration configuration = activity.getResources().getConfiguration();
        switch (empPortal.color_style) {//改变颜色 3 red  2  blue  1 white 0 自定义
            case 0:
                SkinManager.getInstance().restoreDefaultTheme();
                ClassEvent mClassEvent2 = new ClassEvent();
                mClassEvent2.msg = "portalSwitch";
                EventBus.getDefault().post(mClassEvent2);  //发送桌面重启
                mFinishPortalSwitch.finishPortalSwitchActivity();
                break;
            case 1:
                String skinNameWhite = "WhiteStyle.skin";
                String skinTargetDirWhite = Environment.getExternalStorageDirectory() + File.separator + skinNameWhite;
                Utils.changeSkin(activity,skinTargetDirWhite,skinNameWhite,mFinishPortalSwitch);
//                configuration.locale=Locale.GERMAN;
                break;
            case 2:
//                configuration.locale = Locale.getDefault();
                SkinManager.getInstance().restoreDefaultTheme();
                ClassEvent mClassEvent = new ClassEvent();
                mClassEvent.msg = "portalSwitch";
                EventBus.getDefault().post(mClassEvent);  //发送桌面重启
                mFinishPortalSwitch.finishPortalSwitchActivity();
                break;
            case 3:
                String skinNameRed = "RedStyle.skin";
                String skinTargetDirRed = Environment.getExternalStorageDirectory() + File.separator + skinNameRed;
                Utils.changeSkin(activity,skinTargetDirRed,skinNameRed,mFinishPortalSwitch);
//                configuration.locale = Locale.FRENCH;
                break;
        }
        float textSize = 1;
        textSize = textSize + empPortal.getFont_style() * 0.1f;
        configuration.fontScale = textSize;    //1为标准字体，multiple为放大的倍数
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager mWm = (WindowManager)activity.getSystemService(Context.WINDOW_SERVICE);
        mWm.getDefaultDisplay().getMetrics(displayMetrics);
        displayMetrics.scaledDensity = configuration.fontScale * displayMetrics.density;
        activity.getResources().updateConfiguration(configuration, displayMetrics);
    }
    public interface FinishPortalSwitch{
        void finishPortalSwitchActivity();
    }

}
