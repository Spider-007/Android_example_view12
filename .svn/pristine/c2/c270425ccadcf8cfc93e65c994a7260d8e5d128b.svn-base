package com.htmitech.emportal.net;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.htmitech.commonx.base.http.RequestParams;
import com.htmitech.commonx.util.DeviceUtils;
import com.htmitech.emportal.HtmitechApplication;

public class HttpRequestEntity {
	private RequestParams mParams;
	private String url;
	private boolean withCommonParams = true;

	public HttpRequestEntity(String url, String appendString) {
		this.url = url;
		mParams = new RequestParams();
		if (!TextUtils.isEmpty(appendString)) {
			this.url += appendString;
		}
	}

	public HttpRequestEntity(String url, boolean withCommonParams,
			String appendString) {
		this.url = url;
		mParams = new RequestParams();
		this.withCommonParams = withCommonParams;
		if (!TextUtils.isEmpty(appendString)) {
			this.url += appendString;
		}
	}

	public HttpRequestEntity addQueryStringParameter(String key, String value) {
		if (!TextUtils.isEmpty(key) && value != null) {
			mParams.addQueryStringParameter(key, value);
		}
		return this;
	}

	public HttpRequestEntity setJSONParameter(HashMap<String, String> urlParams) {
		if (urlParams != null && urlParams.size() > 0) {
			if (withCommonParams) {
				urlParams.putAll(buildCommonParams());
			}
			Iterator<Entry<String, String>> iterator = urlParams.entrySet()
					.iterator();
			JSONObject jsonParameter = new JSONObject();
			while (iterator.hasNext()) {
				Entry<String, String> next = iterator.next();
				String key = next.getKey();
				String value = next.getValue();
				if (!TextUtils.isEmpty(key) && value != null) {
					try {
						jsonParameter.put(key, value);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			mParams.setJSONParameter(jsonParameter);
		}
		return this;
	}

	public HttpRequestEntity setRequestObject(Object requestParameter) {
		mParams.setRequestObject(requestParameter);
		return this;
	}

	private HashMap<String, String> buildCommonParams() {
		HashMap<String, String> commonParams = new HashMap<String, String>();
		commonParams.put(NetParamUrlConstant.COMMON_PARAM_KEY_DEVICEID,
				DeviceUtils.getDeviceId(HtmitechApplication.instance()));
		commonParams.put(NetParamUrlConstant.COMMON_PARAM_KEY_MAC,
				DeviceUtils.getWifiMacAddress(HtmitechApplication.instance()));
		commonParams.put(NetParamUrlConstant.COMMON_PARAM_KEY_PLATFORM, "3");
		commonParams.put(NetParamUrlConstant.COMMON_PARAM_KEY_OS_VERSION,
				DeviceUtils.getSystemVersion());
		commonParams.put(NetParamUrlConstant.COMMON_PARAM_KEY_LONGITUDE, "1.0");
		commonParams.put(NetParamUrlConstant.COMMON_PARAM_KEY_LATITUDE, "1.0");
		commonParams.put(NetParamUrlConstant.COMMON_PARAM_KEY_APPVER,
				DeviceUtils.getAppVersionCode(HtmitechApplication.instance())
						+ "");
		commonParams.put(NetParamUrlConstant.COMMON_PARAM_KEY_SCREENWIDTH,
				DeviceUtils.getScreenWidthPx(HtmitechApplication.instance())
						+ "");
		commonParams.put(NetParamUrlConstant.COMMON_PARAM_KEY_SCREENHEIGHT,
				DeviceUtils.getScreenHeightPx(HtmitechApplication.instance())
						+ "");
		commonParams.put(NetParamUrlConstant.COMMON_PARAM_KEY_SCREENDPI,
				DeviceUtils.getScreenScal(HtmitechApplication.instance()) + "");
		commonParams.put(NetParamUrlConstant.COMMON_PARAM_KEY_DEVICETYPE, "3");

		return commonParams;
	}

	public String getUrl() {
		return url;
	}

	public String getPOSTBody() {
		return mParams.toString();
	}

	public List<NameValuePair> getParamsMap() {
		return mParams.getQueryStringParams();
	}

	public RequestParams getRequestParams() {
		return mParams;
	}

}
