package com.htmitech.proxy.util;

import android.text.TextUtils;
import android.widget.TextView;

import com.htmitech.api.BookInit;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.proxy.doman.AppInfo;
import com.htmitech.proxy.myenum.ApplicationAllEnum;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.unit.TextUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ResourceUtil;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Created by Administrator on 2018-6-27.
 */

public class LogMesgUpUtil {

    public static String getLogMsg(AppInfo appInfo) {
        if (appInfo == null) {
            return "";
        }

        String msg =   OAConText.getInstance(HtmitechApplication.getApplication()).UserID + "|" +
                appInfo.getApp_id() + "|" +

                BookInit.getInstance().getPortalId() + "|" +
                OAConText.getInstance(HtmitechApplication.getApplication()).UserName + "|" +
                appInfo.getApp_shortname() +  "|" +
                BookInit.getInstance().getPortalName() + "|" +
                1 + "|" +
                ResourceUtil.getVerName(HtmitechApplication.getInstance()) + "|" +
                getTime("yyyy-MM-dd HH:mm:ss") +"|" +
                getTime("yyyy-MM-dd");

        return msg;

    }

    public static String getLogMsg(ApplicationAllEnum mApplicationAllEnum) {
        if (mApplicationAllEnum == null || mApplicationAllEnum.mAppInfo == null) {
            return "";
        }
        String msg;
        if(TextUtils.isEmpty(mApplicationAllEnum.appId)){

            msg =    OAConText.getInstance(HtmitechApplication.getApplication()).UserID + "|" +
                    mApplicationAllEnum.mAppInfo.getApp_id() + "|" +



                    BookInit.getInstance().getPortalId() + "|" +
                    OAConText.getInstance(HtmitechApplication.getApplication()).UserName + "|" +
                    mApplicationAllEnum.mAppInfo.getApp_shortname() +  "|" +
                    BookInit.getInstance().getPortalName() + "|" +
                    1 + "|" +
                    ResourceUtil.getVerName(HtmitechApplication.getInstance()) + "|" +
                    getTime("yyyy-MM-dd HH:mm:ss") +"|" +
                    getTime("yyyy-MM-dd");

        }else{
            msg =   OAConText.getInstance(HtmitechApplication.getApplication()).UserID + "|" +
                    mApplicationAllEnum.appId + "|" +


                    BookInit.getInstance().getPortalId() + "|" +
                    OAConText.getInstance(HtmitechApplication.getApplication()).UserName + "|" +
                    mApplicationAllEnum.name +  "|" +
                    BookInit.getInstance().getPortalName() + "|" +
                    1 + "|" +
                    ResourceUtil.getVerName(HtmitechApplication.getInstance()) + "|" +
                    getTime("yyyy-MM-dd HH:mm:ss") +"|" +
                    getTime("yyyy-MM-dd");
        }



        return msg;

    }

    public static String getTime(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str);
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static void uploadLoadMsg(){
        String getPictureUrl = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.FILE_UPLOAD_LOG_MSG;
        final File file = new File("/mnt/sdcard/htmitech/statis_temp");
        if(file == null || file.listFiles() == null){
            return;
        }
        for(File file1 : file.listFiles()){
            AnsynHttpRequest.requestByPostWithToken(HtmitechApplication.getInstance(), file1, getPictureUrl, CHTTP.POSTWITHTOKEN_UPLOAD, new ObserverCallBackType() {
                @Override
                public void success(String requestValue, int type, final String requestName) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String tmpPath = "/mnt/sdcard/htmitech/statis_temp" + File.separator + requestName;
                            File file2 = new File(tmpPath);
                            file2.delete();
                        }
                    }).start();

                }

                @Override
                public void fail(String exceptionMessage, int type, String requestName) {

                }

                @Override
                public void notNetwork() {

                }

                @Override
                public void callbackMainUI(String successMessage) {

                }
            }, file1.getName(), LogManagerEnum.MYINFO_UPDATE_PIC.getFunctionCode());
        }

    }
}
