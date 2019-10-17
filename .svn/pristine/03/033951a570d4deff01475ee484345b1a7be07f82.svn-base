package com.htmitech.emportal.ui.plugin.h5;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;
import com.htmitech.proxy.util.ClassUtil;
import com.htmitech.proxy.util.ZTActivityUnit;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 公司热度榜
 */

public class HTMICordovaSuport extends CordovaPlugin {
    private static final String TAG = HTMICordovaSuport.class.getName();
    public HTMICordovaSuport() {
    }



    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if(action.contains("nativeFinish")){
            ZTActivityUnit.finsh();
        }else{
            String parmas = args.getString(0);
            String[] parmasStr = parmas.split(",");
            Log.d(TAG, parmas);

            callbackContext.success(success(parmasStr));
        }
        return true;
    }

    public JSONObject success(String[] parmasStr){
        JSONObject object = new JSONObject();
        for (String par:parmasStr) {
            if(!TextUtils.isEmpty(par)){
                par = par.replaceAll("\\|","");
                String value = Dictionary.getDictionary(par);

                try {
                    object.put(par,value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return object;
    }


    public enum Dictionary{
        USER_ID("user_id") {
            @Override
            public String getValue(String value) {

                return value(value);
            }
        },
        USER_NAME("user_name") {
            @Override
            public String getValue(String value) {
                return value(value);
            }
        },
        USER_CODE("user_code") {
            @Override
            public String getValue(String value) {
                return value(value);
            }
        },
        LOGIN_NAME("login_name") {
            @Override
            public String getValue(String value) {
                return value(value);
            }
        },
        GROUP_CORP_ID("group_corp_id") {
            @Override
            public String getValue(String value) {
                return value(value);
            }
        },
        ORG_ID("org_id") {
            @Override
            public String getValue(String value) {
                return value(value);
            }
        },
        ORG_NAME("org_name") {
            @Override
            public String getValue(String value) {
                return value(value);
            }
        },
        PARENT_ORG_ID("parent_org_id") {
            @Override
            public String getValue(String value) {
                return value(value);
            }
        },
        PORTAL_ID("portal_id") {
            @Override
            public String getValue(String value) {
                return value(value);
            }
        },
        APP_ID("app_id") {
            @Override
            public String getValue(String value) {
                return value(value);
            }
        },
        PARENT_APP_ID("parent_app_id") {
            @Override
            public String getValue(String value) {
                return value(value);
            }
        },
        APP_NAME("app_name") {
            @Override
            public String getValue(String value) {
                return value(value);
            }
        };
        String value;
        Dictionary(String value){
            this.value = value;
        }
        Dictionary(){

        }
        public abstract String getValue(String value);

        public String value(String values){
            String n = "";
            try {
                Field f = ClassUtil.appInfo.getClass().getDeclaredField(values);
                f.setAccessible(true);
                n = f.get(ClassUtil.appInfo) + "";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return n;
        }
        public static String getDictionary(String value){
            for(Dictionary mDictionary : Dictionary.values()){
                if (mDictionary.value.equals(value)) {
                    return mDictionary.getValue(value);
                }
            }
            return Dictionary.APP_NAME.getValue(Dictionary.APP_NAME.value);
        }

    }
}
