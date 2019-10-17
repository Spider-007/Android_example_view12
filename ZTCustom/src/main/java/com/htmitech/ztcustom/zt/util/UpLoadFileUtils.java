package com.htmitech.ztcustom.zt.util;


import android.content.Context;
import android.util.Log;

import com.htmitech.ztcustom.zt.constant.ContantValues;
import com.htmitech.ztcustom.zt.db.QualityObjectionDao;
import com.htmitech.ztcustom.zt.domain.QualityObjectionUploadFileResult;
import com.htmitech.ztcustom.zt.interfaces.UploadFileCallBackListener;

import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传文件
 * Created by heyang on 2017-5-9.
 */
public class UpLoadFileUtils {

    public String state = "";


    /**
     * 上传文件
     *
     * @param filePath
     * @param fileid
     */
    public void uploadFile(final String filePath, final String userid, String id, final String fileid, final Context context, final boolean reUpload, final int FILE_ID, final UploadFileCallBackListener listener) {


        //构建RequestParams对象，传入请求的服务器地址URL
        RequestParams params = new RequestParams(ContantValues.UPLOADFILEPATH);
        params.setAsJsonContent(true);
        params.setMultipart(true);
        params.addHeader("userid", userid);
        params.addHeader("id", id);
        params.addHeader("fileid", fileid);
        params.setConnectTimeout(15000);
        params.setReadTimeout(15000);
        List<KeyValue> list = new ArrayList<KeyValue>();
        list.add(new KeyValue("data", new File(filePath)));
        MultipartBody body = new MultipartBody(list, "UTF-8");
        params.setRequestBody(body);
//        params.addBodyParameter("data", new File(filePath), "multipart/form-data");
        x.http().post(params, new org.xutils.common.Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //成功
                Log.e("ddddddddddddddddddddd", result);
                QualityObjectionUploadFileResult bean = new QualityObjectionUploadFileResult();
                bean = FastJsonUtils.getPerson(result, QualityObjectionUploadFileResult.class);
                if (reUpload) {
                    if (bean.success) {//提报成功
                        state = "success";
                        try {
                            new QualityObjectionDao(context).updateFileState(FILE_ID);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (bean.success) {//提报成功
                        state = "success";
                        try {
                            new QualityObjectionDao(context).insertIntoFile(fileid, getFileName(filePath), filePath, "zlyy", "2", userid);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {//提报失败
                        state = "fail";
                        try {
                            new QualityObjectionDao(context).insertIntoFile(fileid, getFileName(filePath), filePath, "zlyy", "1", userid);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFinished() {
                //上传完成
                Log.e("ffffffffffff", "完成了");
                listener.fileCallBack(state);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                //取消上传
                Log.e("ccccccccccccccccccc", "取消上传文件");
                state = "cancle";
                cex.printStackTrace();
                listener.fileCallBack(state);
                if (reUpload) {

                } else {
                    try {
                        new QualityObjectionDao(context).insertIntoFile(fileid, getFileName(filePath), filePath, "zlyy", "1", userid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //上传失败
                Log.e("eeeeeeeeeeeeeeeee", "上传文件失败");
                ex.printStackTrace();
                state = "fail";
                if (reUpload) {

                } else {
                    try {
                        new QualityObjectionDao(context).insertIntoFile(fileid, getFileName(filePath), filePath, "zlyy", "1", userid);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 根据路径获取文件名
     *
     * @param path 路径参数
     * @return 文件名字符串
     */
    private String getFileName(String path) throws Exception {
        // 判空操作必须要有 , 处理方式不唯一 , 根据实际情况可选其一 。
        if (path == null) {
            throw new Exception("路径不能为null"); // 处理方法一
        }
        int start = path.lastIndexOf("/");
        int end = path.lastIndexOf(".");
        if (start != -1 && end != -1) {
            return path.substring(start + 1, end);//包含头不包含尾 , 故:头 + 1
        } else {
            return "";
        }
    }
}
