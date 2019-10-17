package htmitech.com.componentlibrary.entity;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by Think on 2017/6/7.
 * 扩展字段
 */

public class FormExtensionFiles implements Serializable{
    /**
     * 数据库存储的
     */
    public int ID;
    public String dataId;
    public String formId;
    public String fieldId;
    public String fileName;
    /**
     * 表单下发字段
     */
    public String fileType;
    public String fileSize;
    public String fileCreater;
    public String fileTime;
    public String fileUrl;
    public String fileId;
    public long fileLong;
    public String fileSmallUrl;
    public boolean isNet;
    public String file_path;
    public int status_flag;
    public String fileMediumUrl;

    public long mediaDuration;

    public long getMediaDuration() {
        return mediaDuration;
    }

    public void setMediaDuration(long mediaDuration) {
        this.mediaDuration = mediaDuration;
    }

    public String getFileMediumUrl() {
        return fileMediumUrl;
    }

    public void setFileMediumUrl(String fileMediumUrl) {
        this.fileMediumUrl = fileMediumUrl;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        if(TextUtils.isEmpty(fileSize)){
            fileSize = "0";
        }
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileCreater() {
        return fileCreater;
    }

    public void setFileCreater(String fileCreater) {
        this.fileCreater = fileCreater;
    }

    public String getFileTime() {
        return fileTime;
    }

    public void setFileTime(String fileTime) {
        this.fileTime = fileTime;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public long getFileLong() {
        return fileLong;
    }

    public void setFileLong(long fileLong) {
        this.fileLong = fileLong;
    }

    public String getFileSmallUrl() {
        return fileSmallUrl;
    }

    public void setFileSmallUrl(String fileSmallUrl) {
        this.fileSmallUrl = fileSmallUrl;
    }

    public boolean isNet() {
        return isNet;
    }

    public void setIsNet(boolean isNet) {
        this.isNet = isNet;
    }
}
