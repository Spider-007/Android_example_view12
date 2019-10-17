package com.htmitech_updown.updownloadmanagement;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.utils.FastJsonUtils;


import htmitech.com.componentlibrary.entity.bigfileentity.SaveBigFileExtFields;
import htmitech.com.componentlibrary.entity.bigfileentity.SaveBigFileExtFieldsResultBean;
import htmitech.com.componentlibrary.listener.ObserverCallBack;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Created by Administrator on 2018/7/17.
 */
public class BigFileSaveExtFieldsTask implements ObserverCallBack {

    //    String saveExtFilesPath = "http://htrf.dscloud.me:8083/data-crab/extfielddata/saveExtFields";
    String saveExtFilesPath = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.SAVE_EXTFILES_PATH;
    private String SAVE_EXT_FIELDS = "save_ext_fileds";
    private Context context;
    private SaveBigFileExtFields saveBigFileExtFields;

    public BigFileSaveExtFieldsTask(Context context, SaveBigFileExtFields saveBigFileExtFields) {
        this.context = context;
        this.saveBigFileExtFields = saveBigFileExtFields;
        saveExtFields();
    }

    public void saveExtFields() {

//        AnsynHttpRequest.requestByPost(context, jsonObject, daiban_yiban_all_url, CHTTP.POST_LOG, this, HTTPDATBANYIBAN_ALL, "");
        AnsynHttpRequest.requestByPost(context, saveBigFileExtFields, saveExtFilesPath, CHTTP.POST, this);
    }


    @Override
    public void success(String requestValue) {
        if (requestValue != null && !requestValue.equals("")) {
            SaveBigFileExtFieldsResultBean saveBigFileExtFieldsResultBean = FastJsonUtils.getPerson(requestValue, SaveBigFileExtFieldsResultBean.class);
            if (saveBigFileExtFieldsResultBean != null) {
                if (saveBigFileExtFieldsResultBean.code == 200) {
                    Log.e("SaveExtFieldsTask", "成功");
                }
            }
        }
    }

    @Override
    public void fail(String exceptionMessage) {
        Log.e("SaveExtFieldsTask", "失败");
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
