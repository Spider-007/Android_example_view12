package com.htmitech_updown.updownloadmanagement;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.utils.FastJsonUtils;
import com.htmitech_updown.updownloadmanagement.db.DbUtil;
import com.htmitech_updown.updownloadmanagement.uploadbean.ChunkInfo;
import com.htmitech_updown.updownloadmanagement.uploadbean.GetUploadBatchResultBean;
import com.htmitech_updown.updownloadmanagement.uploadbean.UploadFileInfoBean;
import com.htmitech_updown.updownloadmanagement.uploadfile.Md5Utils;
import com.htmitech_updown.updownloadmanagement.uploadfile.UploadManager;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import htmitech.com.componentlibrary.entity.bigfileentity.SaveBigFileExtFields;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;
import htmitech.com.componentlibrary.unit.NetConnectionUtils;
import htmitech.com.componentlibrary.unit.PreferenceUtils;
import htmitech.com.componentlibrary.unit.ServerUrlConstant;

/**
 * Created by heyang on 2018/7/12.
 */
public class UploadBigFileFactory {
    private SaveBigFileExtFields submitBean;

    private Context context;

    private String getFileBatchPath = ServerUrlConstant.SERVER_JAVA_URL() + ServerUrlConstant.GET_BIGFILE_BATCH;
//    private String getFileBatchPath = "http://htrf.dscloud.me:8083/data-crab/filecontroller/createBigfileBatch";

    private String GET_FILE_BATCH = "getFileBatchPath";

    private static UploadBigFileFactory instance;


    private UploadBigFileFactory() {
        Log.e("contrst", "123");
    }


    public static UploadBigFileFactory get() {
        if (instance == null) {
            instance = new UploadBigFileFactory();
        }
        return instance;
    }

    public UploadBigFileFactory setSaveBigFileExtField(SaveBigFileExtFields submitBean) {
        this.submitBean = submitBean;
        return instance;
    }

    public UploadBigFileFactory setContext(Context context) {
        this.context = context;
        return instance;
    }


    public void createUploadTask(String path, String taskName, String formId, String uniqueId, int mediaDuration) {
        if (context == null || submitBean == null) {
            throw new NullPointerException("NullPointerException");
        }
        File file = new File(path);
        if (path.equals("")) {
            Toast.makeText(context, "请选择文件", Toast.LENGTH_SHORT).show();
        } else if (!file.exists()) {
            Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
        } else {
            //文件初始化时先把信息存到数据库
            ChunkInfo fileInfo = new ChunkInfo();
            fileInfo.setFilePath(path);
            fileInfo.setFileLength(file.length());
            if (file.length() > 1024l * 1024l) {
                fileInfo.setIsChunk(true);
            } else {
                fileInfo.setIsChunk(false);
            }
            int chunks = (int) (file.length() / UploadFileContast.chunkSize + (file.length() % UploadFileContast.chunkSize > 0 ? 1 : 0));
            fileInfo.setChunks(chunks);
            fileInfo.setUploadStatus(0);
            fileInfo.setFormId(formId);
            fileInfo.setUniqueId(uniqueId);
            String sbumitBeanStr = JSON.toJSONString(submitBean);
            fileInfo.setSubmitBean(sbumitBeanStr);
            fileInfo.setFileName(path.substring(path.lastIndexOf("/") + 1));
            long time1 = System.currentTimeMillis();
            final String md5 = Md5Utils.getFileMd5(file) + time1;//文件的MD5加上一个当前时间戳
            fileInfo.setMd5(md5);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String createTime = df.format(new Date());// new Date()为获取当前系统时间
            fileInfo.setCreateTime(createTime);
            fileInfo.setTaskName(taskName);
            fileInfo.setMediaDuration(mediaDuration);

            DbUtil.addUpDownState(fileInfo);
            //获取文件号
            AnsynHttpRequest.requestByPostWithToken(context, null, getFileBatchPath, CHTTP.POSTWITHTOKEN, new ObserverCallBackType() {
                @Override
                public void success(String requestValue, int type, String requestName) {
                    requestValue = GetFinalRefreshTokenAfterRequestValue.getFinalRequestValue(context, requestValue, type, getFileBatchPath, null, this, requestName, null);
                    if (null != requestValue) {
                        GetUploadBatchResultBean batchResultBean = FastJsonUtils.getPerson(requestValue, GetUploadBatchResultBean.class);
                        if (batchResultBean.code == 200) {
                            if (batchResultBean.result != null && !batchResultBean.result.equals("")) {
                                UploadFileInfoBean uploadFileInfoBean = DbUtil.getUploadFileInfoSCLB(requestName);
                                ChunkInfo fileInfo = new ChunkInfo();
                                fileInfo.setFilePath(uploadFileInfoBean.filePath);
                                fileInfo.setFileLength(Long.parseLong(uploadFileInfoBean.len));
                                fileInfo.setIsChunk(uploadFileInfoBean.isChunk.equals("1") ? true : false);
                                fileInfo.setUploadStatus(0);
                                fileInfo.setChunks(uploadFileInfoBean.chunks);
                                fileInfo.setFormId(uploadFileInfoBean.formId);
                                fileInfo.setUniqueId(uploadFileInfoBean.uniqueId);
                                fileInfo.setSubmitBean(uploadFileInfoBean.submitBean);
                                fileInfo.setBatchNumber(batchResultBean.result);
                                fileInfo.setFileName(uploadFileInfoBean.fileName);
                                fileInfo.setMd5(requestName);
                                fileInfo.setCreateTime(uploadFileInfoBean.createTime);
                                fileInfo.setTaskName(uploadFileInfoBean.taskName);
                                fileInfo.setMediaDuration(uploadFileInfoBean.mediaDuration);
                                DbUtil.addUpDownState(fileInfo);
                                boolean isUploadNow = false;
                                if (NetConnectionUtils.hasNetWorkConnection(context)) {//有网
                                    //判断是不是有wifi，当前网络环境
                                    if (NetConnectionUtils.hasWifiConnection(context)) {
                                        isUploadNow = true;
                                    } else {
                                        int code = PreferenceUtils.getUploadBigFileNet(context);
                                        if (code == 1 || code == 2) {
                                            isUploadNow = true;
                                        }
                                    }
                                }
                                if (isUploadNow) {
                                    UploadManager.getInstance().addUploadTask(fileInfo);
                                }
                            }
                        }
                    }
                }

                @Override
                public void fail(String exceptionMessage, int type, String requestName) {
                    Log.e("AnsynHttpRequest", "fail");
                }

                @Override
                public void notNetwork() {
                    Log.e("AnsynHttpRequest", "notNetwork");
                }

                @Override
                public void callbackMainUI(String successMessage) {

                }
            }, md5, null);
        }
    }
}
