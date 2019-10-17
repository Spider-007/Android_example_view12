package com.htmitech.emportal.ui.homepage;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.htmitech.addressbook.ClassEvent;
import com.htmitech.api.BookInit;
import com.htmitech.emportal.entity.OAConText;
import com.htmitech.emportal.ui.login.data.logindata.RefreshTokenEntity;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import com.htmitech.proxy.dao.AppliationCenterDao;
import com.htmitech.proxy.doman.EmpPortal;
import com.htmitech.proxy.util.NetWorkManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.minxing.client.RefreshToken;
import com.minxing.client.util.FastJsonUtils;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.SecuritySharedPreference;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Created by yanxin on 2017-8-23.
 */
public class PortalMessageService extends Service implements ObserverCallBackType {
    public NetWorkManager netWorkManager = new NetWorkManager();
    public static String daiban_yiban_url = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.GET_PORATL_Count_JAVA;
    public AsyncHttpClient client;
    public StringEntity stringEntity;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(this);
        ArrayList<EmpPortal> portalAll = mAppliationCenterDao.getPortalAll();
        client = new AsyncHttpClient();
        client.addHeader("Content-Type", "application/json");
        String refreshToken = PreferenceUtils.getRefreshToken();
        String accessToken = PreferenceUtils.getAccessToken();
        client.addHeader("accessToken", accessToken);
        client.addHeader("refreshToken", refreshToken);
        try {
            RequestCountBean requestCountBean = new RequestCountBean();
            requestCountBean.userId = OAConText.getInstance(this).UserID;
            requestCountBean.groupCorpId = BookInit.getInstance().getmApcUserdefinePortal().getGroup_corp_id()+"";
            ArrayList<String> portalStringList = new ArrayList<String>();
            for (int i = 0; i < portalAll.size(); i++) {
                portalStringList.add(portalAll.get(i).getPortal_id());
            }
            requestCountBean.portalIdList = portalStringList;
            String jsonObject = JSONObject.toJSONString(requestCountBean);
            stringEntity = new StringEntity(
                    jsonObject.toString(), "utf-8");
            RequestHandle post = client.post(this, daiban_yiban_url,
                    stringEntity, null, new MyCallBack());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    class MyCallBack extends JsonHttpResponseHandler {
        private int unreadMessage = -1;

        public MyCallBack() {

        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
            try {
//            if(null != mEmpPortal){
//                unreadMessage = MXAPI.getInstance(PortalMessageService.this).queryNetworkChatUnread(
//                        (int)mEmpPortal.getMx_appid());
//            }
                int status = response.getInt("code");
                if (status == 900) {//超时
                    RefreshToken.RefreshAccessToken(new ObserverCallBackType() {
                        @Override
                        public void success(String requestValue, int type, String requestName) {
                            if (type == 1) {
                                if (requestValue != null && !requestValue.equals("")) {
                                    RefreshTokenEntity entity = FastJsonUtils.getPerson(requestValue, RefreshTokenEntity.class);
                                    if (entity == null) {
                                        Log.e("REFRESHTOKEN", "===网络请求失败===");
                                    }
                                    switch (entity.code) {
                                        case 200:
                                            if (entity.result.accessToken != null) {
                                                PreferenceUtils.saveAccessToken(entity.result.accessToken);
                                            }
                                            if (entity.result.refreshToken != null) {
                                                PreferenceUtils.saveRefreshToken(entity.result.refreshToken);
                                            }
                                            RequestHandle post = client.post(PortalMessageService.this, daiban_yiban_url,
                                                    stringEntity, null, new MyCallBack());
                                            break;
                                        case 400:
                                        case 800:
                                            break;
                                        case 900:
                                        default:
                                            RefreshToken.RefreshAccessToken(this, requestName);  //失败继续刷新
                                            Log.e("CODE", entity.code + "=====" + entity.message);
                                            break;
                                    }
                                } else {
                                    Log.e("REFRESHTOKEN", "===网络请求失败===");
                                }
                            }
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
                    }, "requsetToken");
                    return;
                }
                int Result = -1;
                AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(PortalMessageService.this);
//                EmpPortal currentPortal = mAppliationCenterDao.getPortalId();
                ArrayList<EmpPortal> portalAll = mAppliationCenterDao.getPortalAll();
                Map result = JSONObject.parseObject(response.getJSONObject("result").toString(), Map.class);
                Iterator<Map.Entry<String, Integer>> iter = result.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, Integer> entry = iter.next();
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    if (null != value && value > 0) {
//                        SharedPreferences sp = PortalMessageService.this.getSharedPreferences(PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
                        SecuritySharedPreference sp = new SecuritySharedPreference(PortalMessageService.this, PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
                        sp.edit().putBoolean(key, true).commit();
                    } else {
//                        SharedPreferences sp = PortalMessageService.this.getSharedPreferences(PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
                        SecuritySharedPreference sp = new SecuritySharedPreference(PortalMessageService.this, PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
                        sp.edit().putBoolean(key, false).commit();
                    }

                }
//                for (int i = 0; i < portalAll.size();i++){
//                    unreadMessage = MXAPI.getInstance(PortalMessageService.this).queryNetworkChatUnread(
//                        (int)portalAll.get(i).getMx_appid());
//                    if(unreadMessage > 0){
//                        SharedPreferences sp = PortalMessageService.this.getSharedPreferences(PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
//                        sp.edit().putBoolean(portalAll.get(i).getPortal_id(),true).commit();
//                    }
//                }
                if (null != portalAll && portalAll.size() > 1) {
                    ClassEvent mClassEvent = new ClassEvent();
                    mClassEvent.msg = "redflag";
                    EventBus.getDefault().post(mClassEvent);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onSuccess(statusCode, headers, response);
        }

        @Override
        public void onFailure(int statusCode, Header[] headers,
                              Throwable throwable, org.json.JSONObject errorResponse) {
//            if(unreadMessage>0){
//                SharedPreferences sp = PortalMessageService.this.getSharedPreferences(PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
//                sp.edit().putBoolean(portalId,true).commit();
//                AppliationCenterDao mAppliationCenterDao = new AppliationCenterDao(PortalMessageService.this);
//                EmpPortal currentPortal = mAppliationCenterDao.getPortalId();
//                if(null != currentPortal&&currentPortal.getPortal_id().equals(portalId)){
            ClassEvent mClassEvent = new ClassEvent();
            mClassEvent.msg = "redflag";
            EventBus.getDefault().post(mClassEvent);
//                }
//            }
//            Toast.makeText(PortalMessageService.this, "门户未读信息获取失败！", Toast.LENGTH_SHORT).show();
            super.onFailure(statusCode, headers, throwable,
                    errorResponse);
            throwable.printStackTrace();

        }
    }

    @Override
    public void success(String requestValue, int type, String requestName) {
        if (requestName.contains("daibanyibancount")) {
            JSONObject mJSONObject = JSON.parseObject(requestValue);
            String Result = mJSONObject.getString("Result");
            if (TextUtils.isEmpty(Result)) {
                try {
                    int num = Integer.parseInt(Result);
                    if (num > 0) {
                        SharedPreferences sp = this.getSharedPreferences(PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
                        sp.edit().putBoolean(requestName.replace("daibanyibancount", ""), true).commit();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestName.contains("commonformcount")) {
            JSONObject mJSONObject = JSON.parseObject(requestValue);
            String Result = mJSONObject.getString("Result");
            if (TextUtils.isEmpty(Result)) {
                try {
                    int num = Integer.parseInt(Result);
                    if (num > 0) {
                        SharedPreferences sp = this.getSharedPreferences(PreferenceUtils.AllPortal_NUM, Context.MODE_PRIVATE);
                        sp.edit().putBoolean(requestName.replace("commonformcount", ""), true).commit();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void fail(String exceptionMessage, int type, String requestName) {
        Toast.makeText(PortalMessageService.this, exceptionMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void notNetwork() {

    }

    @Override
    public void callbackMainUI(String successMessage) {

    }
}
