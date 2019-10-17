package com.htmitech.ztcustom.zt.unit;

import com.alibaba.fastjson.JSON;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class MyHttp {
    public static String requestByHttpPost(Object entity, String url)
            throws Exception {
        try {
            // 新建HttpPost对象
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, CHTTP.APPLICATION_JSON);
            // Post参数
            StringEntity strin = new StringEntity(JSON.toJSON(entity).toString(), "UTF-8");
            strin.setContentType(CHTTP.APPLICATION_JSON);
            strin.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
                    CHTTP.APPLICATION_JSON));
            // 设置参数实体
            httpPost.setEntity(strin);
            // 获取HttpClient对象
            HttpClient httpClient = new DefaultHttpClient();
            // 获取HttpResponse实例
            HttpResponse httpResp = httpClient.execute(httpPost);
            // 请求超时
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
            // 读取超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
            int code = httpResp.getStatusLine().getStatusCode();
            // 判断是够请求成功
            if (code == HttpStatus.SC_OK) {
                // 获取返回的数据
                String result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
                return result;
            } else {
                throw new Exception("http request " + code + " is not 200");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * http GET
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String requestByHttpGet(String url) throws Exception {
        HttpGet httpRequest = new HttpGet(url);
        String strResult = "";
        /* 发送请求并等待响应 */
        HttpResponse httpResponse = new DefaultHttpClient()
                .execute(httpRequest);
        int code = httpResponse.getStatusLine().getStatusCode();
        /* 若状态码为200 ok */
        if (code == HttpStatus.SC_OK) {
            strResult = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");

        } else {
            throw new Exception("http request " + code + " is not 200");
        }
        return strResult;
    }


}
