package com.htmitech_updown.updownloadmanagement.uploadbean;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.htmitech.thread.AnsynHttpRequest;
import com.htmitech.thread.CHTTP;
import com.htmitech.thread.GetFinalRefreshTokenAfterRequestValue;
import com.htmitech.utils.FastJsonUtils;
import com.htmitech_updown.updownloadmanagement.BigFileSaveExtFieldsTask;
import com.htmitech_updown.updownloadmanagement.UploadFileContast;
import com.htmitech_updown.updownloadmanagement.db.DbUtil;
import com.htmitech_updown.updownloadmanagement.uploadfile.Md5Utils;
import com.htmitech_updown.updownloadmanagement.uploadfile.UpFileToServer;
import com.htmitech_updown.updownloadmanagement.uploadfile.UploadManager;
import com.htmitech_updown.updownloadmanagement.uploadfile.UploadStatus;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import htmitech.com.componentlibrary.entity.bigfileentity.SaveBigFileExtFields;
import htmitech.com.componentlibrary.listener.ObserverCallBackType;

/**
 * Created by Administrator on 2018-7-4.
 */

public class ChunkInfo extends FileInfo implements Serializable, Runnable {
    /**
     * 文件的当前分片值
     */
    private int chunk = 0;
    /**
     * 文件总分片值
     */
    private int chunks = 1;
    /**
     * 下载进度值
     */
    private int progress = 1;
    /**
     * 批次号需要请求
     */
    private String batchNumber;
    /**
     * 下载状态
     */
    private int uploadStatus;

    private int uploadStatusTemp;

    /**
     * 是否正在下载
     */

    private boolean isDownLoading = false;
    /**
     * 当前对象
     */
    private Context context;

    private String taskName;

    private String formId;

    private String uniqueId;

    private String submitBean;

    public String getSubmitBean() {
        return submitBean;
    }

    public void setSubmitBean(String submitBean) {
        this.submitBean = submitBean;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(int uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public int getUploadStatusTemp() {
        return uploadStatusTemp;
    }

    public void setUploadStatusTemp(int uploadStatu) {
        this.uploadStatusTemp = uploadStatu;
    }

    public boolean isDownLoading() {
        return isDownLoading;
    }

    public void setDownLoading(boolean downLoading) {
        isDownLoading = downLoading;
    }

    public int getChunks() {
        return chunks;
    }

    public void setChunks(int chunks) {
        this.chunks = chunks;
    }

    public int getChunk() {
        return chunk;
    }

    public void setChunk(int chunk) {
        this.chunk = chunk;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    @Override
    public String toString() {
        return "ChunkInfo{" +
                "chunk=" + chunk +
                ", chunks=" + chunks +
                ", progress=" + progress +
                ", batchNumber=" + batchNumber +
                '}';
    }

    @Override
    public void run() {
        String upResult;
        if (!isChunk()) {
            ChunkInfo chunkInfo = new ChunkInfo();
            chunkInfo.setChunk(0);
            chunkInfo.setChunks(1);
            chunkInfo.setFilePath(getFilePath());
            chunkInfo.setMediaDuration(getMediaDuration());
            chunkInfo.setMd5(this.getMd5());
            upResult = new UpFileToServer(context).upToServer(chunkInfo, "upchunk");
            System.err.print(upResult);
            if (upResult.equals("-1") || upResult.equals("500")) {
                //上传异常，给出提示
                Intent intent = new Intent();
                intent.putExtra("ErrorIntent", upResult);
                intent.setAction("updownloaderror");
                context.sendBroadcast(intent);
            } else {
                SmallFileUploadResultBean bean = FastJsonUtils.getPerson(upResult, SmallFileUploadResultBean.class);
                if (bean != null) {
                    if (bean.code == 200) {
                        DbUtil.addUpDownSuccessState(this, bean.result);
                        Intent intent = new Intent();
                        intent.putExtra("finishIntent", "finish");
                        intent.setAction("updownloadfinish");
                        context.sendBroadcast(intent);
                        boolean flag = true;
                        List<UploadFileInfoBean> uploadFileInfoBeanList = DbUtil.getUploadFileInfoUniqueIdList(this.uniqueId);
                        String bigFileSaveExtFieldsBeanString = "";
                        if (uploadFileInfoBeanList.size() > 0) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                        bigFileSaveExtFieldsBeanString = this.submitBean;
                        if (flag) {
                            if (!bigFileSaveExtFieldsBeanString.equals("")) {
                                SaveBigFileExtFields saveBigFileExtFields = FastJsonUtils.getPerson(bigFileSaveExtFieldsBeanString, SaveBigFileExtFields.class);
                                for (int i = 0; i < saveBigFileExtFields.editFields.size(); i++) {
                                    if (this.uniqueId.equals(saveBigFileExtFields.editFields.get(i).uniqueId)) {
                                        saveBigFileExtFields.editFields.get(i).value = bean.result.fileId;
                                        saveBigFileExtFields.editFields.get(i).fieldFileCount = uploadFileInfoBeanList.size() + "";
                                    }
                                }
                                //开始上传
                                new BigFileSaveExtFieldsTask(context, saveBigFileExtFields);
                            }
                        }
                    }
                }
            }
        } else {
            long fileLength = getFileLength();
            int chunks = (int) (fileLength / UploadFileContast.chunkSize + (fileLength % UploadFileContast.chunkSize > 0 ? 1 : 0));
            this.chunks = chunks;
            int currentChunks = chunk;
            while (currentChunks < chunks) {
                ChunkInfo chunkInfo = new ChunkInfo();
                chunkInfo.setMd5(getMd5());
                chunkInfo.setChunk(currentChunks);
                chunkInfo.setBatchNumber(this.batchNumber);
                chunkInfo.setUniqueId(this.uniqueId);
                chunkInfo.setMd5(this.getMd5());
                chunkInfo.setChunks(chunks);
                chunkInfo.setFilePath(getFilePath());
                chunkInfo.setFileName(getFileName());
                chunkInfo.setCreateTime(getCreateTime());
                chunkInfo.setMediaDuration(getMediaDuration());
                if (uploadStatus == UploadStatus.UPLOAD_STATUS_PAUSE || uploadStatus == UploadStatus.UPLOAD_STATUS_ERROR) {
                    this.setDownLoading(false);
                    if (uploadStatus == UploadStatus.UPLOAD_STATUS_ERROR) {
                        chunk--;
                        uploadStatusTemp = UploadStatus.UPLOAD_STATUS_ERROR;
                        DbUtil.addUpDownState(this);
                    } else if (uploadStatus == UploadStatus.UPLOAD_STATUS_PAUSE) {
                        uploadStatusTemp = UploadStatus.UPLOAD_STATUS_PAUSE;
                        DbUtil.addUpDownState(this);
                    }
                    break;
                }
                uploadStatus = UploadStatus.UPLOAD_STATUS_UPLOADING;
                uploadStatusTemp = uploadStatus;
                upResult = new UpFileToServer(context).upToServer(chunkInfo, "upchunks");
                Log.e("RESULT", chunkInfo.chunk + 1 + "===" + upResult);
                if (upResult.equals("-1") || upResult.equals("500")) {
                    uploadStatus = UploadStatus.UPLOAD_STATUS_ERROR;
                    //上传异常，给出提示
                    Intent intent = new Intent();
                    intent.putExtra("ErrorIntent", upResult);
                    intent.setAction("updownloaderror");
                    context.sendBroadcast(intent);
                } else {
                    RequestResultBean bean = JSON.parseObject(upResult, RequestResultBean.class);
                    if (bean != null) {
                        if (bean.code != 200) {
                            if (bean.code == 400 && bean.debugMsg.contains("文件碎片已上传，不能重复上传")) {

                            } else {
                                uploadStatus = UploadStatus.UPLOAD_STATUS_ERROR;
                            }
                        } else {
                            RequestSuccessResultBean resultBean = JSON.parseObject(upResult, RequestSuccessResultBean.class);
                            if (resultBean != null && resultBean.result != null) {
                                if (resultBean.code == 200 && resultBean.result.stateMsg.contains("文件碎片上传完成")) {//碎片上传成功
                                    DbUtil.addUpDownState(this);
                                }
                            }
                            if (resultBean != null && resultBean.result != null && resultBean.result.fsFileInfo != null) {//文件全部上传成功
                                if (resultBean.result.stateMsg.contains("所有文件已上传完成")) {//文件上传成功
                                    this.chunk++;
                                    DbUtil.addUpDownSuccessState(this, resultBean.result.fsFileInfo);
                                    Intent intent = new Intent();
                                    intent.putExtra("finishIntent", "finish");
                                    intent.putExtra("task_id", chunkInfo.getMd5());
                                    intent.setAction("updownloadfinish");
                                    context.sendBroadcast(intent);
//                                    //判断是否是当前流程所有的文件都上传成功，如果成功了就调用接口，把文件ID和流程ID绑定
//                                    List<UploadFileInfoBean> uploadFileInfoBeanList = DbUtil.getUploadFileInfoFlowIdList(this.formId);
//                                    boolean flag = true;
//                                    if (uploadFileInfoBeanList != null) {
//                                        for (int i = 0; i < uploadFileInfoBeanList.size(); i++) {
//                                            if (uploadFileInfoBeanList.get(i).status.equals("0")) {
//                                                flag = false;//只要有没上传完的就跳出
//                                                break;
//                                            }
//                                        }
//                                        String bigFileSaveExtFieldsBeanString = "";
//                                        if (uploadFileInfoBeanList.size() == 0) {
//                                            flag = false;
//                                        } else {
//                                            bigFileSaveExtFieldsBeanString = uploadFileInfoBeanList.get(0).submitBean;
//                                        }
//                                        if (flag) {//都上传完成了，可以调用接口，将流程ID和上传的文件绑定
//                                            HashMap<String, String> hashMap = new HashMap<>();
//                                            ArrayList<String> keyList = new ArrayList<>();
//                                            for (int i = 0; i < uploadFileInfoBeanList.size(); i++) {
//                                                if (!keyList.contains(uploadFileInfoBeanList.get(i).uniqueId)) {
//                                                    keyList.add(uploadFileInfoBeanList.get(i).uniqueId);
//                                                }
//                                            }
//                                            for (int j = 0; j < keyList.size(); j++) {
//                                                String value = "";
//                                                for (int i = 0; i < uploadFileInfoBeanList.size(); i++) {
//                                                    if (uploadFileInfoBeanList.get(i).uniqueId.equals(keyList.get(j))) {
//                                                        value = value + uploadFileInfoBeanList.get(i).fileId + ",";
//                                                    }
//                                                }
//                                                value = value.substring(0, value.length() - 1);
//                                                hashMap.put(keyList.get(j), value);
//                                            }
//
//                                            if (!bigFileSaveExtFieldsBeanString.equals("")) {
//                                                SaveBigFileExtFields saveBigFileExtFields = FastJsonUtils.getPerson(bigFileSaveExtFieldsBeanString, SaveBigFileExtFields.class);
//                                                for (int i = 0; i < saveBigFileExtFields.editFields.size(); i++) {
//                                                    saveBigFileExtFields.editFields.get(i).value = hashMap.get(saveBigFileExtFields.editFields.get(i).uniqueId);
//                                                }
//                                                //开始上传
//                                                new BigFileSaveExtFieldsTask(context, saveBigFileExtFields);
//                                            }
//                                        }
//                                    }

                                    //每上传成功一个文件就提交一次

                                    boolean flag = true;
                                    List<UploadFileInfoBean> uploadFileInfoBeanList = DbUtil.getUploadFileInfoUniqueIdList(this.uniqueId);
                                    String bigFileSaveExtFieldsBeanString = "";
                                    if (uploadFileInfoBeanList.size() > 0) {
                                        flag = true;
                                    } else {
                                        flag = false;
                                    }
                                    bigFileSaveExtFieldsBeanString = this.submitBean;
                                    if (flag) {
                                        if (!bigFileSaveExtFieldsBeanString.equals("")) {
                                            SaveBigFileExtFields saveBigFileExtFields = FastJsonUtils.getPerson(bigFileSaveExtFieldsBeanString, SaveBigFileExtFields.class);
                                            for (int i = 0; i < saveBigFileExtFields.editFields.size(); i++) {
                                                if (this.uniqueId.equals(saveBigFileExtFields.editFields.get(i).uniqueId)) {
                                                    saveBigFileExtFields.editFields.get(i).value = resultBean.result.fsFileInfo.fileId;
//                                                    saveBigFileExtFields.editFields.get(i).fieldFileCount = uploadFileInfoBeanList.size() + "";
                                                }
                                            }
                                            //开始上传
                                            new BigFileSaveExtFieldsTask(context, saveBigFileExtFields);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        uploadStatus = UploadStatus.UPLOAD_STATUS_ERROR;
                    }
                }
                System.err.print(upResult);
                currentChunks++;
                chunk = currentChunks;
            }
        }
    }

}