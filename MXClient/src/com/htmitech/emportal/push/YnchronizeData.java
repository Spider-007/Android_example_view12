package com.htmitech.emportal.push;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import com.htmitech.addressbook.ClassEvent;
import com.htmitech.api.BookInit;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.service.BookService;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.listener.CallBackSuccess;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by heyang on 2017-2-24.
 */
public class YnchronizeData implements CallBackSuccess {

    Context context = null;

    public YnchronizeData(Context context) {
        this.context = context;
    }

    public void startServiceToYnchronizeData() {
        ComponentInit.getInstance().setSuccess(this);
        Intent i = new Intent(context, BookService.class);
        i.putExtra("LoginName", OAConText.getInstance(HtmitechApplication.instance()).UserID);
        context.startService(i);
    }

    @Override
    public void sysUserSuccess(boolean flag) {
        ClassEvent mClassEvent = new ClassEvent();
        mClassEvent.msg = "ynchronizeData";
        EventBus.getDefault().post(mClassEvent);
        if (!getRunningActivityName().equals("ClientTabActivity"))
            TestActivityManager.getInstance().getCurrentActivity().recreate();
    }

    @Override
    public void setProgressbar(String value) {

    }

    private static String getRunningActivityName() {
        ActivityManager activityManager = (ActivityManager) TestActivityManager.getInstance().getCurrentActivity().getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity = activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }

}
