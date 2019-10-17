package com.htmitech.emportal.ui.plugin.h5;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.htmitech.api.BookInit;
import com.htmitech.datepicker.PopChooseTimeHelper;
import com.htmitech.domain.SYS_Department;
import com.htmitech.domain.SYS_User;
import com.htmitech.emportal.entity.AuthorInfo;
import com.htmitech.emportal.ui.formConfig.CallBackDoAction;
import com.htmitech.listener.CallCheckAllUserListener;
import com.htmitech.myEnum.ChooseSystemBook;
import com.htmitech.myEnum.ChooseTreeHierarchy;
import com.htmitech.myEnum.ChooseWay;
import com.htmitech.myEnum.ChooseWayEnum;
import com.htmitech.myEnum.TimeEnum;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 公司热度榜
 */

public class HtmitechHeatPlugin extends CordovaPlugin {

    public HtmitechHeatPlugin() {
    }



    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        callbackContext.success();
        return true;
    }
}
