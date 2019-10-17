package com.htmitech_updown.updownloadmanagement.uploadbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;

/**
 * Created by Administrator on 2018-7-4.
 */

public class FileInfo implements Serializable {
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件名字
     */
    private String fileName;
    /**
     * 管理员guid
     */
    private String gguid;

    /**
     * 文件的MD5值
     */
    private String md5;
    /**
     * 文件长度
     */
    private long fileLength;


    private long fileSize;

    private int mediaDuration;

    /**
     * 是不是一个数据块
     * true 表示是一个文件，false表示是一个块（分片的数据）
     */
    private boolean isChunk;

    private String createTime;

    public int getMediaDuration() {
        return mediaDuration;
    }

    public void setMediaDuration(int mediaDuration) {
        this.mediaDuration = mediaDuration;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getGguid() {
        return gguid;
    }

    public void setGguid(String gguid) {
        this.gguid = gguid;
    }


    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean isChunk() {
        return isChunk;
    }

    public void setIsChunk(boolean isChunk) {
        this.isChunk = isChunk;
    }

    public long getFileLength() {
        return fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getFileSize() {
        if (fileSize == 0) {
            try {
                return getFileSize(new File(filePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return fileSize;
    }

    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "filePath='" + filePath + '\'' +
                ", gguid='" + gguid + '\'' +
                ", md5='" + md5 + '\'' +
                ", fileLength=" + fileLength +
                ", isChunk=" + isChunk +
                '}';
    }
}
