package com.htmitech.proxy.plugin;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import com.htmitech.emportal.R;
import com.htmitech.emportal.ui.applicationcenter.ApplicationCenterActivity;
import com.htmitech.emportal.ui.detail.CheckForm;
import com.htmitech.emportal.ui.widget.AppCenterClassifyDialog;
import com.htmitech.proxy.ApplicationCenterImp.ApplicationObserver;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.pop.GeneralDialog;

import java.util.ArrayList;
import java.util.Map;

/**
 * 应用分类   tony
 */
public class ApplicationClassify implements ApplicationObserver {


    @Override
    public void excetStart(final Context context, AppInfo mAppInfo, Map<String, Object> parameters) {

        new AppCenterClassifyDialog(context,mAppInfo.getView(),mAppInfo.getClassifyAppInfo(),mAppInfo.getApp_id() + "").builder().setTitle(mAppInfo.getApp_shortname()).show();
//        ArrayList<CheckForm> checkForms = new ArrayList<CheckForm>();
//        checkForms.add(new CheckForm("1","小名","2"));
//        checkForms.add(new CheckForm("1","小名1","2"));
//        checkForms.add(new CheckForm("1","小名2","2"));
//        checkForms.add(new CheckForm("1","小名3","2"));
//        checkForms.add(new CheckForm("1","小名4","2"));
//        checkForms.add(new CheckForm("1","小名5","2"));
//        new GeneralDialog(context, checkForms, new GeneralDialog.IGeneralDialogItem() {
//            @Override
//            public void ItemClick(ArrayList<CheckForm> checkForms) {
//
//            }
//        }).builder().show();
    }


}
