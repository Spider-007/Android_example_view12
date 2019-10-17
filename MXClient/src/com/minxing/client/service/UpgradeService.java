package com.minxing.client.service;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSONObject;
import com.minxing.client.AppConstants;
import com.minxing.client.core.ServiceError;
import com.minxing.client.http.HttpCallBack;
import com.minxing.client.http.HttpClientAsync;
import com.minxing.client.http.HttpMethod;
import com.minxing.client.http.HttpRequestParams;
import com.minxing.client.http.Interface;
import com.minxing.client.upgrade.EmpmAppUpgradeInfo;

import java.util.TreeMap;

import htmitech.com.componentlibrary.content.ConcreteLogin;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ResourceUtil;

@SuppressWarnings("deprecation")
public class UpgradeService {

	public void checkUpgrade(Context mContext,String envType, final long currentVersionCode, final boolean isNeedBroad, final ViewCallBack viewCallBack) {
//		MyHttp.requestByHttpPostWithHeader()
		HttpRequestParams hrp = new HttpRequestParams();
		TreeMap<String, String> headerTreeMap = new TreeMap<String, String>();
		String refreshToken = PreferenceUtils.getRefreshToken();
		String accessToken = PreferenceUtils.getAccessToken();
		headerTreeMap.put("device","android");
		headerTreeMap.put("accessToken",accessToken);
		headerTreeMap.put("refreshToken",refreshToken);
		headerTreeMap.put("functionLogId","1");
		headerTreeMap.put("Content-Type","application/json");
		hrp.setHeaders(headerTreeMap);
		hrp.setRequestType(HttpMethod.POST);



		JSONObject mJson = new JSONObject();
		mJson.put("userId",PreferenceUtils.getEMPUserID(mContext));
		mJson.put("versionName", ResourceUtil.getVerName(mContext));
		mJson.put("versionNo",currentVersionCode+"");
		mJson.put("type","1");
		mJson.put("envType",envType);
		hrp.setJsonParams(mJson.toJSONString());
		hrp.setWbinterface(Interface.UPGRADE);

		HttpCallBack callback = new HttpCallBack() {
			@Override
			public void success(Object obj) {
				EmpmAppUpgradeInfo result =null;
				if (obj != null) {
					JSONObject json = (JSONObject) obj;
					result = JSONObject.parseObject(json.toJSONString(),EmpmAppUpgradeInfo.class);
					if(result != null && result.result !=null){
						int versonCode =result.result.versionNo;
						if (versonCode > currentVersionCode) {
							result.result.NeedUpgrade = true;
							PreferenceUtils.saveUpgradeMark(mContext);
						} else {
							PreferenceUtils.clearUpgradeMark(mContext);
						}
						Intent refresh = new Intent();
						refresh.setAction(AppConstants.MXCLIENT_REFRESH_NEW_VERSION);
						if(isNeedBroad){
							refresh.putExtra(AppConstants.MXCLIENT_UPGRADE_INFO, result);
						}
						mContext.sendBroadcast(refresh, ConcreteLogin.getInstance().getAppSignaturePermission());
					}
					this.mCallBack.success(result);
				}

			}

			@Override
			public void failure(ServiceError error) {
				this.mCallBack.failure(error);
			}
		};
		callback.setViewCallBack(viewCallBack);
		new HttpClientAsync(callback).execute(hrp);
	}
}
