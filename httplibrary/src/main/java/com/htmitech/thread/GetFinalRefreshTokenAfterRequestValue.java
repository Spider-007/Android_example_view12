package com.htmitech.thread;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.htmitech.RefreshToken;
import com.htmitech.entity.RefreshTokenEntity;
import com.htmitech.utils.FastJsonUtils;

import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.PreferenceUtils;


/**
 * Created by heyang on 2016-12-12.
 */
public class GetFinalRefreshTokenAfterRequestValue {

    enum requestCode {
        ERROR,//表示错误 服务器异常 400 800
        SUCCESS,//表示正常返回 200
        TOKEN_TIME_OUT;//表示token超时

        public static requestCode findByCode(int code) {
            switch (code) {
                case 200:
                    return SUCCESS;
                case 400:
                case 800:
                    return ERROR;
                case 900:
                    return TOKEN_TIME_OUT;
            }
            return ERROR;
        }
    }


    /**
     * empm接口刷新token
     *
     * @param requestValue
     * @param type
     * @param url
     * @param javaBean
     * @param mObserverCallBackType
     * @param requestName
     * @param functionCode
     * @return
     */
    public static String getFinalRequestValue(Context context, String requestValue, int type, String url, Object javaBean, ObserverCallBackType mObserverCallBackType, String requestName, String functionCode) {
        //假设里面有一个返回类型 表示是token的 还是正常请求的  Type = 1  表示的token  2 表示正常请求
        if (type == 1) {
            if (requestValue != null && !requestValue.equals("")) {
                RefreshTokenEntity entity = FastJsonUtils.getPerson(requestValue, RefreshTokenEntity.class);
                if (entity == null) {
                    Log.e("REFRESHTOKEN", "===网络请求失败===");
                    return "";
                }
                switch (requestCode.findByCode(entity.code)) {
                    case SUCCESS:
                        if (entity.result.accessToken != null) {
                            PreferenceUtils.saveAccessToken(entity.result.accessToken);
                        }
                        if (entity.result.refreshToken != null) {
                            PreferenceUtils.saveRefreshToken(entity.result.refreshToken);
                        }
                        AnsynHttpRequest.requestByPostWithToken(context.getApplicationContext(), javaBean, url, CHTTP.POSTWITHTOKEN, mObserverCallBackType, requestName, functionCode);  //再次请求接口

                        break;
                    case ERROR:
                        Log.e("CODE", entity.code + "=====" + entity.message);

//                        ToastInfo toastInfo = ToastInfo.getInstance(context);
//                        toastInfo.setText("服务器异常：" + entity.message);
//                        toastInfo.show(Toast.LENGTH_SHORT);
                        Toast.makeText(context, "服务器异常：" + entity.message, Toast.LENGTH_SHORT).show();
                        return "";
                    case TOKEN_TIME_OUT:
                    default:
                        RefreshToken.RefreshAccessToken(context, mObserverCallBackType, requestName);  //失败继续刷新
                        Log.e("CODE", entity.code + "=====" + entity.message);
                        break;
                }
            } else {
                Log.e("REFRESHTOKEN", "===网络请求失败===");
                return "";
            }
        } else if (type == 2) {
            if (requestValue != null && !requestValue.equals("")) {
                Log.e("requestValueToken", "requestValue ----- >" + requestValue);
                IsNeedRefreshToken entity = FastJsonUtils.getPerson(requestValue, IsNeedRefreshToken.class);

                if (entity == null) {
                    Log.e("REFRESHTOKEN", "===网络请求失败===");
                    return "";
                }
                switch (requestCode.findByCode(entity.code)) {
                    case SUCCESS:
                        return requestValue;
                    case ERROR:
//                        ToastInfo toastInfo = ToastInfo
//                                .getInstance(HtmitechApplication.instance());
//                        toastInfo.setText("服务器异常：" + entity.message);
//                        toastInfo.show(Toast.LENGTH_SHORT);
//                        if(context != null && entity != null)
//                            Toast.makeText(context, "服务器异常：" + entity.getMessage(), Toast.LENGTH_SHORT).show();
                        return requestValue;
                    case TOKEN_TIME_OUT:
                    default:
                        RefreshToken.RefreshAccessToken(context, mObserverCallBackType, requestName);
                        break;
                }
            } else {
                Log.e("REFRESHTOKEN", "===网络请求失败===");
                return "";
            }
        }
        return "";
    }


    /**
     * cloudApi接口刷新token
     *
     * @param bean
     * @param baseModel
     * @param typeModel
     * @param parameters
     * @return
     */
//    public static String getFinalRequestValueCloudApi(Object bean, final BaseModel baseModel, final int typeModel, final Object parameters) {
//
//        String requestValue = JSON.toJSONString(bean);
//        if (requestValue != null && !requestValue.equals("")) {
//            Log.e("requestValueCloudApi", "requestValue ----- >" + requestValue);
//            requestValue = requestValue.replaceAll("\\\\", "");
//            if (requestValue.substring(0, 1).equals("\"")) {
//                requestValue = requestValue.substring(1);
//            }
//            if (requestValue.substring(requestValue.length() - 1, requestValue.length()).equals("\"")) {
//                requestValue = requestValue.substring(0, requestValue.length() - 1);
//            }
//            IsNeedRefreshTokenCloudApi entity = FastJsonUtils.getPerson(requestValue, IsNeedRefreshTokenCloudApi.class);
//
//            if (entity == null) {
//                Log.e("REFRESHTOKEN", "===网络请求失败===");
//                return "";
//            }
//            switch (requestCode.findByCode(entity.Status)) {
//                case TOKEN_TIME_OUT:
//                    RefreshToken.RefreshAccessToken(new ObserverCallBackType() {
//                        @Override
//                        public void success(String requestValue, int type, String requestName) {
//                            if (type == 1) {
//                                if (requestValue != null && !requestValue.equals("")) {
//                                    RefreshTokenEntity entity = FastJsonUtils.getPerson(requestValue, RefreshTokenEntity.class);
//                                    if (entity == null) {
//                                        Log.e("REFRESHTOKEN", "===网络请求失败===");
//                                    }
//                                    switch (requestCode.findByCode(entity.code)) {
//                                        case SUCCESS:
//                                            if (entity.result.accessToken != null) {
//                                                PreferenceUtils.saveAccessToken(entity.result.accessToken);
//                                            }
//                                            if (entity.result.refreshToken != null) {
//                                                PreferenceUtils.saveRefreshToken(entity.result.refreshToken);
//                                            }
//                                            baseModel.getDataFromServerByType(typeModel, parameters);
//                                            break;
//                                        case ERROR:
//                                            Log.e("CODE", entity.code + "=====" + entity.message);
//
////                                            ToastInfo toastInfo = ToastInfo
////                                                    .getInstance(HtmitechApplication.instance());
////                                            toastInfo.setText("服务器异常：" + entity.message);
////                                            toastInfo.show(Toast.LENGTH_SHORT);
//                                            break;
//                                        case TOKEN_TIME_OUT:
//                                        default:
//                                            RefreshToken.RefreshAccessToken(this, requestName);  //失败继续刷新
//                                            Log.e("CODE", entity.code + "=====" + entity.message);
//                                            break;
//                                    }
//                                } else {
//                                    Log.e("REFRESHTOKEN", "===网络请求失败===");
//                                }
//
//                            }
//                        }
//
//                        @Override
//                        public void fail(String exceptionMessage, int type, String requestName) {
//
//                        }
//
//                        @Override
//                        public void notNetwork() {
//
//                        }
//
//                        @Override
//                        public void callbackMainUI(String successMessage) {
//
//                        }
//                    }, "requsetToken");
//                    return "timeout";
//                default:
//                    return "";
//            }
//        } else {
//            Log.e("REFRESHTOKEN", "===网络请求失败===");
//            return "";
//        }
//    }

    /**
     * empm接口刷新token
     *
     * @param requestValue
     * @param type
     * @param url
     * @param javaBean
     * @param mObserverCallBackType
     * @param requestName
     * @param functionCode
     * @return
     */
    public static String getFinalRequestValueLOG_POST(Context context, String requestValue, int type, String url, Object javaBean, ObserverCallBackType mObserverCallBackType, String requestName, String functionCode) {
        //假设里面有一个返回类型 表示是token的 还是正常请求的  Type = 1  表示的token  2 表示正常请求
        if (type == 1) {
            if (requestValue != null && !requestValue.equals("")) {
                RefreshTokenEntity entity = FastJsonUtils.getPerson(requestValue, RefreshTokenEntity.class);
                if (entity == null) {
                    Log.e("REFRESHTOKEN", "===网络请求失败===");
                    return "";
                }
                switch (requestCode.findByCode(entity.code)) {
                    case SUCCESS:
                        if (entity.result.accessToken != null) {
                            PreferenceUtils.saveAccessToken(entity.result.accessToken);
                        }
                        if (entity.result.refreshToken != null) {
                            PreferenceUtils.saveRefreshToken(entity.result.refreshToken);
                        }
                        AnsynHttpRequest.requestByPost(context, javaBean, url, CHTTP.POST_LOG, mObserverCallBackType, requestName, functionCode);

                        break;
                    case ERROR:
                        Log.e("CODE", entity.code + "=====" + entity.message);

//                        ToastInfo toastInfo = ToastInfo
//                                .getInstance(HtmitechApplication.instance());
//                        toastInfo.setText("服务器异常：" + entity.message);
//                        toastInfo.show(Toast.LENGTH_SHORT);
                        return "";
                    case TOKEN_TIME_OUT:
                    default:
                        RefreshToken.RefreshAccessToken(context, mObserverCallBackType, requestName);  //失败继续刷新
                        Log.e("CODE", entity.code + "=====" + entity.message);
                        break;
                }
            } else {
                Log.e("REFRESHTOKEN", "===网络请求失败===");
                return "";
            }
        } else if (type == 2) {
            if (requestValue != null && !requestValue.equals("")) {
                Log.e("requestValueLogPost", "requestValue ----- >" + requestValue);
                IsNeedRefreshToken entity = FastJsonUtils.getPerson(requestValue, IsNeedRefreshToken.class);

                if (entity == null) {
                    Log.e("REFRESHTOKEN", "===网络请求失败===");
                    return "";
                }
                switch (requestCode.findByCode(entity.code)) {
                    case SUCCESS:
                        return requestValue;
                    case ERROR:
//                        ToastInfo toastInfo = ToastInfo
//                                .getInstance(HtmitechApplication.instance());
//                        toastInfo.setText("服务器异常：" + entity.message);
//                        toastInfo.show(Toast.LENGTH_SHORT);
                        return requestValue;
                    case TOKEN_TIME_OUT:
                    default:
                        RefreshToken.RefreshAccessToken(context, mObserverCallBackType, requestName);
                        break;
                }
            } else {
                Log.e("REFRESHTOKEN", "===网络请求失败===");
                return "";
            }
        }
        return "";
    }


}