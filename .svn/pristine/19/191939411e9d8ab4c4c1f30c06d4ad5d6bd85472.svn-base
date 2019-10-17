package com.htmitech.thread;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.htmitech.myEnum.LogManagerEnum;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import htmitech.com.componentlibrary.content.ComponentConstant;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ResourceUtil;
import htmitech.com.componentlibrary.unit.Utils;

public class MyHttp {


    public static String requestByHttpPost(Context context,Object entity, String url, String funactionCode) throws Exception {
//		String path = "http://114.112.89.94:8081/CZTCloudApi/api/GetMobileData/GetSyncData";
        // 新建HttpPost对象
        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader(HTTP.CONTENT_TYPE, CHTTP.APPLICATION_JSON);
        LogManagerEnum managerEnum = LogManagerEnum.getLogManagerEnum(funactionCode);
        if (managerEnum != null && TextUtils.isEmpty(managerEnum.getFunctionId())) {
            managerEnum.functionId = "0";
        }
        if (managerEnum != null && managerEnum.getFunctionId() != null && !managerEnum.getFunctionId().equals(""))
            httpPost.addHeader("functionLogId", managerEnum.getFunctionId());

        JSONObject deviceInfo = new JSONObject();
        deviceInfo.put("gmsRange", "");
        deviceInfo.put("hasInfraRed", "");
        deviceInfo.put("sysName", "Android");
        deviceInfo.put("sysType", "1");
        deviceInfo.put("sysVersion", Build.VERSION.RELEASE + "");
        deviceInfo.put("screenResolution", Utils.getScreenResolution(context));
        deviceInfo.put("deviceModel", Build.MODEL);
        deviceInfo.put("deviceSn", ((TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
        deviceInfo.put("deviceProductor", Build.MODEL);
        deviceInfo.put("deviceName", URLEncoder.encode(Build.MODEL, "UTF-8"));
        deviceInfo.put("hasBluetooth", "");
        deviceInfo.put("networkingMode", Utils.GetNetworkType(context));
        deviceInfo.put("devRam", Utils.getTotalMemorySize(context));
        httpPost.addHeader("deviceSn", ((TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
        httpPost.addHeader("position", ComponentConstant.scheduleLocationStr);
        httpPost.addHeader("deviceInfo", deviceInfo.toJSONString());
        httpPost.setHeader("Connection", "keep-alive");
        String refreshToken = PreferenceUtils.getRefreshToken();
        String accessToken = PreferenceUtils.getAccessToken();
        httpPost.addHeader("accessToken", accessToken);
        httpPost.addHeader("refreshToken", refreshToken);
//        httpPost.addHeader("Accept-Encoding", "gzip");
        // Post参数
        StringEntity strin = new StringEntity(JSON.toJSON(entity).toString(), "UTF-8");
        strin.setContentType(CHTTP.APPLICATION_JSON);
        strin.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, CHTTP.APPLICATION_JSON));
        // 设置参数实体
        httpPost.setEntity(strin);
        // 获取HttpClient对象
        HttpClient httpClient = new DefaultHttpClient();
        // 获取HttpResponse实例
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        // 读取超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);

        HttpResponse httpResp = httpClient.execute(httpPost);

        int code = httpResp.getStatusLine().getStatusCode();
        // 判断是够请求成功
        if (code == HttpStatus.SC_OK) {
            // 获取返回的数据
            String result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
            return result;
        } else {
            throw new Exception("http request " + code + " is not 200");
        }
    }

    public static String requestGet(String DOWNPATH) {
        {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(DOWNPATH);
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(60000);
                conn.setRequestMethod("GET");
                int code = 0;
                if ((code = conn.getResponseCode()) == HttpStatus.SC_OK) {
                    //解析json
                    String jsonString = readStream(conn.getInputStream());
                    return jsonString;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
        }
        return "";
    }

    private static String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String requestByHttpPostWithHeader(Context context,Object entity, String url, String funactionCode) throws Exception {
//		String path = "http://114.112.89.94:8081/CZTCloudApi/api/GetMobileData/GetSyncData";
        // 新建HttpPost对象
        URL urls = new URL(url);
        String originHost = urls.getHost();
//		String dstIp = httpdnsService.getIpByHost(urls.getHost());
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, CHTTP.APPLICATION_JSON);
        httpPost.addHeader("imei", ((TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
        httpPost.addHeader("device", "android");
        httpPost.addHeader("version", ResourceUtil.getVerName(context.getApplicationContext()));
        httpPost.addHeader("serverIp", PreferenceUtils.getServerIp());
        httpPost.addHeader("loginName",PreferenceUtils.getLogin_name(context));
        LogManagerEnum managerEnum = LogManagerEnum.getLogManagerEnum(funactionCode);
        if (managerEnum != null && !TextUtils.isEmpty(managerEnum.getFunctionId()))
            httpPost.addHeader("functionLogId", managerEnum.getFunctionId());
        httpPost.addHeader("phone-model", Build.MODEL + "|" + Build.VERSION.RELEASE);//设备型号+|+系统版本号
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String date = formatter.format(curDate);
        JSONObject deviceLog = new JSONObject();
        deviceLog.put("loginTime", date);
        deviceLog.put("enableRom", Utils.getAvailMemory(context) + "");
        deviceLog.put("enablePower", "");
        deviceLog.put("sysName", "Android");
//		deviceLog.put("longitude",Utils.getPosition() == null ?"":Utils.getPosition().getLongitude(  )+"");
//		deviceLog.put("latitude",Utils.getPosition() == null ?"":Utils.getPosition().getLatitude()+"");
        deviceLog.put("appVersion", ResourceUtil.getVerCode(context.getApplicationContext()));
        deviceLog.put("deviceName", URLEncoder.encode(Build.MODEL, "UTF-8"));
        deviceLog.put("networkingMode", "");
        deviceLog.put("serverIp", PreferenceUtils.getServerIp());
        deviceLog.put("deviceType", "1");
        deviceLog.put("deviceIp", Utils.getLocalIpAddress() == null ? "" : Utils.getLocalIpAddress());
        deviceLog.put("enableMemory", Utils.getAvailMemory(context.getApplicationContext()) + "");
        deviceLog.put("sysVersion", Build.VERSION.RELEASE + "");
        httpPost.addHeader("deviceLog", deviceLog.toJSONString());
        JSONObject deviceInfo = new JSONObject();
        deviceInfo.put("gmsRange", "");
        deviceInfo.put("hasInfraRed", "");
        deviceInfo.put("sysName", "Android");
        deviceInfo.put("sysType", "1");
        deviceInfo.put("sysVersion", Build.VERSION.RELEASE + "");
        deviceInfo.put("screenResolution", Utils.getScreenResolution(context.getApplicationContext()));
        deviceInfo.put("deviceModel", Build.MODEL);
        deviceInfo.put("deviceSn", ((TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
        deviceInfo.put("deviceProductor", Build.MODEL);
        deviceInfo.put("deviceName", URLEncoder.encode(Build.MODEL, "UTF-8"));
        deviceInfo.put("hasBluetooth", "");
        deviceInfo.put("networkingMode", Utils.GetNetworkType(context.getApplicationContext()));
        deviceInfo.put("devRam", Utils.getTotalMemorySize(context.getApplicationContext()));
        httpPost.addHeader("deviceInfo", deviceInfo.toJSONString());
        httpPost.setHeader("Connection", "keep-alive");
//        httpPost.addHeader("Accept-Encoding", "gzip");
        // Post参数
        if (entity != null) {
//			StringEntity strin = new StringEntity(JSON.toJSON(entity).toString(), "UTF-8");
//			strin.setContentType(CHTTP.APPLICATION_JSON);
//			strin.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, CHTTP.CONTENT_TYPE_TEXT_JSON));
            HttpEntity entitys = null;
            int flag = 0;
            if (entity instanceof ArrayList) {
                ArrayList list = (ArrayList) entity;
                if (list.get(0) instanceof BasicNameValuePair) {
                    flag = 1;
                } else {
                    flag = 0;
                }
            } else if ((entity instanceof File)) {
                flag = 2;
            }
            try {
                if (flag == 0) {
                    StringEntity strin = new StringEntity(JSON.toJSON(entity).toString(), "UTF-8");
                    strin.setContentType(CHTTP.APPLICATION_JSON);
                    strin.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, CHTTP.CONTENT_TYPE_TEXT_JSON));
                    // 设置参数实体
                    httpPost.setEntity(strin);
                } else if (flag == 1) {
                    List<NameValuePair> mNameValuePairList = (List<NameValuePair>) entity;
                    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                    entitys = new UrlEncodedFormEntity(mNameValuePairList, "UTF-8");
                    // 设置参数实体
                    httpPost.setEntity(entitys);

                } else if (flag == 2) {
                    MultipartEntity multipartEntity = new MultipartEntity();
                    FileBody fileBody = new FileBody((File) entity);
                    multipartEntity.addPart("file", fileBody);
                    httpPost.setHeader("Content-Type", String.valueOf(multipartEntity.getContentType().getValue()));
                    httpPost.setEntity(multipartEntity);
                }

            } catch (Exception e) {
                StringEntity strin = new StringEntity(JSON.toJSON(entity).toString(), "UTF-8");
                strin.setContentType(CHTTP.APPLICATION_JSON);
                strin.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, CHTTP.CONTENT_TYPE_TEXT_JSON));
                // 设置参数实体
                httpPost.setEntity(strin);
            }


        }
        // 获取HttpClient对象
        HttpClient httpClient = new DefaultHttpClient();
        // 获取HttpResponse实例
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        // 读取超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        // 获取HttpResponse实例
        HttpResponse httpResp = httpClient.execute(httpPost);
        int code = httpResp.getStatusLine().getStatusCode();
        // 判断是够请求成功
        if (code == HttpStatus.SC_OK) {
            // 获取返回的数据
            String result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
            return result;
        } else {
            throw new Exception("http request " + code + " is not 200");
        }
    }

    /*
    * 自定义头信息的post请求
    * */
    public static String requestByHttpPostWithHeader(Object entity, String url, Map<String, String> headers) throws Exception {
        // 新建HttpPost对象
        HttpPost httpPost = new HttpPost(url);
        Iterator<Map.Entry<String, String>> iterator = headers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        if (entity != null) {
            HttpEntity entitys = new UrlEncodedFormEntity((List<NameValuePair>) entity);
            // 设置参数实体
            httpPost.setEntity(entitys);
        }
        // 获取HttpClient对象
        HttpClient httpClient = new DefaultHttpClient();
        // 获取HttpResponse实例
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        // 读取超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        // 获取HttpResponse实例
        HttpResponse httpResp = httpClient.execute(httpPost);
        int code = httpResp.getStatusLine().getStatusCode();
        // 判断是够请求成功
        if (code == HttpStatus.SC_OK) {
            // 获取返回的数据
            String result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
            return result;
        } else {
            throw new Exception("http request " + code + " is not 200");
        }
    }

    public static String requestByHttpPostWithToken(Context context,Object entity, String url, String funactionCode) throws Exception {
//		String path = "http://114.112.89.94:8081/CZTCloudApi/api/GetMobileData/GetSyncData";
        // 新建HttpPost对象
        String refreshToken = PreferenceUtils.getRefreshToken();
        String accessToken = PreferenceUtils.getAccessToken();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, CHTTP.APPLICATION_JSON);
        httpPost.addHeader("imei", ((TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
        httpPost.addHeader("device", "andriod");
        httpPost.addHeader("version", ResourceUtil.getVerName(context.getApplicationContext()));
        httpPost.addHeader("server-version", "1.0");
        httpPost.addHeader("phone-model", Build.MODEL + "|" + Build.VERSION.RELEASE);//设备型号+|+系统版本号
        httpPost.addHeader("accessToken", accessToken);
        httpPost.addHeader("refreshToken", refreshToken);
        httpPost.setHeader("Connection", "keep-alive");
//        httpPost.addHeader("Accept-Encoding", "gzip");
        LogManagerEnum managerEnum = LogManagerEnum.getLogManagerEnum(funactionCode);
        if (managerEnum != null && !managerEnum.getFunctionId().equals(""))
            httpPost.addHeader("functionLogId", managerEnum.getFunctionId());
        // Post参数
        if (entity != null) {
//			StringEntity strin = new StringEntity(JSON.toJSON(entity).toString(), "UTF-8");
//			strin.setContentType(CHTTP.APPLICATION_JSON);
//			strin.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, CHTTP.CONTENT_TYPE_TEXT_JSON));
            HttpEntity entitys = null;
            int flag = 0;
            if (entity instanceof ArrayList) {
                ArrayList list = (ArrayList) entity;
                if (list.get(0) instanceof BasicNameValuePair) {
                    flag = 1;
                } else {
                    flag = 0;
                }
            } else if ((entity instanceof File)) {
                flag = 2;
            }
            try {
                if (flag == 0) {
                    StringEntity strin = new StringEntity(JSON.toJSON(entity).toString(), "UTF-8");
                    strin.setContentType(CHTTP.APPLICATION_JSON);
                    strin.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, CHTTP.CONTENT_TYPE_TEXT_JSON));
                    // 设置参数实体
                    httpPost.setEntity(strin);
                } else if (flag == 1) {
                    List<NameValuePair> mNameValuePairList = (List<NameValuePair>) entity;
                    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                    entitys = new UrlEncodedFormEntity(mNameValuePairList, "UTF-8");
                    // 设置参数实体
                    httpPost.setEntity(entitys);

                } else if (flag == 2) {
                    MultipartEntity multipartEntity = new MultipartEntity();
                    FileBody fileBody = new FileBody((File) entity);
                    multipartEntity.addPart("file", fileBody);
                    httpPost.setHeader("Content-Type", String.valueOf(multipartEntity.getContentType().getValue()));
//                    httpPost.setHeader("Connection", "keep-alive");
                    httpPost.setEntity(multipartEntity);
                }

            } catch (Exception e) {
                StringEntity strin = new StringEntity(JSON.toJSON(entity).toString(), "UTF-8");
                strin.setContentType(CHTTP.APPLICATION_JSON);
                strin.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, CHTTP.CONTENT_TYPE_TEXT_JSON));
                // 设置参数实体
                httpPost.setEntity(strin);
            }


        }
        // 获取HttpClient对象
        HttpClient httpClient = new DefaultHttpClient();
        // 获取HttpResponse实例
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        // 读取超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        // 获取HttpResponse实例
        HttpResponse httpResp = httpClient.execute(httpPost);
        int code = httpResp.getStatusLine().getStatusCode();
        // 判断是够请求成功
        if (code == HttpStatus.SC_OK) {
            // 获取返回的数据
            String result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
            return result;
        } else {
            throw new Exception("http request " + code + " is not 200");
        }
    }


    public static String requestByHttpPostWithTokenUpLoad(Context context, final Object entity, String url, String funactionCode) throws Exception {
//		String path = "http://114.112.89.94:8081/CZTCloudApi/api/GetMobileData/GetSyncData";
        // 新建HttpPost对象
        String refreshToken = PreferenceUtils.getRefreshToken();
        String accessToken = PreferenceUtils.getAccessToken();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, CHTTP.APPLICATION_JSON);
        httpPost.addHeader("imei", ((TelephonyManager) context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId());
        httpPost.addHeader("device", "andriod");
        httpPost.addHeader("version", ResourceUtil.getVerName(context.getApplicationContext()));
        httpPost.addHeader("server-version", "1.0");
        httpPost.addHeader("phone-model", Build.MODEL + "|" + Build.VERSION.RELEASE);//设备型号+|+系统版本号
        httpPost.addHeader("accessToken", accessToken);
        httpPost.addHeader("refreshToken", refreshToken);
        httpPost.setHeader("Connection", "keep-alive");
//        httpPost.addHeader("Accept-Encoding", "gzip");
        LogManagerEnum managerEnum = LogManagerEnum.getLogManagerEnum(funactionCode);
        if (managerEnum != null && !managerEnum.getFunctionId().equals(""))
            httpPost.addHeader("functionLogId", managerEnum.getFunctionId());
        // Post参数
        if (entity != null) {
//			StringEntity strin = new StringEntity(JSON.toJSON(entity).toString(), "UTF-8");
//			strin.setContentType(CHTTP.APPLICATION_JSON);
//			strin.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, CHTTP.CONTENT_TYPE_TEXT_JSON));
            HttpEntity entitys = null;
            int flag = 0;
            if (entity instanceof ArrayList) {
                ArrayList list = (ArrayList) entity;
                if (list.get(0) instanceof BasicNameValuePair) {
                    flag = 1;
                } else {
                    flag = 0;
                }
            } else if ((entity instanceof File)) {
                flag = 2;
            }
            try {
                if (flag == 0) {
                    StringEntity strin = new StringEntity(JSON.toJSON(entity).toString(), "UTF-8");
                    strin.setContentType(CHTTP.APPLICATION_JSON);
                    strin.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, CHTTP.CONTENT_TYPE_TEXT_JSON));
                    // 设置参数实体
                    httpPost.setEntity(strin);
                } else if (flag == 1) {
                    List<NameValuePair> mNameValuePairList = (List<NameValuePair>) entity;
                    httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                    entitys = new UrlEncodedFormEntity(mNameValuePairList, "UTF-8");
                    // 设置参数实体
                    httpPost.setEntity(entitys);

                } else if (flag == 2) {
                    MultipartEntity multipartEntity = new MultipartEntity();
                    FileBody fileBody = new FileBody((File) entity);
                    StringBody  stringBody = new StringBody("appvisit");
                    multipartEntity.addPart("uploadFile", fileBody);
                    multipartEntity.addPart("logType", stringBody);
                    httpPost.setHeader("Content-Type", String.valueOf(multipartEntity.getContentType().getValue()));
//                    httpPost.setHeader("Connection", "keep-alive");
                    httpPost.setEntity(multipartEntity);
                }

            } catch (Exception e) {
                StringEntity strin = new StringEntity(JSON.toJSON(entity).toString(), "UTF-8");
                strin.setContentType(CHTTP.APPLICATION_JSON);
                strin.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, CHTTP.CONTENT_TYPE_TEXT_JSON));
                // 设置参数实体
                httpPost.setEntity(strin);
            }


        }
        // 获取HttpClient对象
        HttpClient httpClient = new DefaultHttpClient();
        // 获取HttpResponse实例
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        // 读取超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        // 获取HttpResponse实例
        HttpResponse httpResp = httpClient.execute(httpPost);
        int code = httpResp.getStatusLine().getStatusCode();
        // 判断是够请求成功
        if (code == HttpStatus.SC_OK) {
            // 获取返回的数据
            String result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
            return result;
        } else {
            throw new Exception("http request " + code + " is not 200");
        }
    }

}
