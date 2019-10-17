package com.htmitech.emportal.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.htmitech.Task.DownLoaderTask;
import com.htmitech.addressbook.ClassEvent;
import com.htmitech.api.BookInit;
import com.htmitech.app.Constant;
import com.htmitech.commonx.util.UIUtil;
import com.htmitech.emportal.HtmitechApplication;
import com.htmitech.emportal.base.IBaseCallback;
import com.htmitech.emportal.entity.DataCrabSyncDataParameter;
import com.htmitech.emportal.entity.DataCrabSyncDataResultInfo;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.entity.SyncDataParameter;
import com.htmitech.emportal.entity.SyncDataResultInfo;
import com.htmitech.emportal.ui.login.model.task.LoginModel;

import htmitech.com.componentlibrary.api.ComponentInit;
import htmitech.com.componentlibrary.listener.ObserverCallBack;
import com.htmitech.listener.WatcherBook;
import com.htmitech.proxy.myenum.LogManagerEnum;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;

import org.greenrobot.eventbus.EventBus;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;


/**
 * Created by htrf-pc on 2016/3/24.
 */
public class BookService extends Service implements IBaseCallback, ObserverCallBackType {

    public static final int STOP = 0;
    //public static String DOWNPATH = "http://114.112.89.94:8081/swj/api/GetMobileData/SyncUsers?LastSyncTime=" + PreferenceUtils.getLastTime(); ;
    public static String DOWNPATH = null;
    public boolean isSys = false;
    private Bundle bundle;
    private String loginName;
    private Handler mHandler;
    public boolean isback = false;

    private static final String HTTPSYNCDATA = "syncdata";
    private static final String getDataSync = ServerUrlConstant.SERVER_EMPAPI_URL() + ServerUrlConstant.DATA_CRAB_GETSYNCDATA;
    private String stringToJson;
    private Gson mGson = new Gson();
    private String from = "";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (OAConText.getInstance(HtmitechApplication.instance()) != null) {
            if (OAConText.getInstance(HtmitechApplication.instance()).UserID != null
                    && OAConText.getInstance(HtmitechApplication.instance()).UserID.length() > 0) {
                if (intent != null) {
                    bundle = intent.getExtras();
                }

                if (bundle != null) {
                    isSys = bundle.getBoolean("isSys", false);
                    isback = bundle.getBoolean("isback", false);
                    from = bundle.getString("from") != null ? bundle.getString("from") : "";//是从哪个地方开启的这个服务
                }
                loginName = OAConText.getInstance(HtmitechApplication.instance()).UserID;
                syncBook(loginName);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void syncBook(String loginName) {
//        try{
//            File src =  new File("/storage/sdcard0/htmitech/emportal");
//            if(!src.exists()){
//                src.mkdirs();
//            }
//            if(src.exists())
//                Log.e("tag",src.exists()+"hhh");
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.e("错误",e+"------------");
//        }
        if (!Constant.NEW_SYS_INTERFACE) {
            SyncDataParameter p = new SyncDataParameter();
            if (ComponentInit.getInstance().getSuccess() != null)
                ComponentInit.getInstance().getSuccess().setProgressbar("正在下载数据文件...");
            p.UserID = OAConText.getInstance(HtmitechApplication.instance()).UserID;
            p.LastSyncTime = PreferenceUtils.getLastTime();
            LoginModel loginModel = new LoginModel(this);
            loginModel.getDataFromServerByType(LoginModel.TYPE_GetSyncData, p);
        } else {
            DataCrabSyncDataParameter dataCrabSyncDataParameter = new DataCrabSyncDataParameter();
            if (ComponentInit.getInstance().getSuccess() != null)
                ComponentInit.getInstance().getSuccess().setProgressbar("正在下载数据文件...");
            dataCrabSyncDataParameter.userId = OAConText.getInstance(HtmitechApplication.instance()).UserID;
            dataCrabSyncDataParameter.lastSyncTime = PreferenceUtils.getLastTime();
            ;
            dataCrabSyncDataParameter.groupCorpId = OAConText.getInstance(HtmitechApplication.instance()).group_corp_id;
            if (BookInit.getInstance().getCorp_id() != null && BookInit.getInstance().getCorp_id().trim().equals(""))
                dataCrabSyncDataParameter.corpId = BookInit.getInstance().getCorp_id();
            stringToJson = mGson.toJson(dataCrabSyncDataParameter);
            AnsynHttpRequest.requestByPostWithToken(BookService.this, stringToJson, getDataSync, CHTTP.POSTWITHTOKEN, BookService.this, HTTPSYNCDATA, LogManagerEnum.GGENERAL.functionCode);

        }


    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onSuccess(int requestTypeId, Object result) {
        if (result != null && result instanceof SyncDataResultInfo) {
            SyncDataResultInfo mSyncDataResult = (SyncDataResultInfo) result;
            String zipUrl = mSyncDataResult.getResult();
            final DownLoaderTask mDownLoaderTask = new DownLoaderTask(zipUrl, Constant.SDCARD_PATH, BookService.this, new WatcherBook() {
                private Handler downLoadFail;

                @Override
                public void update(boolean str, String fileName) {
                    AnsynHttpRequest.fileParsing(BookService.this, fileName, new ObserverCallBack() {
                        @Override
                        public void success(String requestValue) {

                        }

                        @Override
                        public void fail(String exceptionMessage) {

                            if (isSys) {
                                return;
                            }
                            UIUtil.showToast(BookService.this, exceptionMessage);
                            Intent mIntent = new Intent(Constant.ACTION_NAME);
                            mIntent.putExtra("value", 1);
                            if (from != null && from.equals("login")) {
                                mIntent.putExtra("isSuccess", false);
                                mIntent.putExtra("from", from);
                            }
                            //发送广播
                            BookService.this.sendBroadcast(mIntent);
                        }

                        @Override
                        public void notNetwork() {

                        }

                        @Override
                        public void callbackMainUI(String successMessage) {
                            if (isback) {
                                ClassEvent mClassEvent = new ClassEvent();
                                mClassEvent.msg = "ynchronizeData";
                                EventBus.getDefault().post(mClassEvent);
                                Intent mIntents = new Intent(Constant.ACTION_NAME);
                                mIntents.putExtra("value", 1);
                                //发送广播
                                BookService.this.sendBroadcast(mIntents);
                                return;
                            }
                            if (isSys) {

                                ClassEvent mClassEvent = new ClassEvent();
                                mClassEvent.msg = "ynchronizeData";
                                EventBus.getDefault().post(mClassEvent);
                                return;
                            }
//                            发送通知告诉通讯录页面刷新完成可以进行加载
                            Log.d("BookService", successMessage + "------------");
                            Intent mIntent = new Intent(Constant.ACTION_NAME);
                            mIntent.putExtra("value", 1);
                            if (from != null && from.equals("login")) {
                                mIntent.putExtra("isSuccess", true);
                                mIntent.putExtra("from", from);
                            }
                            //发送广播
                            BookService.this.sendBroadcast(mIntent);
                        }
                    }); // 将文件写入数据库中
                }

                @Override
                public void DownLoadFail() {
                    if (downLoadFail == null)
                        downLoadFail = new Handler(Looper.getMainLooper());
                    downLoadFail.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            syncBook(loginName);
                        }
                    }, 60000);

                    //-----------add by heyang 2017-11-17 14:50:32 start--------从登录界面进来时，如果下载文件失败发送广播，通知下载失败-------
                    if (from != null && from.equals("login")) {
                        Intent mIntent = new Intent(Constant.ACTION_NAME);
                        mIntent.putExtra("value", 1);
                        mIntent.putExtra("isSuccess", false);
                        mIntent.putExtra("from", from);
                        //发送广播
                        BookService.this.sendBroadcast(mIntent);
                    }
                    //-----------add by heyang 2017-11-17 14:50:32 end---------------
                }
            });
            mDownLoaderTask.execute();// 下载完成 开始解压

        }
    }

    private void dowmZip(DataCrabSyncDataResultInfo mSyncDataResult) {
        String zipUrl = mSyncDataResult.getResult();
        final DownLoaderTask mDownLoaderTask = new DownLoaderTask(zipUrl, Constant.SDCARD_PATH, BookService.this, new WatcherBook() {
            private Handler downLoadFail;

            @Override
            public void update(boolean str, String fileName) {
                AnsynHttpRequest.fileParsing(BookService.this, fileName, new ObserverCallBack() {
                    @Override
                    public void success(String requestValue) {

                    }

                    @Override
                    public void fail(String exceptionMessage) {

                        if (isSys) {
                            return;
                        }
                        UIUtil.showToast(BookService.this, exceptionMessage);
                        Intent mIntent = new Intent(Constant.ACTION_NAME);
                        mIntent.putExtra("value", 1);
                        if (from != null && from.equals("login")) {
                            mIntent.putExtra("isSuccess", false);
                            mIntent.putExtra("from", from);
                        }
                        //发送广播
                        BookService.this.sendBroadcast(mIntent);
                    }

                    @Override
                    public void notNetwork() {

                    }

                    @Override
                    public void callbackMainUI(String successMessage) {
                        if (isback) {
                            ClassEvent mClassEvent = new ClassEvent();
                            mClassEvent.msg = "ynchronizeData";
                            EventBus.getDefault().post(mClassEvent);
                            Intent mIntents = new Intent(Constant.ACTION_NAME);
                            mIntents.putExtra("value", 1);
                            //发送广播
                            BookService.this.sendBroadcast(mIntents);
                            return;
                        }
                        if (isSys) {

                            ClassEvent mClassEvent = new ClassEvent();
                            mClassEvent.msg = "ynchronizeData";
                            EventBus.getDefault().post(mClassEvent);
                            return;
                        }
//                            发送通知告诉通讯录页面刷新完成可以进行加载
                        Log.d("BookService", successMessage + "------------");
                        Intent mIntent = new Intent(Constant.ACTION_NAME);
                        mIntent.putExtra("value", 1);
                        if (from != null && from.equals("login")) {
                            mIntent.putExtra("isSuccess", true);
                            mIntent.putExtra("from", from);
                        }
                        //发送广播
                        BookService.this.sendBroadcast(mIntent);
                    }
                }); // 将文件写入数据库中
            }

            @Override
            public void DownLoadFail() {
                if (downLoadFail == null)
                    downLoadFail = new Handler(Looper.getMainLooper());
                downLoadFail.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                            syncBook(loginName);
                    }
                }, 60000);

                //-----------add by heyang 2017-11-17 14:50:32 start--------从登录界面进来时，如果下载文件失败发送广播，通知下载失败-------
                if (from != null && from.equals("login")) {
                    Intent mIntent = new Intent(Constant.ACTION_NAME);
                    mIntent.putExtra("value", 1);
                    mIntent.putExtra("isSuccess", false);
                    mIntent.putExtra("from", from);
                    //发送广播
                    BookService.this.sendBroadcast(mIntent);
                }
                //-----------add by heyang 2017-11-17 14:50:32 end---------------
            }
        });
        mDownLoaderTask.execute();// 下载完成 开始解压
    }

    @Override
    public void onFail(int requestTypeId, int statusCode, String errorMsg, Object result) {
        if (isSys) {
            return;
        }
        if (requestTypeId == LoginModel.TYPE_GetSyncData) {
            if (mHandler == null)
                mHandler = new Handler();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    syncBook(loginName);
                }
            }, 60000);
        }
        Intent mIntent = new Intent(Constant.ACTION_NAME);
        mIntent.putExtra("value", 11);
        if (from != null && from.equals("login")) {
            mIntent.putExtra("isSuccess", false);
            mIntent.putExtra("from", from);
        }
        //发送广播
        BookService.this.sendBroadcast(mIntent);
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (HTTPSYNCDATA.equals(requestName)) {
            requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(this, requestValue, type, getDataSync, stringToJson, this, requestName, LogManagerEnum.GGENERAL.functionCode);
            if (requestValue != null && !requestValue.equals("")) {
                DataCrabSyncDataResultInfo mSyncDataResult = mGson.fromJson(requestValue.toString(), DataCrabSyncDataResultInfo.class);
                dowmZip(mSyncDataResult);
            }
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        if (HTTPSYNCDATA.equals(requestName)) {
            if (isSys) {
                return;
            }
            Intent mIntent = new Intent(Constant.ACTION_NAME);
            mIntent.putExtra("value", 11);
            if (from != null && from.equals("login")) {
                mIntent.putExtra("isSuccess", false);
                mIntent.putExtra("from", from);
            }
            //发送广播
            BookService.this.sendBroadcast(mIntent);
        }
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
